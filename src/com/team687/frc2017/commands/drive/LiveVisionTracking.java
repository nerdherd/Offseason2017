package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.VisionAdapter;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Live vision tracking (follows target around, no end)
 * 
 * @author tedlin
 *
 */

public class LiveVisionTracking extends Command {

    // private NerdyPID m_rotPID;

    /**
     * @param angle
     */
    public LiveVisionTracking() {
	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "LiveVisionTracking");
	// m_rotPID = new NerdyPID(Constants.kRotP, Constants.kRotI, Constants.kRotD);
	// m_rotPID.setOutputRange(Constants.kMinRotPower, Constants.kMaxRotPower);
	// m_rotPID.setDesired(m_angleToTurn);
	// m_rotPID.setGyro(true);

	Robot.drive.stopDrive();
	Robot.drive.shiftUp();
    }

    @Override
    protected void execute() {
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double relativeAngleError = VisionAdapter.getInstance().getAngleToTurn();
	double processingTime = VisionAdapter.getInstance().getProcessedTime();
	double absoluteDesiredAngle = relativeAngleError + Robot.drive.timeMachineYaw(processingTime);
	double error = absoluteDesiredAngle - robotAngle;
	SmartDashboard.putNumber("Angle Error", error);
	// double power = m_rotPID.calculate(Robot.drive.getCurrentYaw());
	double power = DriveConstants.kRotP * error;
	double sign = Math.signum(power);
	if (Math.abs(power) < DriveConstants.kMinRotPower) {
	    power = sign * DriveConstants.kMinRotPower;
	}
	if (Math.abs(error) <= DriveConstants.kDriveRotationDeadband) {
	    power = 0;
	}
	Robot.drive.setPower(power, power);
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
