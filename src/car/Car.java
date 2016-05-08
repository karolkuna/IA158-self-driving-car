package car;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Car {

	static RegulatedMotor driveMotor = MirrorMotor.invertMotor(Motor.A);
	static IRSensor sensor;

	public static void main(String[] args) {
		driveMotor.resetTachoCount();
		driveMotor.rotateTo(0);
		driveMotor.setSpeed(400);
		driveMotor.setAcceleration(800);

		sensor = new IRSensor();
		sensor.setDaemon(true);
		sensor.start();

		Behavior b1 = new Drive();
		Behavior b2 = new DetectCollision();

		Behavior[] behaviorList = { b1, b2 };

		Arbitrator arbitrator = new Arbitrator(behaviorList);

		Button.waitForAnyPress();
		arbitrator.go();
	}
}
