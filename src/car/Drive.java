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
			// move sensor base from left to right (-60 to 60 degrees)
			if (forward && Car.sensorBaseMotor.getTachoCount() > 60) {
				forward = false;
			} else if (!forward && Car.sensorBaseMotor.getTachoCount() < -60){
				forward = true;
			}
			
			// precision of motors is 2 degrees, rotate target with 3 degrees margin to make sure it overshoots  
			if (forward) {
				Car.sensorBaseMotor.rotateTo(63, true);
			} else {
				Car.sensorBaseMotor.rotateTo(-63, true);
			}
			
			// line following
			float angle = Car.colorSensor.lineAngle; // line angle estimate from rotating color sensor
			// rotating base is not exactly in the center, so the angle needs to be compensated
			if (angle < 0) {
				angle = angle * 0.69f; // it could be calculated more precisely, but this is good enough
			}
			
			// get corrective angle of wheels from PID controller
			float targetAngle = pid.control(Car.colorSensor.lineAngle);
			targetAngle = Math.min(70, Math.max(targetAngle, -70)); // limit it to -70 to 70 deg 
			
			//Car.driveControlMotor.rotate((int)targetAngle, true);
			Car.driveControlMotor.rotateTo((int)targetAngle, true);
			
			Thread.yield();
		}
	}

}