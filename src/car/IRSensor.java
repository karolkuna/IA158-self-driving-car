package car;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

class IRSensor extends Thread {
	
	private EV3IRSensor ir = new EV3IRSensor(SensorPort.S1);
	private SampleProvider sp = ir.getDistanceMode();
	private int distance = 255;

	public IRSensor() {
	}

	public void run() {
		while (true) {
			float[] sample = new float[sp.sampleSize()];
			sp.fetchSample(sample, 0);
			distance = (int) sample[0];
		}
	}

	public int getDistance() {
		return distance;
	}
}
