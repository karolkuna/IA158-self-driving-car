package car;

import lejos.robotics.Color;
import lejos.robotics.subsumption.Behavior;

class Drive implements Behavior {

	private boolean suppressed = false;
	//private int driveAngle = 0;

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
		Car.driveMotor.stop();
	}

	public void action() {
		suppressed = false;

		while (!suppressed) {
			Car.driveMotor.forward();
			//int limitAngle = Car.driveControlMotor.getLimitAngle();
			//System.out.println(limitAngle);
			switch (Car.colorSensor.getColor()) {
				
				case Color.RED : // turn left
					Car.driveControlMotor.rotateTo(30);
					//driveAngle = 30;
					break;
				case Color.GREEN : // turn right
					Car.driveControlMotor.rotateTo(-30);
					//driveAngle = -30;
					break;
				default:
					Car.driveControlMotor.rotateTo(0);
					//driveAngle = 0;
					break;
			}
			
			Thread.yield();
		}
	}
}
