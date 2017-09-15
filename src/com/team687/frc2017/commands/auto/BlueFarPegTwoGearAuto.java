package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveBezierRio;
import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue far peg two gear auto
 * 
 * @author tedlin
 *
 */

public class BlueFarPegTwoGearAuto extends CommandGroup {

    public BlueFarPegTwoGearAuto() {
	addSequential(new DriveBezierRio(DriveConstants.BluePathWallToFarPeg, -0.687, true, true));
	// addSequential(new DeployGear());
	addSequential(new DriveBezierRio(DriveConstants.BluePathFarPegBackUp, 0.687, true, true));
	addSequential(new WaitTime(0.2));

	addSequential(new TurnToAngle(DriveConstants.BlueWallFarToSecondGearAngle, 4, true));
	addSequential(new WaitTime(0.2));
	// addParallel(new IntakeGear());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallFarToSecondGearDistance,
		DriveConstants.BlueWallFarToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(-DriveConstants.BlueWallFarToSecondGearDistance,
		-DriveConstants.BlueWallFarToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	// addParallel(new GearManipUp());
	addSequential(new TurnToAngle(0, 4, true));
	addSequential(new WaitTime(0.2));

	addSequential(new DriveBezierRio(DriveConstants.BluePathWallToFarPeg, -0.687, true, true));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3, false));
    }

}
