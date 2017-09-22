package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.gear.IntakeDown;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue center peg single gear auto
 * 
 * @author tedlin
 *
 */

public class BlueCenterPegGearAuto extends CommandGroup {

    public BlueCenterPegGearAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallToCenterPegDistance,
		DriveConstants.BlueWallToCenterPegDistance, 4));
	addParallel(new IntakeDown());
	addSequential(new DriveDistancePID(DriveConstants.BlueCenterPegBackUpDistance,
		DriveConstants.BlueCenterPegBackUpDistance, 4));
    }

}
