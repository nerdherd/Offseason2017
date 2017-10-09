package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turn to a specified angle (no vision, absolute)
 * 
 * @author tedlin
 * 
 */

public class TurnToAngle extends Command {

    private double m_angleToTurn;
    private double m_startTime, m_timeout;
    private double m_error;

    public TurnToAngle(double angle) {
	m_angleToTurn = angle;
	m_timeout = 10; // default timeout is 10 seconds

	// subsystem dependencies
	requires(Robot.drive);
    }

    /**
     * @param angle
     * @param timeout
     */
    public TurnToAngle(double angle, double timeout) {
	m_angleToTurn = angle;
	m_timeout = timeout;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "TurnToAngle");
	m_startTime = Timer.getFPGATimestamp();

	Robot.drive.shiftUp();
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	m_error = m_angleToTurn - robotAngle;
	m_error = (m_error > 180) ? m_error - 360 : m_error;
	m_error = (m_error < -180) ? m_error + 360 : m_error;
	double power = DriveConstants.kRotP * m_error;

	double sign = Math.signum(power);
	if (Math.abs(power) > DriveConstants.kMaxRotPower) {
	    power = DriveConstants.kMaxRotPower * sign;
	}
	if (Math.abs(power) < DriveConstants.kMinRotPower) {
	    power = DriveConstants.kMinRotPower * sign;
	}
	Robot.drive.setPower(power, power);
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
