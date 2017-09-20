package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.ArcTurn;
import com.team687.frc2017.commands.drive.DriveStraightContinuous;
import com.team687.frc2017.commands.drive.DriveUntilCollision;
import com.team687.frc2017.commands.drive.LiveVisionTracking;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue hopper auto 973 style
 * 
 * @author tedlin
 *
 */

public class BlueHopperShootAuto973 extends CommandGroup {

    public BlueHopperShootAuto973() {
	// drive to hopper with Bezier curves
	// addSequential(new DriveBezierRio(DriveConstants.BluePathWallToHopper973,
	// 0.687, true, false));

	// drive to hopper with continuous motion and arc turns
	addSequential(new DriveStraightContinuous(DriveConstants.BlueWallToHopperInitialDistance,
		DriveConstants.kMaxRotPower, true));
	addSequential(new ArcTurn(DriveConstants.BlueWallToHopperArcTurnAngle, true, 0, true));
	addSequential(new DriveUntilCollision(0.971, 1.95));

	// back up in two motions
	// addSequential(new DriveDistancePID(Constants.BlueHopperBackUpDistance,
	// Constants.BlueHopperBackUpDistance));
	// addSequential(new TurnToAngle(Constants.BlueHopperAngleToShoot));
	addSequential(new WaitTime(3));

	// back up in one motion
	addSequential(new ArcTurn(DriveConstants.BlueHopperToBoilerAngle, false, 0, true));

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
