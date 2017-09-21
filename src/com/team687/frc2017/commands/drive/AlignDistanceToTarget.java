package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Aligns the distance from target using vision
 * 
 * @author tedlin
 *
 */

public class AlignDistanceToTarget extends Command {

    private double m_error;
    private double m_startTime;
    private double m_timeout;

    public AlignDistanceToTarget() {
	m_timeout = 1.678;

	// subsystem dependencies
	requires(Robot.drive);
    }

    public AlignDistanceToTarget(double timeout) {
	m_timeout = timeout;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "AlignDistanceToTarget");

	Robot.drive.shiftUp();
	Robot.drive.stopDrive();

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	double robotInchesFromTarget = Robot.visionAdapter.getDistanceFromTarget();
	m_error = NerdyMath.feetToInches(DriveConstants.kShotDistanceFeet) - robotInchesFromTarget;

	double straightPower = DriveConstants.kDistP * m_error;
	double sign = Math.signum(straightPower);
	if (Math.abs(straightPower) > DriveConstants.kMaxDistPower) {
	    straightPower = DriveConstants.kMaxDistPower * sign;
	}
	if (Math.abs(straightPower) < DriveConstants.kMinDistPower) {
	    straightPower = DriveConstants.kMaxDistPower * sign;
	}

	Robot.drive.setPower(straightPower, -straightPower);
    }

    @Override
    protected boolean isFinished() {
	return Math.abs(m_error) < DriveConstants.kDriveDistanceTolerance
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
