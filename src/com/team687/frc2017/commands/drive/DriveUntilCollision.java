package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive until collision. Collision detection using NavX acceleration
 * 
 * @author tedlin
 *
 */

public class DriveUntilCollision extends Command {

    private double m_straightPower;
    private double m_timeout;
    private double m_startTime;

    private double m_lastAccelX;
    private double m_lastAccelY;
    private double m_jerkX;
    private double m_jerkY;

    public DriveUntilCollision(double straightPower) {
	m_straightPower = straightPower;
	m_timeout = 3.3;

	// subsystem dependencies
	requires(Robot.drive);
    }

    /**
     * @param straightPower
     * @param isHighGear
     * @param timeout
     */
    public DriveUntilCollision(double straightPower, double timeout) {
	m_straightPower = straightPower;
	m_timeout = timeout;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveUntilCollision");
	Robot.drive.stopDrive();

	m_lastAccelX = 0;
	m_lastAccelY = 0;

	Robot.drive.shiftUp();
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double currentAccelX = Robot.drive.getCurrentAccelX();
	double currentAccelY = Robot.drive.getCurrentAccelY();

	m_jerkX = currentAccelX - m_lastAccelX;
	m_jerkY = currentAccelY - m_lastAccelY;

	Robot.drive.setPower(m_straightPower, -m_straightPower);

	m_lastAccelX = currentAccelX;
	m_lastAccelY = currentAccelY;
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_jerkX) > DriveConstants.kCollisionThreshold
		|| Math.abs(m_jerkY) > DriveConstants.kCollisionThreshold
		|| Timer.getFPGATimestamp() > m_timeout + m_startTime;
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
