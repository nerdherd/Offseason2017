package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Simple split joystick arcade
 * 
 * @author tedlin
 *
 */

public class HaloDrive extends Command {

    public HaloDrive() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "HaloDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double throttle = Robot.oi.getDriveJoyLeftY();
	double wheel = -Robot.oi.getDriveJoyRightX();
	double sign = Math.signum(wheel);
	// wheel = Math.pow(wheel, 2) * sign; // sensitivity

	SmartDashboard.putNumber("Halo Wheel", wheel);
	SmartDashboard.putNumber("Halo Throttle", throttle);

	double leftPower = wheel + throttle;
	double rightPower = wheel - throttle;

	SmartDashboard.putNumber("Left power", leftPower);
	SmartDashboard.putNumber("Right power", rightPower);

	Robot.drive.setPower(leftPower, rightPower);
    }

    @Override
    protected boolean isFinished() {
	return false;
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