package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveBezierPath;
import com.team687.frc2017.commands.drive.LiveVisionTracking;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue hopper auto 1678 (follow the wall, starts with side of robot one fuel
 * ball away from boiler)
 * 
 * @author tedlin
 *
 */

public class BlueHopperShootAuto1678 extends CommandGroup {

    public BlueHopperShootAuto1678() {
	// drive up next to hopper
	addSequential(new DriveBezierPath(DriveConstants.BluePathWallToHopper1678, 0.4, false, false));

	// proc hopper
	// addSequential(new ExpandHopper());

	// aim and shoot
	addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }

}
