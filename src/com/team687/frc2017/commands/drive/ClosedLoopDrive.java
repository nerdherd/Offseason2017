package com.team687.frc2017.commands.drive;

import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Simple split joystick arcade
 * 
 * @author tedlin
 *
 */

public class ClosedLoopDrive extends Command {

    public ClosedLoopDrive() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "ClosedLoopDrive");
	Robot.drive.stopDrive();
    }

    @Override
    protected void execute() {
	double wheelX = Robot.oi.getDriveJoyRightX();
	double wheelY = Robot.oi.getDriveJoyRightY();

	double theta = Math.atan(wheelX / wheelY) * 57.29578;
	if (wheelY < 0 && wheelX < 0) {
	    theta = -180 + theta;
	} else if (wheelY < 0 && wheelX > 0) {
	    theta = 180 + theta;
	}
	SmartDashboard.putNumber("Closed loop theta", theta);

	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double error = theta - robotAngle;
	error = (error > 180) ? error - 360 : error;
	error = (error < -180) ? error + 360 : error;

	double wheel = DriveConstants.kRotP * error;

	// wheel = Math.pow(wheel, 2) * sign; // sensitivity
	double throttle = Robot.oi.getDriveJoyLeftY();

	SmartDashboard.putNumber("Closed Loop Wheel", wheel);
	SmartDashboard.putNumber("Closed Loop Throttle", throttle);

	double leftPower = wheel + throttle;
	double rightPower = wheel - throttle;

	SmartDashboard.putNumber("Left power", leftPower);
	SmartDashboard.putNumber("Right power", rightPower);

	// Robot.drive.setPower(leftPower, rightPower);
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