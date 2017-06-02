package sensor;

import java.util.ArrayList;

import constants.Constants;


public class TemperatureSensor {
	private ArrayList<TemperatureObserver> listObservers = new ArrayList<TemperatureObserver>();
	private int temperature;
	
	
	
	

	public TemperatureSensor() {}
	
	public void init(){
		new Thread(){
			public void run(){
				while(!this.isInterrupted()){
				try {
					
					temperature = temperature(Constants.TemperaturePort);
					
					notifyObserver();
					Thread.sleep(5000);
				} catch (InterruptedException e) {}
			}
			}
		}.start();
	}
	
	public int temperature(int port) throws InterruptedException {
		upm_grove.GroveTemp temp = new upm_grove.GroveTemp(port);
		int celsius = temp.value();
		int fahrneheit = (celsius -100)* 2 + 32;

		System.out.println("Celsius: " + (celsius - 100));
		System.out.println("Fahrneheit: " + fahrneheit);
		temp.delete();
		return celsius - 100;
	}
	
	
	public int getTemperature() {
		return temperature;
	}

	private void notifyObserver() {
		for(TemperatureObserver obs : listObservers){
			obs.doExecute();
		}
	}
	public void addObserver(TemperatureObserver obs){
		if(!listObservers.contains(obs)){
			listObservers.add(obs);
		}
	}
	public void removeObserver(TemperatureObserver obs){
		if(listObservers.contains(obs)){
			listObservers.remove(obs);
		}
	}
	public interface TemperatureObserver{
		public void doExecute();
	}
}
