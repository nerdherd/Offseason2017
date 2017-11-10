package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive at a specified heading. Loop is closed on heading but not on straight
 * power.
 * 
 * @author tedlin
 *
 */

public class DriveAtHeading extends Command {

    private double m_straightPower;
    private double m_heading, m_distance;

    /**
     * @param straightPower
     *            (determines direction and magnitude)
     * @param heading
     * @param distance
     *            (absolute value)
     * @param isHighGear
     */
    public DriveAtHeading(double straightPower, double heading, double distance) {
	m_straightPower = straightPower;
	m_heading = heading;
	m_distance = distance;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveAtHeading");
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double rotError = -m_heading - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = DriveConstants.kRotP * rotError;

	Robot.drive.setPower(rotPower + m_straightPower, rotPower - m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(Robot.drive.getDrivetrainTicks()) >= m_distance;
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