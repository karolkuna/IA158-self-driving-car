package car;

import lejos.robotics.subsumption.Behavior;


class Drive implements Behavior {
	
	private boolean suppressed = false;
	private boolean forward = true;
	
	private PIDController pid;
	
	public Drive() {
		pid = new PIDController(-1.0f, 0f, 0f);
	}

	public boolean takeControl() {
		return true;
	}

	public void suppress() {
		suppressed = true;
	}

	public void action() {
		suppressed = false;
		
		Car.driveMotor.forward();
		
		while (!suppressed) {		
			if (forward && Car.sensorBaseMotor.getTachoCount() > 60) {
				forward = false;
			} else if (!forward && Car.sensorBaseMotor.getTachoCount() < -60){
				forward = true;
			}
			
			if (forward) {
				Car.sensorBaseMotor.rotateTo(63, true);
			} else {
				Car.sensorBaseMotor.rotateTo(-63, true);
			}
			
			// drive
			
			float angle = Car.colorSensor.lineAngle;
			// rotating base is not exactly in the center, so the angle needs to be compensated for that
			if (angle < 0) {
				angle = angle * 0.69f; // it could be calculated precisely, but this is good enough
			}
			
			float targetAngle = pid.control(Car.colorSensor.lineAngle);
			targetAngle = Math.min(70, Math.max(targetAngle, -70));
			
			//Car.driveControlMotor.rotate((int)targetAngle, true);
			Car.driveControlMotor.rotateTo((int)targetAngle, true);
			
			Thread.yield();
		}
	}

}