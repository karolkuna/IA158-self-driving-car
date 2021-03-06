package car;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensor extends Thread {
	public int lineAngle = 0;
	
	private EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S2);
	private int color = Color.NONE;
	private boolean wasDetected = false;
	private int firstDetectedAngle = 0;
	
	
	
	public ColorSensor() {	
	}
	
	public void run() {
		while (true) {
			// color sensor is rotating periodically from left to right
			// the point of that is to find where the red line is 
			color = sensor.getColorID();
			if (color == Color.RED) {
				if (!wasDetected) {
					// sensor was moved above the line, remember the angle
					firstDetectedAngle = Car.sensorBaseMotor.getTachoCount();
					wasDetected = true;
				}
			} else {
				if (wasDetected) {
					// sensor was moved away from the line
					// line angle is calculated as point between edges of the line
					int currentAngle = Car.sensorBaseMotor.getTachoCount();
					lineAngle = (currentAngle + firstDetectedAngle) / 2;
					wasDetected = false;
				}
			}

			Thread.yield();
		}
	}
	
	public int getColor() {
		return color;
	}
}
