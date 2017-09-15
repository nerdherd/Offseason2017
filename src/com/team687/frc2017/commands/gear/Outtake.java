package com.team687.frc2017.commands.gear;

import com.team687.frc2017.constants.GearIntakeConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Outtake extends CommandGroup {
    public Outtake() {
	addParallel(new IntakeSetPosition(GearIntakeConstants.kGearIntakeUpPos));
	addSequential(new SpinSpeed(GearIntakeConstants.kGearOuttakeSpinVoltage));
    }
}
