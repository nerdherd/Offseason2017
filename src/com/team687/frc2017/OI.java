package com.team687.frc2017;

import com.team687.frc2017.commands.auto.BlueCenterPegGearAuto;
import com.team687.frc2017.commands.auto.BlueCenterPegGearShootAuto;
import com.team687.frc2017.commands.auto.BlueCenterPegTwoGearAuto;
import com.team687.frc2017.commands.auto.BlueHopperShootAuto973;
import com.team687.frc2017.commands.drive.ArcTurn;
import com.team687.frc2017.commands.drive.DriveUntilCollision;
import com.team687.frc2017.commands.drive.LiveVisionTracking;
import com.team687.frc2017.commands.drive.ResetEncoders;
import com.team687.frc2017.commands.drive.ResetGyro;
import com.team687.frc2017.commands.drive.ShiftDown;
import com.team687.frc2017.commands.drive.ShiftUp;
import com.team687.frc2017.commands.drive.SnapToTarget;
import com.team687.frc2017.commands.drive.TankDrive;
import com.team687.frc2017.commands.drive.TurnToAngle;
import com.team687.frc2017.commands.gear.IntakeSetPosition;
import com.team687.frc2017.commands.gear.IntakeTuckRetain;
import com.team687.frc2017.commands.gear.Outtake;
import com.team687.frc2017.commands.gear.SpinSpeed;
import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.constants.GearIntakeConstants;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * @author tedlin
 * 
 */

public class OI {

    public Joystick driveJoyLeft = new Joystick(0);
    public Joystick driveJoyRight = new Joystick(1);
    public Joystick articJoy = new Joystick(2);

    // buttons on driveJoyLeft
    public JoystickButton testSensors_9;
    public JoystickButton testMinRotPower_7;
    public JoystickButton liveVisionTrack_1;

    public JoystickButton stopShooting_2;
    public JoystickButton wantToShoot_5;

    public JoystickButton tankDrive_10;
    public JoystickButton cheesyDrive_11;

    // buttons on driveJoyRight
    public JoystickButton pivotToAngle_9;
    public JoystickButton clearAll_8;
    public JoystickButton quickTurn_7;

    public JoystickButton snapToTarget_2;
    public JoystickButton turnToAngle_5;
    public JoystickButton shiftDown_4;

    public JoystickButton approachTarget_10;
    public JoystickButton arcTurn_11;

    public JoystickButton shiftUp_3;

    public OI() {
	// pivotToAngle_9 = new JoystickButton(driveJoyRight, 9);
	// pivotToAngle_9.whenPressed(new PivotToAngle(170, false, false));
	clearAll_8 = new JoystickButton(driveJoyRight, 8);
	clearAll_8.cancelWhenPressed(Robot.drive.getCurrentCommand());
	// quickTurn_7 = new JoystickButton(driveJoyRight, 7);

	// snapToTarget_2 = new JoystickButton(driveJoyRight, 2);
	// snapToTarget_2.whenPressed(new SnapToTarget(15));
	// turnToAngle_5 = new JoystickButton(driveJoyRight, 5);
	// turnToAngle_5.whenPressed(new TurnToAngle(90, 15));
	shiftDown_4 = new JoystickButton(driveJoyRight, 4);
	shiftDown_4.whenPressed(new ShiftDown());

	// approachTarget_10 = new JoystickButton(driveJoyRight, 10);
	// approachTarget_10.whenPressed(new ApproachTarget());
	// arcTurn_11 = new JoystickButton(driveJoyRight, 11);
	// arcTurn_11.whenPressed(new ArcTurn(90, 0.254));

	shiftUp_3 = new JoystickButton(driveJoyRight, 3);
	shiftUp_3.whenPressed(new ShiftUp());

	// testSensors_9 = new JoystickButton(driveJoyLeft, 9);
	// testSensors_9.whenPressed(new TestSensors());
	// testMinRotPower_7 = new JoystickButton(driveJoyLeft, 7);
	// testMinRotPower_7.whenPressed(new TestMinRotPower());
	liveVisionTrack_1 = new JoystickButton(driveJoyLeft, 1);
	liveVisionTrack_1.whenPressed(new LiveVisionTracking());

	// tankDrive_10 = new JoystickButton(driveJoyLeft, 10);
	// tankDrive_10.whenPressed(new TankDrive());
	// cheesyDrive_11 = new JoystickButton(driveJoyLeft, 11);
	// cheesyDrive_11.whenPressed(new CheesyDrive());

	// Smart Dashboard buttons
	SmartDashboard.putData("Tank Drive", new TankDrive());

	SmartDashboard.putData("Reset Gyro", new ResetGyro());
	SmartDashboard.putData("Reset Encoders", new ResetEncoders());

	SmartDashboard.putData("Shift Up", new ShiftUp());
	SmartDashboard.putData("Shift Down", new ShiftDown());
	SmartDashboard.putData("Drive until Collision", new DriveUntilCollision(0.971, 0.987));

	SmartDashboard.putData("Rotate to SmartDashboard angle (test)",
		new TurnToAngle(SmartDashboard.getNumber("******Desired Angle*******", 0)));
	SmartDashboard.putData("Rotate to 90", new TurnToAngle(DriveConstants.BlueWallToHopperArcTurnAngle));
	SmartDashboard.putData("Arc to 90", new ArcTurn(DriveConstants.BlueWallToHopperArcTurnAngle, true, 0));

	SmartDashboard.putData("Live Vision Tracking", new LiveVisionTracking());
	SmartDashboard.putData("Snap To Target Auto", new SnapToTarget(true));

	SmartDashboard.putData("Gear Intake Down", new IntakeSetPosition(GearIntakeConstants.kGearIntakeDownPos));
	SmartDashboard.putData("Gear Intake Up Hold", new IntakeTuckRetain());
	SmartDashboard.putData("Outtake gear", new Outtake());
	SmartDashboard.putData("Intake gear", new SpinSpeed(GearIntakeConstants.kGearIntakeSpinVoltage));

	SmartDashboard.putData("Blue 973 Hopper Auto", new BlueHopperShootAuto973());
	// SmartDashboard.putData("Blue 1678 Hopper Auto", new
	// BlueHopperShootAuto1678());
	// SmartDashboard.putData("Blue 2056 Hopper Auto", new
	// BlueHopperShootAuto2056());
	// SmartDashboard.putData("Blue 254 Gear + Hopper Auto", new
	// BlueGearHopperShootAuto254());
	SmartDashboard.putData("Blue Center Peg Gear Auto", new BlueCenterPegGearAuto());
	SmartDashboard.putData("Blue Center Peg Two Gear Auto", new BlueCenterPegTwoGearAuto());
	SmartDashboard.putData("Blue Center Peg Gear + Shoot Auto", new BlueCenterPegGearShootAuto());
	// SmartDashboard.putData("Red 973 Hopper Auto", new RedHopperShootAuto973());
	// SmartDashboard.putData("Red 1678 Hopper Auto", new RedHopperShootAuto1678());
	// SmartDashboard.putData("Red 2056 Hopper Auto", new RedHopperShootAuto2056());
	// SmartDashboard.putData("Red 254 Gear + Hopper Auto", new
	// RedGearHopperShootAuto254());

	// SmartDashboard.putData("Test Sensors", new TestSensors());
	// SmartDashboard.putData("Test Min Rot Power", new TestMinRotPower());
	// SmartDashboard.putData("Test Rot PID", new TestRotPID());
    }

    /**
     * @return input power from left drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyLeftY() {
	return driveJoyLeft.getY();
    }

    /**
     * @return input power from right drive joystick Y (-1.0 to +1.0)
     */
    public double getDriveJoyRightY() {
	return driveJoyRight.getY();
    }

    /**
     * @return input power from left drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyLeftX() {
	return driveJoyLeft.getX();
    }

    /**
     * @return input power from right drive joystick X (-1.0 to +1.0)
     */
    public double getDriveJoyRightX() {
	return driveJoyRight.getX();
    }

    /**
     * @return input throttle from right drive joystick (0 to +1.0)
     */
    public double getThrottleR() {
	return (driveJoyRight.getThrottle() + 1) / 2;
    }

    /**
     * @return input throttle from left drive josytick
     */
    public double getThrottleL() {
	return (driveJoyLeft.getThrottle() + 1) / 2;
    }

    /**
     * @return if quick turn state in cheesydrive mode
     */
    public boolean getQuickTurn() {
	return quickTurn_7.get();
    }

    /**
     * @return if want to shoot
     */
    public boolean wantToShoot() {
	return wantToShoot_5.get();
    }

    /**
     * @return Y of articulation joystick
     * 
     * @return
     */
    public double getArticY() {
	return articJoy.getY();
    }

}
