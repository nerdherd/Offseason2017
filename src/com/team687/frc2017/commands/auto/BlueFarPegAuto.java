package com.team687.frc2017.commands.auto;

import com.team687.frc2017.commands.drive.DriveDistancePID;
import com.team687.frc2017.commands.drive.DriveTime;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.gear.IntakeDown;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.constants.DriveConstants;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Blue far peg auto
 * 
 * @author tedlin
 *
 */

public class BlueFarPegAuto extends CommandGroup {

    public BlueFarPegAuto() {
	addParallel(new IntakeTuckRetain());
	addSequential(new DriveDistancePID(DriveConstants.BlueWallToClosePegTurnPoint,
		DriveConstants.BlueWallToClosePegTurnPoint));
	addSequential(new TurnToAngle(DriveConstants.BlueClosePegTurnPointAngle));
	addSequential(new DriveDistancePID(DriveConstants.BlueClosePegTurnPointToClosePeg,
		DriveConstants.BlueClosePegTurnPointToClosePeg));
	addParallel(new IntakeDown());
	addSequential(new DriveTime(0.5, 3));
    }

}
