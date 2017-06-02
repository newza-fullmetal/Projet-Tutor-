package sensor;

import java.util.ArrayList;

import constants.Constants;
import mraa.Dir;
import mraa.Gpio;


public class MovementSensor {

	private ArrayList<MovementObserver> listObservers = new ArrayList<MovementObserver>();
	private int port;
	public MovementSensor(int port) {
		this.port = port;
	}
	
	public void init(){
		new Thread(){
			public void run(){
				try {
					montionSensor(port);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void montionSensor(int port) throws InterruptedException {
        Gpio gpio = new Gpio(port);
        gpio.dir(Dir.DIR_IN);
        int cpt = 0;
        while (true) {
        	if (gpio.read()==1) {
        		System.out.println("Personne detected ! \n");
        		notifyObserver(Constants.DEPLACE);
        		cpt = 0;
        		Thread.sleep(10000);
        	}
        	else {
        		if(cpt == 5){
        			notifyObserver(Constants.NONDEPLACE);
        			cpt = 0;
        			Thread.sleep(1000);
        		}
        		else {
        			cpt ++;
        			Thread.sleep(1000);
        		}
        		
        	}
            
        }
		
	}
	
	private void notifyObserver(int valeur) {
		for(MovementObserver obs : listObservers){
			obs.doExecute(valeur);
		}
		
	}
	
	public void addObserver(MovementObserver obs){
		if(!listObservers.contains(obs)){
			listObservers.add(obs);
		}
	}
	public void removeObserver(MovementObserver obs){
		if(listObservers.contains(obs)){
			listObservers.remove(obs);
		}
	}
	public interface MovementObserver{
		public void doExecute(int valeur);
	}
}
