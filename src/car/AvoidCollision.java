package car;

import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

class AvoidCollision implements Behavior {
	private boolean suppressed = false;
	private boolean clear = true;
	public static final int MIN_DISTANCE = 20;
	
	public AvoidCollision() {
	}

	private boolean isPathClear() {
		int distance = Car.irSensor.getDistance();

		if (distance < MIN_DISTANCE) {
			Button.LEDPattern(2);
			clear = false;
		} else {
			Button.LEDPattern(1);
			clear = true;
		}
		
		return clear;
	}

	public boolean takeControl() {
		return !isPathClear();
	}

	public void suppress() {
		Car.driveMotor.setAcceleration(Car.defaultAcceleration);
		suppressed = true;
	}

	public void action() {
		suppressed = false;
		
		Car.driveMotor.setAcceleration(400);
		Car.driveMotor.stop();
		
	}

}