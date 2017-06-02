package sensor;

import java.util.ArrayList;

import actuator.Alarm;
import mraa.Result;
import mraa.Uart;
import mraa.UartParity;



public class RFIDSensor {
	private ArrayList<RFIDObserver> listObservers = new ArrayList<RFIDObserver>();
	private boolean RFID = false;
	private Alarm valid ;
	

	public RFIDSensor() {
		super();
		valid = new Alarm(1,50);
	}


	public void init(){
		new Thread(){
			public void run(){
				Uart uart = new Uart(0);
				if (uart.setBaudRate(9600) != Result.SUCCESS) {
	                System.err.println("Error setting baud rate");
	                System.exit(1);
	            }

	            if (uart.setMode(8, UartParity.UART_PARITY_NONE, 1) != Result.SUCCESS) {
	                System.err.println("Error setting mode");
	                System.exit(1);
	            }

	            if (uart.setFlowcontrol(false, false) != Result.SUCCESS) {
	                System.err.println("Error setting flow control");
	                System.exit(1);
	            }
				while(!this.isInterrupted()){
					
		      
		            if (uart.dataAvailable()) {
		            	byte [] arra = uart.readStr(1000).getBytes();
		            	for (byte c : arra) {
		            		System.out.format("%d", c);
		            		
		            	}
		            	System.out.println("\n");
		            	valid.start();
		            	notifyObserver();
		            	try {
							Thread.sleep(1500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
					
				}
			}
		}.start();
	}

	private void notifyObserver() {
		for(RFIDObserver obs : listObservers){
			obs.doExecute();
		}
	}
	
	public void addObserver(RFIDObserver obs){
		if(!listObservers.contains(obs)){
			listObservers.add(obs);
		}
	}
	public void removeObserver(RFIDObserver obs){
		if(listObservers.contains(obs)){
			listObservers.remove(obs);
		}
	}
	public interface RFIDObserver{
		public void doExecute();
	}
}
