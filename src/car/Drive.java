package car;

import lejos.robotics.subsumption.Behavior;

class Drive implements Behavior {

	private boolean suppressed = false;

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;

		while (!suppressed) {
			Car.driveMotor.forward();
			Thread.yield();
		}
	}
}
