package com.team687.frc2017.commands.gear;

import com.team687.frc2017.constants.GearIntakeConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class IntakeDown extends CommandGroup {

    public IntakeDown() {
	addSequential(new IntakeSetPosition(GearIntakeConstants.kGearIntakeDownPos));
	// addSequential(new IntakeSetVoltage(2));
    }

}
