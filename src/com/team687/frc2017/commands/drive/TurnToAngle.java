package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle (no vision, absolute) Rough +- 1 degree before
 * vision kicks in
 * 
 * @author tedlin
 * 
 */

public class TurnToAngle extends Command {

    private double m_angleToTurn;
    private int m_counter = 0;
    private double m_startTime;
    private double m_timeout;
    private double kP;

    private double m_desiredAngle;
    private double m_error;
    // private NerdyPID m_rotPID;

    /**
     * @param angle
     */
    public TurnToAngle(double angle, double timeout) {
	m_angleToTurn = angle;
	m_timeout = timeout; // default timeout is 3 seconds

	// subsystem dependencies
	requires(Robot.drive);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TurnToAngle");
	m_startTime = Timer.getFPGATimestamp();

	// this nullifies the angle set by constructor
	// m_angleToTurn = SmartDashboard.getNumber("*****TurnToAngle desired");
	// kP = SmartDashboard.getNumber("*****kP rot test editable");
	kP = DriveConstants.kRotP;
	// m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
	// m_rotPID = new NerdyPID(kP, kI, kD);
	// m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
	// m_rotPID.setDesired(m_desiredAngle);
	// m_rotPID.setGyro(true);

	Robot.drive.stopDrive();
	Robot.drive.shiftDown();
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = m_angleToTurn - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;
	SmartDashboard.putNumber("Angle Error", m_error);
	// double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
	double power = kP * m_error;
	double sign = Math.signum(power);
	// double minRotPower = SmartDashboard.getDouble("*****min rot power");
	double minRotPower = DriveConstants.kMinRotPower;
	if (Math.abs(power) < minRotPower) {
	    power = minRotPower * sign;
	}
	Robot.drive.setPower(power, power);
	// if (Math.abs(m_error) <= Constants.kDriveRotationTolerance) {
	// m_counter ++;
	// } else {
	// m_counter = 0;
	// }
	SmartDashboard.putNumber("execute time rotational rough", Timer.getFPGATimestamp() - m_startTime);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) <= DriveConstants.kDriveRotationToleranceRough
		|| Timer.getFPGATimestamp() - m_startTime > m_timeout;
    }

    @Override
    protected void end() {
	Robot.drive.stopDrive();
    }

    @Override
    protected void interrupted() {
	end();
    }

}
