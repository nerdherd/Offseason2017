package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveBezierRio;
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
 * Blue far peg two gear auto
 * 
 * @author tedlin
 *
 */

public class BlueFarPegTwoGearAuto extends CommandGroup {

    public BlueFarPegTwoGearAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveBezierRio(DriveConstants.BluePathWallToFarPeg, -0.687, true, true));
	addParallel(new IntakeDown());
	addSequential(new DriveBezierRio(DriveConstants.BluePathFarPegBackUp, 0.687, true, true));
	addSequential(new WaitTime(0.2));

	addParallel(new IntakeDownSpin());
	addSequential(new TurnToAngle(DriveConstants.BlueWallFarToSecondGearAngle, 4));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(DriveConstants.BlueWallFarToSecondGearDistance,
		DriveConstants.BlueWallFarToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	addSequential(new DriveDistancePID(-DriveConstants.BlueWallFarToSecondGearDistance,
		-DriveConstants.BlueWallFarToSecondGearDistance));
	addSequential(new WaitTime(0.2));
	addParallel(new IntakeTuckRetain());
	addSequential(new TurnToAngle(0, 4));
	addSequential(new WaitTime(0.2));

	addSequential(new DriveBezierRio(DriveConstants.BluePathWallToFarPeg, -0.687, true, true));
	addParallel(new IntakeDown());
	addSequential(new DriveTime(0.5, 3));
    }

}
