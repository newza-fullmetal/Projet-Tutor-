package actuator;

import constants.Constants;

public class LampActuator {
	private upm_grove.GroveRelay relay;
	private upm_grove.GroveLed led;
	
	public LampActuator() {
		super();
		// TODO Auto-generated constructor stub
		relay = new upm_grove.GroveRelay(Constants.LampPort);
		 led = new upm_grove.GroveLed(Constants.LampPort);
	}
	public void turnOn(){
		System.out.println(" Lamp On \n");
		relay.on();
		led.on();
	}
	public void turnOff(){
		System.out.println(" Lamp Off \n");
		relay.off();
		led.off();
	}
	
}
