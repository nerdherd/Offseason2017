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

public class BlueCenterPegAuto extends CommandGroup {

    public BlueCenterPegAuto() {
	addSequential(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallToCenterPegDistance,
		DriveConstants.BlueWallToCenterPegDistance, 3));
	addParallel(new IntakeDown());
	addSequential(new DriveDistancePID(DriveConstants.BlueCenterPegBackUpDistance,
		DriveConstants.BlueCenterPegBackUpDistance, 3));
    }

}