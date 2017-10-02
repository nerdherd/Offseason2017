package com.team687.frc2017.commands.gear;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeSetVoltage extends Command {

    private double m_pow;

    /**
     * 0 V to 12 V
     * 
     * @param pow
     */
    public IntakeSetVoltage(double pow) {
	requires(Robot.gearIntake);
	m_pow = pow;
    }

    @Override
    public void execute() {
	Robot.gearIntake.setArticVoltage(m_pow);
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

}
