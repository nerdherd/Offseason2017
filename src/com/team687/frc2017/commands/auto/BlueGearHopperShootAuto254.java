package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveBezierRio;
import com.team687.frc2017.commands.drive.WaitTime;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 254 style blue gear+hopper auto (doesn't work, check other version)
 */

public class BlueGearHopperShootAuto254 extends CommandGroup {

    public BlueGearHopperShootAuto254() {
	// deploy gear
	// addParallel(new IntakeSetPosition(GearIntakeConstants.kGearIntakeUpPos));
	addSequential(new DriveBezierRio(DriveConstants.BluePathWallToClosePeg, -0.45, false, true));
	// addSequential(new TurnToAngle(60.126, 2)); // This is the first heading in
	// the next path
	// segment. This solves a problem where robot starts spinning
	// to find correct heading between two path segments.
	addSequential(new WaitTime(0.687));
	// addParallel(new IntakeDown());

	// drive to hopper
	// addParallel(new SetGearManipulatorUp());
	// addSequential(new DriveBezierRio(DriveConstants.BluePathPegToHopper, 0.687,
	// true, false));
	// addSequential(new ArcTurn(DriveConstants.BlueHopperToBoilerCorrectingAngle,
	// false, 0.33));

	// shoot
	// addParallel(new LiveVisionTracking());
	// addParallel(new Shoot());
    }
}
