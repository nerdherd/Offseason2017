package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Pivot to absolute angle
 * 
 * @author tedlin
 *
 */

public class ArcTurn extends Command {

    private double m_desiredAngle;
    private boolean m_isRightPowered;
    private double m_timeout, m_startTime;
    private double m_error;
    private double m_sign;

    /**
     * @param desiredAngle
     * @param isRightPowered
     * @param timeout
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double timeout, double sign) {
	m_desiredAngle = -desiredAngle;
	m_isRightPowered = isRightPowered;
	m_timeout = timeout;
	m_sign = Math.signum(sign);	
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ArcTurn");

	m_startTime = Timer.getFPGATimestamp();
	Robot.drive.shiftUp();
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = m_desiredAngle - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;
	double rotPower = DriveConstants.kRotP * m_error; // 1.95 since only one side of the drivetrain is moving
	double sign = Math.signum(rotPower);
	if (Math.abs(rotPower) < DriveConstants.kMinRotPower) {
	    rotPower = DriveConstants.kMinRotPower * sign;
	}
	rotPower = Math.abs(rotPower) * m_sign;

	if (m_isRightPowered) {
	    Robot.drive.setPower(0, rotPower);
	} else if (!m_isRightPowered) {
	    Robot.drive.setPower(rotPower, 0);
	}
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) <= DriveConstants.kDriveRotationTolerance
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