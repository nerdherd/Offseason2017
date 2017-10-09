package com.team687.frc2017.commands.gear;

import com.team687.frc2017.constants.GearIntakeConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeDownSpin extends CommandGroup {

    public IntakeDownSpin() {
	addParallel(new IntakeSetPosition(GearIntakeConstants.kGearIntakeDownPos));
	addSequential(new SpinSpeed(GearIntakeConstants.kGearIntakeSpinVoltage));
    }

}
