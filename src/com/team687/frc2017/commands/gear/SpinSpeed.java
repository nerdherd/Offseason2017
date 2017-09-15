package com.team687.frc2017.commands.gear;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class SpinSpeed extends Command {

    private double m_desired;

    public SpinSpeed(double pow) {
	requires(Robot.gearIntake);
	m_desired = pow;
    }

    @Override
    public void execute() {
	Robot.gearIntake.setSpinVoltage(m_desired);
    }

    @Override
    protected void end() {
	Robot.gearIntake.setSpinVoltage(0);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

}
