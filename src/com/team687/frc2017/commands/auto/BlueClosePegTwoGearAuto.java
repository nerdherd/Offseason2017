package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveBezierPath;
import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.commands.gear.IntakeDown;
import com.team687.frc2017.commands.gear.IntakeDownSpin;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue peg close to hopper two gear auto (similar to 1011 FOC auto)
 * 
 * @author tedlin
 * 
 */

public class BlueClosePegTwoGearAuto extends CommandGroup {

    public BlueClosePegTwoGearAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveBezierPath(DriveConstants.BluePathWallToClosePeg, -0.687, true, true));
	addParallel(new IntakeDown());
	addSequential(new DriveBezierPath(DriveConstants.BluePathClosePegBackUp, 0.687, true, true));
	addSequential(new WaitTime(0.2));

	addSequential(new IntakeDownSpin());
	addSequential(new TurnToAngle(DriveConstants.BlueWallCloseToSecondGearAngle, 3));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(DriveConstants.BlueWallCloseToSecondGearDistance,
		DriveConstants.BlueWallCloseToSecondGearDistance, 3));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(-DriveConstants.BlueWallCloseToSecondGearDistance,
		-DriveConstants.BlueWallCloseToSecondGearDistance, 3));
	addSequential(new WaitTime(0.2));
	addParallel(new IntakeTuckRetain());
	addSequential(new TurnToAngle(0, 3));
	addSequential(new WaitTime(0.2));

	addSequential(new DriveBezierPath(DriveConstants.BluePathWallToClosePeg, -0.687, true, true));
	addParallel(new IntakeDown());
	addSequential(new DriveTime(0.5, 3));
    }

}
