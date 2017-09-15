package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.utilities.NerdyMath;
import com.team687.frc2017.utilities.NerdyPID;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Pure PID distance control
 *
 * @author tedlin
 *
 */

public class DriveDistancePID extends Command {

    private double m_leftDistance;
    private double m_rightDistance;
    private NerdyPID m_leftDistPID;
    private NerdyPID m_rightDistPID;
    private NerdyPID m_rotPID;

    private double m_counter = 0;

    /**
     * @param leftDistance
     * @param rightDistance
     */
    public DriveDistancePID(double leftDistance, double rightDistance) {
	m_leftDistance = leftDistance;
	m_rightDistance = rightDistance;

	// subsystem dependencies
	requires(Robot.drive);
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveDistancePID");

	m_leftDistPID = new NerdyPID();
	m_leftDistPID.setPID(DriveConstants.kDistP, DriveConstants.kDistI, DriveConstants.kDistD);
	m_leftDistPID.setOutputRange(DriveConstants.kMinDistPowerLowGear, DriveConstants.kMaxDistPowerLowGear);
	m_leftDistPID.setGyro(false);
	m_leftDistPID.setDesired(m_leftDistance);

	m_rightDistPID = new NerdyPID();
	m_rightDistPID.setPID(DriveConstants.kDistP, DriveConstants.kDistI, DriveConstants.kDistD);
	m_rightDistPID.setOutputRange(DriveConstants.kMinDistPowerLowGear, DriveConstants.kMaxDistPowerLowGear);
	m_rightDistPID.setGyro(false);
	m_rightDistPID.setDesired(m_rightDistance);

	m_rotPID = new NerdyPID();
	m_rotPID.setPID(DriveConstants.kRotPLowGear, DriveConstants.kRotI, DriveConstants.kRotD);
	m_rotPID.setOutputRange(DriveConstants.kMinDistPowerLowGear, DriveConstants.kMaxDistPowerLowGear);
	m_rotPID.setGyro(true);
	m_rotPID.setDesired(Robot.drive.getCurrentYaw());

	Robot.drive.resetEncoders();
	Robot.drive.stopDrive();
	Robot.drive.shiftDown();
    }

    @Override
    protected void execute() {
	double lPow = m_leftDistPID.calculate(Robot.drive.getLeftTicks());
	double rPow = m_rightDistPID.calculate(Robot.drive.getRightTicks());
	double rotPow = m_rotPID.calculate(Robot.drive.getCurrentYaw());

	double[] pow = { rotPow + lPow, rotPow - rPow };
	pow = NerdyMath.normalize(pow, false);
	Robot.drive.setPower(pow[0], pow[1]);

	if (Math.abs(Robot.drive.getLeftTicks() - m_leftDistance) <= DriveConstants.kDriveDistanceTolerance
		&& Math.abs(Robot.drive.getRightTicks() - m_rightDistance) <= DriveConstants.kDriveDistanceTolerance) {
	    m_counter += 1;
	} else {
	    m_counter = 0;
	}
    }

    @Override
    protected boolean isFinished() {
	return m_counter > DriveConstants.kDriveDistanceOscillationCount;
    }

    @Override
    protected void end() {
	Robot.drive.stopDrive();
    }

    @Override
    protected void interrupted() {
	end();
    }

}
