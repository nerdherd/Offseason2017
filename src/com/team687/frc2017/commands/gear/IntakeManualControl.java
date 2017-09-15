package com.team687.frc2017.commands.gear;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeManualControl extends Command {

    public IntakeManualControl() {
	requires(Robot.gearIntake);
    }

    @Override
    public void execute() {
	Robot.gearIntake.manualControl();
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

}