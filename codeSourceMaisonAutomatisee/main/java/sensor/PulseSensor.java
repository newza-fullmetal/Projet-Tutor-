package sensor;

import java.util.ArrayList;

import constants.Constants;


public class PulseSensor {
	private ArrayList<PulseObserver> listObservers = new ArrayList<PulseObserver>();
	private int  heartRate;
	private boolean init = false;
	public PulseSensor() {}
	
	
	public void init(){
		new Thread(){
			public void run(){
				pulse(Constants.PulsePort);
			}
		}.start();
	}
	
	private void pulse (int port) {
		// Instantiate a Grove Ear-clip Heart Rate sensor on digital pin D(port)
		 upm_groveehr.GroveEHR heart = new upm_groveehr.GroveEHR(port);
		// set the beat counter to 0, init the clock and start counting beats
	        heart.clearBeatCounter();
	        heart.initClock();
	        heart.startBeatCounter();

	        while (true) {
	    		
	            long millis = heart.getMillis();
	            long beats = heart.beatCounter();	
	            // heartRate() requires that at least 5 seconds pass before	
	            // returning anything other than 0	
	            int hr = heart.heartRate();	
		        if(!init){
			        try {
						Thread.sleep(5000);
						init = true;
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        }
	            heartRate  = hr;
	            if(millis != 1){
	            // output milliseconds passed, beat count, and computed heart rate	
	            System.out.println("Millis: " + millis + ", Beats: " + beats + ", Heart rate: " + hr);
	            if(hr <= 60 || hr >= 140){
	            notifyObserver();
	            try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            }
	            }
	            try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	        }
	}
	

	public int getHeartRate() {
		return heartRate;
	}


	private void notifyObserver() {
		for(PulseObserver obs : listObservers){
			obs.doExecute();
		}
		
	}
	
	public void addObserver(PulseObserver obs){
		if(!listObservers.contains(obs)){
			listObservers.add(obs);
		}
	}
	public void removeObserver(PulseObserver obs){
		if(listObservers.contains(obs)){
			listObservers.remove(obs);
		}
	}
	public interface PulseObserver{
		public void doExecute();
	}
}
