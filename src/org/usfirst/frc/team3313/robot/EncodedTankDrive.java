package org.usfirst.frc.team3313.robot;

import edu.wpi.first.wpilibj.*;

public class EncodedTankDrive {

	private Spark leftMotor;
	private Spark rightMotor;
	private Encoder leftEncod;
	private Encoder rightEncod;
	private double RIGHT_SCALE = .978;
	double Kp = 0.03;

	public EncodedTankDrive(Spark left, Spark right, Encoder leftEncod, Encoder rightEncod) {
		this.leftMotor = left;
		this.rightMotor = right;
		this.leftEncod = leftEncod;
		this.rightEncod = rightEncod;
	}

	/**
	 * Manual tank drive method. Use AdvancedDrive in Robot.java during TeleOp
	 */
	public void tankDrive(double speedLeft, double speedRight) {
		leftMotor.set(speedLeft);
		rightMotor.set(speedRight);
	}

	/**
	 *
	 * @param speed    between -1.0 and 1.0
	 * @param distance in inches, limit to 1 decimal place
	 * @return true
	 */
	public boolean driveStraight(double speed, double distance) {
		leftEncod.reset();
		rightEncod.reset();
		leftMotor.set(speed);
		rightMotor.set(-speed * RIGHT_SCALE);
		boolean left = false;
		while (!(left == true)) {
			if (Math.abs(leftEncod.getDistance()) >= ((distance / 4) * 3)) {
				if (Math.abs(leftEncod.getDistance()) >= ((distance / 10) * 9)) {
					if (speed > .75) {
						leftMotor.set(speed / 3);
						rightMotor.set(speed / 3);
					}
				} else {
					if (speed > .75) {
						leftMotor.set(speed / 2);
						rightMotor.set(speed / 2);
					}
				}
			}
			if (Math.abs(leftEncod.getDistance()) >= distance) {
				leftMotor.set(0);
				left = true;
			}
		}
		leftMotor.set(0);
		rightMotor.set(0);

		while (!this.leftEncod.getStopped() && !rightEncod.getStopped()) {
		}
		return true;
	}

	/**
	 * Make a turn without any forward motion
	 * 
	 * @param speed    The amount of force applied to the motors; ranges from 0 to 1
	 * @param distance How long to turn for. Due to the gyroscope not functioning
	 *                 properly, this is mostly trial and error
	 */
	public void driveTurn(double speed, double distance) {
		leftEncod.reset();
		rightEncod.reset();
		leftMotor.set(speed);
		rightMotor.set(speed * RIGHT_SCALE);
		boolean left = false;
		while (!(left == true)) {
			if (Math.abs(leftEncod.getDistance()) >= distance) {
				leftMotor.set(0);
				rightMotor.set(0);
				left = true;
			}
		}
		leftMotor.set(0);
		rightMotor.set(0);

		while (!this.leftEncod.getStopped() && !rightEncod.getStopped()) {
		}
	}

	/**
	 * Attempt to brake the motors by setting the speed in the opposite direction
	 * for .2 seconds.
	 * 
	 * @param force The about of force to push back against the motors, too much
	 *              jerk could case harm to the motors. USE WITH CAUTION.
	 */
	public void brakeMotors(double force) {
		rightMotor.set(rightMotor.get() > 0 ? -force : force);
		leftMotor.set(leftMotor.get() > 0 ? -force : force);
		Timer.delay(.2);
		leftMotor.set(0);
		rightMotor.set(0);
	}

}
