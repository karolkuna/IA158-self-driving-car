package car;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Car {

	static RegulatedMotor driveMotor = MirrorMotor.invertMotor(Motor.A);
	static RegulatedMotor driveControlMotor = Motor.B;
	static RegulatedMotor sensorBaseMotor = Motor.C;
	static IRSensor irSensor;
	static ColorSensor colorSensor;
	
	static int defaultAcceleration = 40;

	public static void main(String[] args) {
		driveMotor.resetTachoCount();
		driveMotor.rotateTo(0);
		driveMotor.setSpeed(200);
		driveMotor.setAcceleration(defaultAcceleration);
		
		driveControlMotor.setAcceleration(200);
		driveControlMotor.rotateTo(0);

		sensorBaseMotor.rotateTo(0);
		sensorBaseMotor.setAcceleration(1000);
		
		irSensor = new IRSensor();
		irSensor.setDaemon(true);
		irSensor.start();
		
		colorSensor = new ColorSensor();
		colorSensor.setDaemon(true);
		colorSensor.start();

		Behavior b1 = new Drive();
		Behavior b2 = new AvoidCollision();

		Behavior[] behaviorList = { b1, b2 }; // priority in increasing order 

		Arbitrator arbitrator = new Arbitrator(behaviorList);

		Button.waitForAnyPress();
		arbitrator.go();
	}
}
