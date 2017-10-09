package com.team687.frc2017.commands.drive;

import java.util.ArrayList;

import com.team687.frc2017.Robot;
import com.team687.frc2017.constants.DriveConstants;
import com.team687.frc2017.utilities.BezierCurve;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive a path generated from Bezier curve
 *
 * @author tedlin
 *
 */

public class DriveBezierRio extends Command {

    private BezierCurve m_path;
    private double m_basePower = 1; // 1.0 is max PercentVBus output
    private double m_straightPower;
    private boolean m_straightPowerIsDynamic;
    private boolean m_softStop;

    private ArrayList<Double> m_headingList;
    private double m_desiredHeading;
    private ArrayList<Double> m_arcLengthList;

    private int m_pathSegmentCounter;
    private boolean m_pathIsFinished;
    private double m_direction;

    public DriveBezierRio(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3,
	    double straightPower, boolean straightPowerIsDynamic, boolean softStop) {
	m_path = new BezierCurve(x0, y0, x1, y1, x2, y2, x3, y3);
	m_straightPower = straightPower;
	m_straightPowerIsDynamic = straightPowerIsDynamic;
	m_softStop = softStop;
    }

    /**
     * 
     * @param path
     * @param straightPower
     *            (postive if going forward (forward is side with climber), negative
     *            if going backwards)
     * @param straightPowerIsDynamic
     *            (true for paths with sharp turns)
     * @param softStop
     *            (if you want to slow down near end)
     */
    public DriveBezierRio(double[] path, double straightPower, boolean straightPowerIsDynamic, boolean softStop) {
	m_path = new BezierCurve(path[0], path[1], path[2], path[3], path[4], path[5], path[6], path[7]);
	m_straightPower = straightPower;
	m_straightPowerIsDynamic = straightPowerIsDynamic;
	m_softStop = softStop;
    }

    @Override
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriveBezierRio");
	Robot.drive.stopDrive();
	Robot.drive.resetEncoders();
	Robot.drive.shiftUp();

	m_path.calculateBezier();
	m_headingList = m_path.getHeadingList();
	m_arcLengthList = m_path.getArcLengthList();

	m_pathSegmentCounter = 0;
	m_pathIsFinished = false;
	m_direction = Math.signum(m_straightPower);
    }

    @Override
    protected void execute() {
	if (m_pathSegmentCounter < m_arcLengthList.size()) {
	    if (Math.abs(Robot.drive.getDrivetrainTicks()) < m_arcLengthList.get(m_pathSegmentCounter)) {
		double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
		m_desiredHeading = m_headingList.get(m_pathSegmentCounter); // TOOD: figure out if we have to modify
									    // this value when going reverse

		m_desiredHeading = -m_desiredHeading; // Reversing desired heading is always necessary because of how
						      // our rotational PID is structured.
		double headingError = m_desiredHeading - robotAngle;
		headingError = (headingError > 180) ? headingError - 360 : headingError;
		headingError = (headingError < -180) ? headingError + 360 : headingError;

		double rotPower = DriveConstants.kRotPBezier * headingError;
		double straightPower = m_straightPower; // default is specified straight power

		if (m_straightPowerIsDynamic) {
		    straightPower = m_direction * m_basePower
			    / (Math.abs(headingError) * DriveConstants.kStraightPowerAdjuster);
		}

		double maxStraightPower = DriveConstants.kMaxStraightPower;
		if (m_softStop) {
		    double straightErrorFromEnd = m_arcLengthList.get(m_arcLengthList.size() - 1)
			    - Math.abs(Robot.drive.getDrivetrainTicks());
		    double softMaxStraightPower = DriveConstants.kDistPBezier * straightErrorFromEnd;
		    maxStraightPower = Math.min(Math.abs(maxStraightPower), Math.abs(softMaxStraightPower));
		}

		// limit straight power to soft stop and to maintain straight:rotational ratio
		if (Math.abs(straightPower) > maxStraightPower) {
		    straightPower = maxStraightPower * m_direction;
		}
		// make sure robot reaches end point
		if (Math.abs(straightPower) < DriveConstants.kMinStraightPower) {
		    straightPower = DriveConstants.kMinStraightPower * m_direction;
		}

		double leftPow = rotPower + straightPower;
		double rightPow = rotPower - straightPower;
		Robot.drive.setPower(leftPow, rightPow);
	    } else {
		m_pathSegmentCounter++;
	    }
	} else {
	    m_pathIsFinished = true;
	}
    }

    @Override
    protected boolean isFinished() {
	return m_pathIsFinished
		|| Math.abs(Robot.drive.getDrivetrainTicks()) >= m_arcLengthList.get(m_arcLengthList.size() - 1);
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
