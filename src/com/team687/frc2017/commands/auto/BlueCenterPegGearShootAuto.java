package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.commands.gear.IntakeDown;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue center peg + shoot preload 10 kpa auto
 */

public class BlueCenterPegGearShootAuto extends CommandGroup {

    public BlueCenterPegGearShootAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallToCenterPegDistance,
		DriveConstants.BlueWallToCenterPegDistance, 4));
	addParallel(new IntakeDown());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallCenterToTurnPoint,
		DriveConstants.BlueWallCenterToTurnPoint, 4));
	addSequential(new WaitTime(0.3));
	addSequential(new TurnToAngle(DriveConstants.BlueCenterToBoilerTurnPointAngle, 4));
	addSequential(new WaitTime(0.3));
	addSequential(new DriveDistancePID(DriveConstants.BlueCenterTurnPointToBoiler,
		DriveConstants.BlueCenterTurnPointToBoiler, 4));
	// addSequential(new SnapToTarget(true));
	// addParallel(new AlignDistanceToTarget());
	// addParallel(new Shoot());
    }

}
