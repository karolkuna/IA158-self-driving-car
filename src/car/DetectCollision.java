package car;

import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

class DetectCollision implements Behavior {

	public static final int MIN_DISTANCE = 30;
	
	public DetectCollision() {
	}

	private boolean checkDistance() {
		int distance = Car.irSensor.getDistance();

		if (distance < MIN_DISTANCE) {
			Button.LEDPattern(2);
			return true;
		} else {
			Button.LEDPattern(1);
			return false;
		}
	}

	public boolean takeControl() {
		return checkDistance();
	}

	public void suppress() {
	}

	public void action() {
	}

}