package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveBezierPath;
import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.SnapToTarget;
import com.team687.frc2017.commands.drive.TurnTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 2056 follow the wall red
 * 
 * @author tedlin
 *
 */

public class RedHopperShootAuto2056 extends CommandGroup {

    public RedHopperShootAuto2056() {
	// drive a little past hopper
	addSequential(new DriveBezierPath(DriveConstants.RedPathWallToHopper2056, 0.4, false, false));

	// proc hopper by turning
	addSequential(new TurnTime(-0.687, 0.3));

	// drive align with goal
	addSequential(new SnapToTarget(true));

	// drive to perfectly align with hopper
	addSequential(new DriveDistancePID(DriveConstants.RedPathDistanceAlignWithHopper,
		DriveConstants.RedPathDistanceAlignWithHopper, 2));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
