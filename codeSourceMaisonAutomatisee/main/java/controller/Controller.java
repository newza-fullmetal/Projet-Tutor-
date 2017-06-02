package controller;


import java.util.Date;

import actuator.Alarm;
import actuator.ChauffageActuator;
import actuator.LampActuator;
import constants.Constants;
import sensor.Accelerometer;
import sensor.Accelerometer.AccelerometerObserver;
import sensor.InfraredDetector;
import sensor.InfraredDetector.LocationObserver;
import sensor.MovementSensor;
import sensor.MovementSensor.MovementObserver;
import sensor.PulseSensor;
import sensor.PulseSensor.PulseObserver;
import sensor.RFIDSensor;
import sensor.RFIDSensor.RFIDObserver;
import sensor.TemperatureSensor;
import sensor.TemperatureSensor.TemperatureObserver;

public class Controller {
	private MovementSensor movementSensor;
	private Accelerometer accelerometer;
	private InfraredDetector infraredDetector1;
	private InfraredDetector infraredDetector2;
	private RFIDSensor rfidSensor;
	private TemperatureSensor temperatureSensor;
	private PulseSensor pulseSensor;
	private ChauffageActuator chauffageActuator;
	private LampActuator lampActuator;
	private Alarm pulseWarning;
	private Alarm chuteWarning;
	private Alarm intrusderDetection;
	private AccelerometerObserver accObserver;
	private LocationObserver locationObserver1;
	private LocationObserver locationObserver2;
	private TemperatureObserver tempObserver;
	private RFIDObserver	rfidObserver;
	private PulseObserver pulseObserver;
	private MovementObserver movementObserver;
	
	private boolean entrer = false;
	private boolean sortir = false;
	private boolean RFIDaccepted = false;
	
	synchronized public void setEntrer(boolean entrer) {
		this.entrer = entrer;
	}

	synchronized public void setSortir(boolean sortir) {
		this.sortir = sortir;
	}

	public Controller() {
		super();
	}

	public void init(){
		//accelerometer.init();
		//temperatureSensor.init();
		//pulseSensor.init();
		rfidSensor.init();
		movementSensor.init();
		//infraredDetector1.init();
		//infraredDetector2.init();
	}
	public void setAccelerometer(Accelerometer accelerometer) {
		this.accelerometer = accelerometer;
		accObserver = new AccelerometerObserver() {
			
			@Override
			public void doExecute() {
				// TODO Auto-generated method stub
				// envoyer des SMS vers qq d'autre
				SendSMS.sendSms("Warning : Ta maman est tombe");
				chuteWarning.start();
				
			}
		};
		this.accelerometer.addObserver(accObserver);
		
	}

	public void setInfraredDetector1(InfraredDetector infraredDetector) {
		this.infraredDetector1 = infraredDetector;
		locationObserver1 = new LocationObserver() {
			
			@Override
			public void doExecute() {
				// TODO Auto-generated method stub
				// turn on off lamp
				if(sortir){
					lampActuator.turnOff();
					setEntrer(false);
					setSortir(false);
				}
				else{
					setEntrer(true);
				}
				
			}
		};
		this.infraredDetector1.addObserver(locationObserver1);
	}
	public void setInfraredDetector2(InfraredDetector infraredDetector) {
		this.infraredDetector2 = infraredDetector;
		locationObserver2 = new LocationObserver() {
			
			@Override
			public void doExecute() {
				// TODO Auto-generated method stub
				// turn on off lamp
				if(entrer)
				{
					lampActuator.turnOn();
					setEntrer(false);
					setSortir(false);
				}
				else{
					setSortir(true);
				}
			}
		};
		this.infraredDetector2.addObserver(locationObserver2);
		
	}
	
	public void setRfidSensor(RFIDSensor rfidSensor) {
		this.rfidSensor = rfidSensor;
		rfidObserver = new RFIDObserver() {
			
			@Override
			public void doExecute() {
				RFIDaccepted = !RFIDaccepted;
				System.out.println("person in home : " + RFIDaccepted);
			}
		};
		this.rfidSensor.addObserver(rfidObserver);
	}
	
	
	public void setTemperatureSensor(TemperatureSensor temperatureSensor) {
		this.temperatureSensor = temperatureSensor;
		tempObserver = new TemperatureObserver() {
			
			@Override
			public void doExecute() {
				int temp = temperatureSensor.getTemperature();
				if(temp <= 10)
					chauffageActuator.turnOn();
				else if(temp >= 25)
					chauffageActuator.turnOff();
			}
		};
		this.temperatureSensor.addObserver(tempObserver);
	}

	
	public void setMovementSensor(MovementSensor movementSensor) {
		this.movementSensor = movementSensor;
		movementObserver = new MovementObserver() {
			
			@Override
			public void doExecute(int valeur) {
				// TODO Auto-generated method stub
				if ((valeur == Constants.DEPLACE) && !RFIDaccepted){
					System.out.println(" Instrusder detected");
					intrusderDetection.start();
					//SendSMS.sendSms("Warning : Someone is in your house");
				}
				else if((valeur == Constants.DEPLACE) && RFIDaccepted){
					lampActuator.turnOn();
				}
				else if (valeur == Constants.NONDEPLACE){
					lampActuator.turnOff();
				}
			}
		};
		this.movementSensor.addObserver(movementObserver);
	}

	public void setPulseSensor(PulseSensor pulseSensor) {
		this.pulseSensor = pulseSensor;
		pulseObserver = new PulseObserver() {
			
			@Override
			public void doExecute() {
				// TODO Auto-generated method stub
				// recevoir et afficher périoquement le pouls
				// envoyer un SMS si le pouls a problème

				int hr = pulseSensor.getHeartRate();
					pulseWarning.start();
					Date date = new Date();
					SendSMS.sendSms("Warning! Heart rate at "+date.toString()+" : "+pulseSensor.getHeartRate());
	
			}
		};
		this.pulseSensor.addObserver(pulseObserver);
	}

	public void setChauffageActuator(ChauffageActuator chauffageActuator) {
		this.chauffageActuator = chauffageActuator;
	}

	public void setLampActuator(LampActuator lampActuator) {
		this.lampActuator = lampActuator;
	}

	public void setPulseWarning(Alarm pulseWarning) {
		this.pulseWarning = pulseWarning;
	}

	public void setChuteWarning(Alarm chuteWarning) {
		this.chuteWarning = chuteWarning;
	}

	public void setIntrusderDetection(Alarm intrusderDetection) {
		this.intrusderDetection = intrusderDetection;
	}

}
