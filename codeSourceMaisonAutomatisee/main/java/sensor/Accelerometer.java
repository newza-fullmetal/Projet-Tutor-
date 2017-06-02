package sensor;

import java.util.ArrayList;

import constants.Constants;
import upm_mma7660.MMA7660;


public class Accelerometer {
	private ArrayList<AccelerometerObserver> listObservers = new ArrayList<AccelerometerObserver>();
	private static final float seuil = 3f;
	
	
	
	public Accelerometer() {}

	public void init(){
		new Thread(){
			public void run (){
				while(!this.isInterrupted()){
					
					try {
						accelerateur();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}.start();
		
	}
	
	public void accelerateur() throws InterruptedException {
		// ! [Interesting]
        // Instantiate an MMA7660 on I2C bus 0
        MMA7660 accel = new MMA7660(Constants.AccelerometerPort);

        // place device in standby mode so we can write registers
        accel.setModeStandby();

        // enable 64 samples per second
        accel.setSampleRate(upm_mma7660.MMA7660_AUTOSLEEP_T.MMA7660_AUTOSLEEP_64);

        // place device into active mode
        accel.setModeActive();
        
        float sample[] = accel.getAcceleration();
        Thread.sleep(1000);
        	float moyen;
        	while(true){
            float acceleration[] = accel.getAcceleration();
            /*
            System.out.println("Acceleration: x = "
                               + acceleration[0]
                               + " y = "
                               + acceleration[1]
                               + " x = "
                               + acceleration[2]);

             */
            moyen = sqrt(acceleration[0]*10,acceleration[1]*10,acceleration[2]*10);
            System.out.println("moyen = " + moyen);
            if (moyen <= seuil){
            	
            	notifyObserver();
            	Thread.sleep(10000);
            }
        	}
	}
	
	public static float sqrt(float x, float y, float z){
		return (float) Math.sqrt(x*x+y*y+z*z);
	}


	private void notifyObserver() {
		for(AccelerometerObserver obs : listObservers){
			obs.doExecute();
		}
		
	}
	public void addObserver(AccelerometerObserver obs){
		if(!listObservers.contains(obs)){
			listObservers.add(obs);
		}
	}
	public void removeObserver(AccelerometerObserver obs){
		if(listObservers.contains(obs)){
			listObservers.remove(obs);
		}
	}
	public interface AccelerometerObserver{
		public void doExecute();
	}
}
