package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Pure PID distance control
 *
 * @author tedlin
 *
 */

public class DriveDistancePID extends Command {

    private double m_rightDistance;
    private double m_leftDistance;
    private double m_rightError;
    private double m_leftError;

    private double m_startTime;
    private double m_timeout;

    public DriveDistancePID(double distance) {
	m_timeout = 10;
	m_rightDistance = distance;
	m_leftDistance = distance;

	// subsystem dependencies
	requires(Robot.drive);
    }

    // /**
    // * @param distance
    // * @param timeout
    // */
    // public DriveDistancePID(double distance, double timeout) {
    // m_timeout = timeout;
    // m_rightDistance = distance;
    // m_leftDistance = distance;
    //
    // // subsystem dependencies
    // requires(Robot.drive);
    // }

    /**
     * @param rightDistance
     * @param leftDistance
     * @param timeout
     */
    public DriveDistancePID(double rightDistance, double leftDistance, double timeout) {
	m_timeout = timeout;
	m_rightDistance = rightDistance;
	m_leftDistance = leftDistance;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveDistancePID");

	Robot.drive.shiftUp();
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
	m_rightError = m_rightDistance - Robot.drive.getRightTicks();
	m_leftError = m_leftDistance - Robot.drive.getLeftTicks();

	double straightRightPower = DriveConstants.kDistP * m_rightError;
	double straightLeftPower = DriveConstants.kDistP * m_leftError;

	double rightSign = Math.signum(straightRightPower);
	if (Math.abs(straightRightPower) > DriveConstants.kMaxDistPower) {
	    straightRightPower = DriveConstants.kMaxDistPower * rightSign;
	}
	if (Math.abs(straightRightPower) < DriveConstants.kMinDistPower) {
	    straightRightPower = DriveConstants.kMinDistPower * rightSign;
	}

	double leftSign = Math.signum(straightLeftPower);
	if (Math.abs(straightLeftPower) > DriveConstants.kMaxDistPower) {
	    straightLeftPower = DriveConstants.kMaxDistPower * leftSign;
	}
	if (Math.abs(straightLeftPower) < DriveConstants.kMinDistPower) {
	    straightLeftPower = DriveConstants.kMinDistPower * leftSign;
	}

	Robot.drive.setPower(straightLeftPower, -straightRightPower);
    }

    @Override
    protected boolean isFinished() {
	boolean reachedGoal = Math.abs(m_leftError) < DriveConstants.kDriveDistanceTolerance
		&& Math.abs(m_rightError) < DriveConstants.kDriveDistanceTolerance;
	return reachedGoal || Timer.getFPGATimestamp() - m_startTime > m_timeout;
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
