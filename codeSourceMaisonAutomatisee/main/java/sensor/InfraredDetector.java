package sensor;

import java.util.ArrayList;

import constants.Constants;
import upm_rpr220.RPR220;

public class InfraredDetector {
	private ArrayList<LocationObserver> listObservers = new ArrayList<LocationObserver>();
	private int port;
	public InfraredDetector(int port) {
		this.port = port;
	}
	public void init(){
		new Thread(){
			public void run(){
				while(!this.isInterrupted()){
					try {
						Thread.sleep(2000);
						irDetecte(port);
					} catch (InterruptedException e) {
						
					}
				}
			}
		}.start();
	}
	
	public void irDetecte(int port) throws InterruptedException {
		RPR220 sensor = new RPR220(port);
			if (sensor.blackDetected()){
                //System.out.println("Black detected");
			}
            else{
                System.out.println("Someone come across"); // cos nguoi di qua
                notifyObserver();
                Thread.sleep(10000);
            }
	}

	private void notifyObserver() {
		for(LocationObserver obs : listObservers){
			obs.doExecute();
		}
		
	}
	
	public void addObserver(LocationObserver obs){
		if(!listObservers.contains(obs)){
			listObservers.add(obs);
		}
	}
	public void removeObserver(LocationObserver obs){
		if(listObservers.contains(obs)){
			listObservers.remove(obs);
		}
	}
	public interface LocationObserver{
		public void doExecute();
	}
}
