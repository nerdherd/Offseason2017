package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive straight without setting power to 0 when it reaches goal
 * 
 * @author tedlin
 *
 */

public class DriveStraightContinuous extends Command {

    private double m_distance;
    private double m_straightPower;

    /**
     * @param distance
     * @param straightPower
     * @param isHighGear
     */
    public DriveStraightContinuous(double distance, double straightPower) {
	m_distance = distance;
	m_straightPower = straightPower;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveStraightContinuous");

	Robot.drive.shiftUp();
	Robot.drive.setPower(0, 0);
    }

    @Override
    protected void execute() {
	Robot.drive.setPower(m_straightPower, -m_straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Robot.drive.getDrivetrainTicks() > m_distance;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
	end();
    }

}
