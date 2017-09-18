package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.SnapToTarget;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Red center peg two gear auto
 * 
 * @author tedlin
 *
 */

public class RedCenterPegTwoGearAuto extends CommandGroup {

    public RedCenterPegTwoGearAuto() {
	addSequential(new DriveDistancePID(DriveConstants.RedWallToCenterPegDistance,
		DriveConstants.RedWallToCenterPegDistance));
	// addSequential(new DeployGear());
	addSequential(new DriveDistancePID(DriveConstants.RedCenterPegBackUpDistance,
		DriveConstants.RedCenterPegBackUpDistance));

	addSequential(new WaitTime(0.3));
	addSequential(new TurnToAngle(DriveConstants.RedWallToSecondGearAngle, 4));
	addSequential(new WaitTime(0.3));
	addSequential(new DriveDistancePID(DriveConstants.RedWallToSecondGearDistance,
		DriveConstants.RedWallToSecondGearDistance));
	// addParallel(new IntakeGear());

	addSequential(new DriveDistancePID(-DriveConstants.RedWallToSecondGearDistance,
		-DriveConstants.RedWallToSecondGearDistance));
	// addParallel(new GearManipUp());
	addSequential(new WaitTime(0.2));
	addSequential(new TurnToAngle(0, 4));
	addSequential(new WaitTime(0.1));
	addSequential(new SnapToTarget(true, 2));
	addSequential(new WaitTime(0.4));
	addSequential(new DriveDistancePID(-DriveConstants.RedCenterPegBackUpDistance,
		-DriveConstants.RedCenterPegBackUpDistance));
	// addSequential(new DeployGear());
	addSequential(new DriveTime(0.5, 3));
    }

}
