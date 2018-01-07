package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.ArcTurn;
import com.team687.frc2017.commands.drive.DriftTurnToAngle;
import com.team687.frc2017.commands.drive.DriveAtHeading;
import com.team687.frc2017.commands.drive.ResetEncoders;
import com.team687.frc2017.commands.drive.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * 254 style gear + hopper
 */

public class BlueGearHopperAuto254 extends CommandGroup {

    public BlueGearHopperAuto254() {
	addSequential(new DriveAtHeading(-.7, 0, 25000, 0.004));
	addSequential(new DriveAtHeading(-.7, 0, 56000, 0.004));
	addSequential(new DriveAtHeading(-.3, 45, 130000, 0.004));
	addSequential(new DriveAtHeading(-.3, 60, 250000, 0.0012));

	addSequential(new ResetEncoders());
	addSequential(new WaitTime(.25));
	addSequential(new DriveAtHeading(0.5, 55, 5000, 0.004));
	addSequential(new ArcTurn(120, true, 3, -1));
	addSequential(new ResetEncoders());
	addSequential(new DriveAtHeading(0.5, 120, 40000, 0.004));
	addSequential(new DriveAtHeading(0.3, 120, 40000, 0.004));
	addSequential(new ArcTurn(30, false, 4, 1));
	addSequential(new ResetEncoders());
	addSequential(new DriftTurnToAngle(0.3, 0, 46006.0, 0.01));

    }

}
