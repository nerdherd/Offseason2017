package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.ArcTurn;
import com.team687.frc2017.commands.drive.DriveDistanceContinuous;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.DriveUntilCollision;
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
	addSequential(new DriveDistanceContinuous(DriveConstants.BlueWallToHopperInitialDistance,
		DriveConstants.kMaxRotPower));
	addSequential(new ArcTurn(DriveConstants.BlueWallToHopperArcTurnAngle, true, 0));
	addSequential(new WaitTime(0.5));
	addSequential(new DriveUntilCollision(0.971, 0.987));

	// back up in two motions
	// addSequential(new DriveDistancePID(Constants.BlueHopperBackUpDistance,
	// Constants.BlueHopperBackUpDistance));
	// addSequential(new TurnToAngle(Constants.BlueHopperAngleToShoot));
	addSequential(new WaitTime(3));

	// back up in one motion
	addSequential(new DriveTime(-0.687, 1));
	addSequential(new ArcTurn(DriveConstants.BlueHopperToBoilerAngle, false, 0));

	// aim and shoot
	// addSequential(new SnapToTarget(true));
	// addParallel(new AlignDistanceToTarget(3.3));
	// addParallel(new Shoot());
    }

}
