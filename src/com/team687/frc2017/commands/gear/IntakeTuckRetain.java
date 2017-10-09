package com.team687.frc2017.commands.gear;

import com.team687.frc2017.constants.GearIntakeConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeTuckRetain extends CommandGroup {

    public IntakeTuckRetain() {
	addParallel(new IntakeSetPosition(GearIntakeConstants.kGearIntakeUpPos));
	// addSequential(new SpinSpeed(GearIntakeConstants.kGearIntakeSpinHoldVoltage));
    }

}
