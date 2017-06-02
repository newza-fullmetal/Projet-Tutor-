package actuator;

import constants.Constants;

public class ChauffageActuator {
	private upm_grove.GroveRelay relay;
	
	public ChauffageActuator() {
		super();
		// TODO Auto-generated constructor stub
		relay = new upm_grove.GroveRelay(Constants.ChauffagePort);
	}
	public void turnOn(){
		System.out.println(" Heat on \n");
		relay.on();
	}
	public void turnOff(){
		System.out.println(" Heat off \n");
		relay.off();
	}
	
}
