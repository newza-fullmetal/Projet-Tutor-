package controller;



import java.io.IOException;
import java.util.Date;

import actuator.Alarm;
import actuator.ChauffageActuator;
import actuator.LampActuator;
import constants.Constants;
import sensor.Accelerometer;
import sensor.InfraredDetector;
import sensor.MovementSensor;
import sensor.PulseSensor;
import sensor.PulseSensor.PulseObserver;
import sensor.RFIDSensor;
import sensor.TemperatureSensor;

public class CycleManager {

	public static void main(String[] args) {
		Controller controller = new Controller();
		Accelerometer accelerometer = new Accelerometer();
		InfraredDetector infraredDetector1 = new InfraredDetector(Constants.InfraredPort1); // close the door
		InfraredDetector infraredDetector2 = new InfraredDetector(Constants.InfraredPort2); // far from the door
		RFIDSensor rfidSensor = new RFIDSensor();
		TemperatureSensor temperatureSensor = new TemperatureSensor();
		PulseSensor pulseSensor = new PulseSensor();
		MovementSensor movementSensor = new MovementSensor(Constants.movementPort);
		Alarm pulseWarning = new Alarm(1,1000);
		Alarm chuteWarning =  new Alarm(3,1000);
		Alarm intrusderDetection = new Alarm(5, 500);
		ChauffageActuator chauffageActuator = new ChauffageActuator();
		LampActuator lampActuator = new LampActuator();
		
		
		controller.setAccelerometer(accelerometer);
		controller.setChauffageActuator(chauffageActuator);
		controller.setInfraredDetector1(infraredDetector1);
		controller.setInfraredDetector2(infraredDetector2);
		controller.setLampActuator(lampActuator);
		controller.setPulseWarning(pulseWarning);
		controller.setChuteWarning(chuteWarning);
		controller.setIntrusderDetection(intrusderDetection);
		controller.setRfidSensor(rfidSensor);
		controller.setTemperatureSensor(temperatureSensor);
		controller.setPulseSensor(pulseSensor);
		controller.setMovementSensor(movementSensor);
		controller.init();
		while(true){
			
		}

	}

}
