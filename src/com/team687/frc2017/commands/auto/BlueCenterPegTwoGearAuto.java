package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.SnapToTarget;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue two gear center peg auto
 * 
 * @author tedlin
 *
 */

public class BlueCenterPegTwoGearAuto extends CommandGroup {

    public BlueCenterPegTwoGearAuto() {
	addSequential(
		new DriveDistancePID(DriveConstants.BlueWallToCenterPegDistance, DriveConstants.BlueWallToCenterPegDistance));
	// addSequential(new DeployGear());
	addSequential(
		new DriveDistancePID(DriveConstants.BlueCenterPegBackUpDistance, DriveConstants.BlueCenterPegBackUpDistance));

	addSequential(new WaitTime(0.3));
	addSequential(new TurnToAngle(DriveConstants.BlueWallCenterToSecondGearAngle, 4, true));
	addSequential(new WaitTime(0.3));
	addSequential(new DriveDistancePID(DriveConstants.BlueWallCenterToSecondGearDistance,
		DriveConstants.BlueWallCenterToSecondGearDistance));
	// addParallel(new IntakeGear());

	addSequential(new DriveDistancePID(-DriveConstants.BlueWallCenterToSecondGearDistance,
		-DriveConstants.BlueWallCenterToSecondGearDistance));
	// addParallel(new GearManipUp());
	addSequential(new WaitTime(0.2));
	addSequential(new TurnToAngle(0, 4, true));
	addSequential(new WaitTime(0.1));
	addSequential(new SnapToTarget(true, 2));
	addSequential(new WaitTime(0.4));
	addSequential(
		new DriveDistancePID(-DriveConstants.BlueCenterPegBackUpDistance, -DriveConstants.BlueCenterPegBackUpDistance));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3, false));
    }

}
