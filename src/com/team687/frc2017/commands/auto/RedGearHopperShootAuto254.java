package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.ArcTurn;
import com.team687.frc2017.commands.drive.DriveBezierRio;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 254 style gear + hopper red auto
 * 
 * @author tedlin
 *
 */

public class RedGearHopperShootAuto254 extends CommandGroup {

    public RedGearHopperShootAuto254() {
	// deploy gear
	addSequential(new DriveBezierRio(DriveConstants.RedPathWallToClosePeg, -0.687, true, true));
	// addSequential(new SetGearManipulatorDown());

	// drive to hopper
	// addParallel(new SetGearManipulatorUp());
	addSequential(new DriveBezierRio(DriveConstants.RedPathPegToHopper, 0.687, true, false));
	addSequential(new ArcTurn(DriveConstants.RedHopperToBoilerCorrectingAngle, true, 0));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());

    }

}
