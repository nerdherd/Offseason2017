package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.gear.IntakeDown;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Close peg single gear auto
 * 
 * @author tedlin
 *
 */

public class BlueClosePegGearAuto extends CommandGroup {

    public BlueClosePegGearAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallToFarPegTurnPoint,
		DriveConstants.BlueWallToFarPegTurnPoint, 4));
	addSequential(new TurnToAngle(DriveConstants.BlueFarPegTurnPointAngle, 4));
	addSequential(new DriveDistancePID(DriveConstants.BlueFarPegTurnPointToFarPeg,
		DriveConstants.BlueFarPegTurnPointToFarPeg, 4));
	addParallel(new IntakeDown());
	addSequential(new DriveTime(0.687, 3));
    }

}
