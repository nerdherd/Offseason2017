package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.commands.gear.IntakeDown;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.commands.gear.SpinSpeed;
import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.constants.GearIntakeConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue two gear center peg auto
 * 
 * @author tedlin
 *
 */

public class BlueCenterPegTwoGearAuto extends CommandGroup {

    public BlueCenterPegTwoGearAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallToCenterPegDistance,
		DriveConstants.BlueWallToCenterPegDistance, 4));
	addParallel(new IntakeDown());
	addSequential(new DriveDistancePID(DriveConstants.BlueCenterPegBackUpDistance,
		DriveConstants.BlueCenterPegBackUpDistance, 4));

	addSequential(new WaitTime(0.3));
	addSequential(new TurnToAngle(DriveConstants.BlueWallCenterToSecondGearAngle, 4));
	addSequential(new WaitTime(0.3));
	addParallel(new SpinSpeed(GearIntakeConstants.kGearIntakeSpinVoltage));
	addSequential(new DriveDistancePID(DriveConstants.BlueWallCenterToSecondGearDistance,
		DriveConstants.BlueWallCenterToSecondGearDistance, 4));
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(-DriveConstants.BlueWallCenterToSecondGearDistance,
		-DriveConstants.BlueWallCenterToSecondGearDistance, 4));
	addSequential(new WaitTime(0.2));
	addSequential(new TurnToAngle(0, 4));
	addSequential(new WaitTime(0.1));
	// addSequential(new SnapToTarget(true, 2));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(-DriveConstants.BlueCenterPegBackUpDistance,
		-DriveConstants.BlueCenterPegBackUpDistance, 4));
	addParallel(new IntakeDown());
	addSequential(new DriveTime(0.5, 2));
    }

}
