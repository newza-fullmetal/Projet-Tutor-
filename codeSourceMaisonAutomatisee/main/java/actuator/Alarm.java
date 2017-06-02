package actuator;

import constants.Constants;
import mraa.Dir;
import mraa.Gpio;

public class Alarm {

	private int time ;
	private int duree;
	public Alarm(int time, int duree) {
		super();
		this.duree = duree;
		this.time = time;
	}
	public void start(){
		new Thread(){
			public void run(){
				try {
					buzzer(Constants.AlarmPort, duree);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	
	}
	private void buzzer(int port,int duree) throws InterruptedException {
		Gpio pin = new Gpio(port);
		pin.dir(Dir.DIR_OUT);
		for(int i = 0; i< time;i++ ){
		Thread.sleep(duree);
		pin.write(1);
		try {
			Thread.sleep(duree);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pin.write(0);
		}
		
	}
}
