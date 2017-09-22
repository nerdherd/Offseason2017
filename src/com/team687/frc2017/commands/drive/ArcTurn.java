package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Arc turning
 * 
 * @author tedlin
 *
 */

public class ArcTurn extends Command {

    private double m_straightPower;
    private double m_desiredAngle;
    private boolean m_isRightPowered;
    private double m_timeout;
    private double m_startTime;
    private double m_error;

    public ArcTurn(double desiredAngle, boolean isRightPowered, double straightPower) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_straightPower = straightPower;
	m_timeout = 5;

	requires(Robot.drive);
    }

    /**
     * Arc Turn
     * 
     * @param desiredAngle
     * @param isRightPowered
     * @param striaghtPower
     * @param isHighGear
     * @param timeout
     */
    public ArcTurn(double desiredAngle, boolean isRightPowered, double straightPower, double timeout) {
	m_desiredAngle = desiredAngle;
	m_isRightPowered = isRightPowered;
	m_straightPower = straightPower;
	m_timeout = timeout;

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
	SmartDashboard.putNumber("Angle Error", m_error);
	double rotPower = DriveConstants.kRotP * m_error * 1.95; // since only one side of the drivetrain is moving
	double sign = Math.signum(rotPower);

	if (Math.abs(rotPower) > DriveConstants.kMaxRotPower) {
	    rotPower = DriveConstants.kMaxRotPower * sign;
	}
	if (Math.abs(rotPower) < DriveConstants.kMinRotPower) {
	    rotPower = DriveConstants.kMinRotPower * sign;
	}

	if (m_isRightPowered) {
	    Robot.drive.setPower(0 + m_straightPower, rotPower - m_straightPower);
	} else if (!m_isRightPowered) {
	    Robot.drive.setPower(rotPower + m_straightPower, 0 - m_straightPower);
	}
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) < DriveConstants.kDriveRotationTolerance
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
