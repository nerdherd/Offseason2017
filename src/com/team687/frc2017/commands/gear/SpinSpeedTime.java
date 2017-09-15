package com.team687.frc2017.commands.gear;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class SpinSpeedTime extends Command {

    private double m_desired;

    private double m_timeout;
    private double m_startTime;

    public SpinSpeedTime(double pow, double timeout) {
	requires(Robot.gearIntake);

	m_desired = pow;
	m_timeout = timeout;
    }

    @Override
    public void initialize() {
	m_startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
	Robot.gearIntake.setSpinVoltage(m_desired);
    }

    @Override
    protected boolean isFinished() {
	return Timer.getFPGATimestamp() - m_startTime > m_timeout;
    }

}
