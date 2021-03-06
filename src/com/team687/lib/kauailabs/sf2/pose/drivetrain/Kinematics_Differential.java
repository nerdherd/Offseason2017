/* ============================================
SF2 source code is placed under the MIT license
Copyright (c) 2017 Kauai Labs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
===============================================
*/

package com.team687.lib.kauailabs.sf2.pose.drivetrain;

import java.util.List;

import com.team687.lib.kauailabs.sf2.orientation.Quaternion;
import com.team687.lib.kauailabs.sf2.pose.Pose;
import com.team687.lib.kauailabs.sf2.quantity.Scalar;
import com.team687.lib.kauailabs.sf2.time.Timestamp;
import com.team687.lib.kauailabs.sf2.time.TimestampedValue;

public class Kinematics_Differential implements IDriveTrainKinematics {

    TimestampedValue<Quaternion> last_quat;
    DriveTrainParameters drive_params;

    final int X = 0;
    final int Y = 1;
    final int ROT = 2;

    static int LEFT_WHEEL = 0;
    static int RIGHT_WHEEL = 1;
    static int LEFT_SECOND_WHEEL = 2;
    static int RIGHT_SECOND_WHEEL = 3;

    static int NUM_WHEELS = 2;

    int num_drive_wheels;

    TimestampedValue<Quaternion> q_diff_temp;
    float enc_based_pose_change[];
    Scalar temp;

    public Kinematics_Differential(DriveTrainParameters drive_params) {

	this.last_quat = new TimestampedValue<Quaternion>(new Quaternion());
	this.drive_params = drive_params;
	/* Allocate memory for working variables. */
	this.enc_based_pose_change = new float[3];
	this.q_diff_temp = new TimestampedValue<Quaternion>(new Quaternion());

	if ((this.drive_params.getNumDriveWheels() != 2) && (this.drive_params.getNumDriveWheels() != 4)) {
	    throw new IllegalArgumentException(
		    "Kinematics_Differential requires" + "exactly 2 or 4 wheels be used.  These wheels correspond"
			    + "to either the left/right wheels in the center row of "
			    + "a 6-wheeled drive system, or the pair of left and"
			    + "pair of right wheels in the middle of a 8-wheeled drive " + "system.");
	}

	num_drive_wheels = this.drive_params.getNumDriveWheels();
	temp = new Scalar();
    }

    @Override
    public DriveTrainParameters getDriveTrainParameters() {
	return this.drive_params;
    }

    @Override
    /*
     * Note: Input drive wheel distances are in units of inches since the last time
     * step()
     */
    /* was invoked. */
    /*
     * Note: Input drive/steer wheel values are ordered from left front corner,
     * increasing clockwise
     */
    /*
     * Return: Returns a TimestampedPose object representing the change in pose
     * since pose_last.
     */
    /*
     * Note: the individual drive/steer wheel values are assumed to be measured
     * coincident with the
     */
    /* current TimestampedQuaternion. */
    public boolean step(Timestamp system_timestamp, TimestampedValue<Pose> pose_last,
	    TimestampedValue<Quaternion> quat_curr, List<TimestampedValue<Scalar>> drive_wheel_distance_delta_curr,
	    List<TimestampedValue<Scalar>> steer_wheel_angle_degrees_curr,
	    List<TimestampedValue<Scalar>> drive_motor_current_amps_curr, TimestampedValue<Pose> pose_curr_out) {

	Quaternion.difference(quat_curr.getValue(), pose_last.getValue().getOrientation(), q_diff_temp.getValue());
	long delta_t = quat_curr.getTimestamp() - pose_last.getTimestamp();
	q_diff_temp.set(q_diff_temp.getValue(), delta_t);

	float avg_inches = 0;
	for (int i = 0; i < this.num_drive_wheels; i++) {
	    avg_inches += drive_wheel_distance_delta_curr.get(i).getValue().get();
	}
	avg_inches /= this.num_drive_wheels;

	q_diff_temp.getValue().getYawRadians(temp);
	double theta = temp.get();

	/*
	 * Adjust X/Y offset deltas, currently in body frame, to be in World frame - by
	 * rotating these values by the current body rotation
	 */

	enc_based_pose_change[X] = avg_inches * (float) Math.sin(theta);
	enc_based_pose_change[Y] = avg_inches * (float) Math.cos(theta);

	/* Use Encoder-derived values for Translational Motion */
	pose_curr_out.getValue().addOffsets(enc_based_pose_change[X], enc_based_pose_change[Y]);

	/* Use IMU-derived values for orientation. */
	pose_curr_out.getValue().getOrientation().copy(quat_curr.getValue());

	return true;
    }
}
