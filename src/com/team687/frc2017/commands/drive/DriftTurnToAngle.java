package com.team687.frc2017.commands.drive;

import com.team687.frc2017.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * An arc turn that drift turns to an angle near the end of specified distance
 * 
 * @author tedlin
 *
 */

public class DriftTurnToAngle extends Command {
    
    private double m_straightPower;
    private double m_desiredAngle;
    private double m_distance;
    private double m_kRotP;
    
    public DriftTurnToAngle(double straightPower, double angle, double distance, double kRotP) {
	m_straightPower = straightPower;
	m_desiredAngle = angle;
	m_distance = distance;
	m_kRotP = kRotP;
    }
    
    @Override 
    protected void initialize() {
	SmartDashboard.putString("Current Command", "DriftTurnToAngle");
    }
    
    @Override
    protected void execute() {
	double doneness = Math.abs(Robot.drive.getDrivetrainTicks() / m_distance);
	double robotAngle = (360 - Robot.drive.getCurrentYaw()) % 360;
	double rotError = -m_desiredAngle - robotAngle;
	rotError = (rotError > 180) ? rotError - 360 : rotError;
	rotError = (rotError < -180) ? rotError + 360 : rotError;
	double rotPower = m_kRotP * rotError * doneness;
	
	Robot.drive.setPower(rotPower + m_straightPower, rotPower - m_straightPower);
    }
    
    @Override
    protected boolean isFinished() {
	return Math.abs(Robot.drive.getDrivetrainTicks()) >= m_distance;
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
