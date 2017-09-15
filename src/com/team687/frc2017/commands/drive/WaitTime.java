package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Wait time
 * 
 * @author tedlin
 *
 */

public class WaitTime extends Command {

    private double m_time;
    private double m_startTime;

    /**
     * @param time
     */
    public WaitTime(double time) {
	m_time = time;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "WaitTime");
	Robot.drive.stopDrive();

	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() <= m_time + m_startTime;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
	end();
    }

}
