package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveAtHeading;
import com.team687.frc2017.commands.drive.ResetEncoders;
import com.team687.frc2017.commands.drive.ShiftUp;
import com.team687.frc2017.commands.drive.WaitTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class Hopper971Auto extends CommandGroup {

    public Hopper971Auto() {

	addSequential(new DriveAtHeading(-0.5, 0, 70000, 0.004));
	addSequential(new DriveAtHeading(-0.5, -20, 190000, 0.01));
	addSequential(new DriveAtHeading(-0.5, -30, 210000, 0.01));
	// addSequential(new DriveAtHeading(0.5, 0, 225000, 0.01));
	addSequential(new WaitTime(5));
	// addSequential(new TurnToAngle(0 , 5));
	// addSequential(new ArcTurn(30, true, 5, -1));
	addSequential(new ResetEncoders());
	addSequential(new DriveAtHeading(0.6, 15, 8000, 0.01));
	addSequential(new ShiftUp());

    }

}
