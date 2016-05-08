package car;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;

public class ColorSensor extends Thread {

	private EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S1);
	private int color = Color.NONE;
	
	public ColorSensor() {	
	}
	
	public void run() {
		while (true) {
			color = sensor.getColorID();
		}
	}
	
	public int getColor() {
		return color;
	}
}
