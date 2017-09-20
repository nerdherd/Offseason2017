package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.ArcTurn;
import com.team687.frc2017.commands.drive.DriveStraightContinuous;
import com.team687.frc2017.commands.drive.DriveUntilCollision;
import com.team687.frc2017.commands.drive.LiveVisionTracking;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 973 style red hopper auto
 * 
 * @author tedlin
 *
 */

public class RedHopperShootAuto973 extends CommandGroup {

    public RedHopperShootAuto973() {
	// drive to hopper with Bezier curves
	// addSequential(new DriveBezierRio(Constants.RedPathWallToHopper, 1));

	// drive to hopper with continuous motion and arc turns
	addSequential(new DriveStraightContinuous(DriveConstants.RedWallToHopperInitialDistance,
		DriveConstants.kMaxRotPower, true));
	addSequential(new ArcTurn(DriveConstants.RedWallToHopperArcTurnAngle, false, 0, true));
	addSequential(new WaitTime(0.687));
	addSequential(new DriveUntilCollision(0.971, 1.95));

	// back up in two motions
	// addSequential(new DriveDistancePID(Constants.BlueHopperBackUpDistance,
	// Constants.BlueHopperBackUpDistance));
	// addSequential(new TurnToAngle(Constants.BlueHopperAngleToShoot));

	// back up in one motion
	addSequential(new ArcTurn(DriveConstants.RedHopperToBoilerAngle, true, 0, true));

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
