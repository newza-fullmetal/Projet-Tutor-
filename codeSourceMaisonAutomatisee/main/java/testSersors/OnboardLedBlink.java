/*
 * Author: Jessica Gomez <jessica.gomez.hernandez@intel.com>
 * Author: Petre Eftime <petre.p.eftime@intel.com>
 *
 * Copyright (c) 2015 - 2016 Intel Corporation.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * @file
 * @ingroup basic
 * @brief On board LED blink Java
 *
 * Demonstrate how to blink the on board LED, writing a digital value to an
 * output pin using the MRAA library.
 *
 * @hardware No external hardware is needed.
 *
 * @req mraa.jar
 *
 * @date 19/08/2015
 */
package testSersors;

import mraa.Dir;
import mraa.Gpio;
import mraa.Platform;
import mraa.Result;
import mraa.Uart;
import mraa.UartParity;
import mraa.mraa;
import upm_buzzer.Buzzer;
import upm_grove.Grove;
import upm_grove.GroveButton;
import upm_grove.GroveLed;
import upm_grove.GroveLight;
import upm_grove.GroveRelay;
import upm_grove.GroveRotary;
import upm_grove.GroveSlide;
import upm_grove.GroveTemp;
import upm_groveehr.GroveEHR;
import upm_rpr220.RPR220;
import upm_mma7660.MMA7660;
import upm_mma7660.MMA7660_AUTOSLEEP_T;
import upm_mma7660.MMA7660_INTR_T;
import upm_mma7660.MMA7660_MODE_T;
import upm_mma7660.MMA7660_REG_T;
import upm_mma7660.MMA7660_TILT_BF_T;
import upm_mma7660.MMA7660_TILT_LP_T;
import mraa.Gpio;
import mraa.Platform;
import mraa.Result;
import mraa.mraa;

public class OnboardLedBlink {
	
	public static void pulse (int port) {
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
	            // output milliseconds passed, beat count, and computed heart rate	
	            System.out.println("Millis: " + millis + ", Beats: " + beats + ", Heart rate: " + hr);
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	        }
	}
	
	public static float[] accelerateur() throws InterruptedException {
		// ! [Interesting]
        // Instantiate an MMA7660 on I2C bus 0
        MMA7660 accel = new MMA7660(0);

        // place device in standby mode so we can write registers
        accel.setModeStandby();

        // enable 64 samples per second
        accel.setSampleRate(upm_mma7660.MMA7660_AUTOSLEEP_T.MMA7660_AUTOSLEEP_64);

        // place device into active mode
        accel.setModeActive();

        
            float acceleration[] = accel.getAcceleration();
            System.out.println("Acceleration: x = "
                               + acceleration[0]
                               + " y = "
                               + acceleration[1]
                               + " x = "
                               + acceleration[2]);

            System.out.println();

            
        
        return acceleration;
	}
	
	public static void SampleLedBlink() {
		 // select onboard LED pin based on the platform type
        // create a GPIO object from MRAA using it

        Platform platform = mraa.getPlatformType();
        Gpio pin = null;
        if (platform == Platform.INTEL_GALILEO_GEN1)
            pin = new Gpio(3);
        else if (platform == Platform.INTEL_GALILEO_GEN2)
            pin = new Gpio(13);
        else if (platform == Platform.INTEL_EDISON_FAB_C)
            pin = new Gpio(13);
        /*
        else if (platform == Platform.INTEL_GT_TUCHUCK)
            pin = new Gpio(100);
	*/
        // set the pin as output
        if (pin == null) {
            System.err.println("Could not initialize GPIO, exiting");
            return;
        }

        if (pin.dir(Dir.DIR_OUT) != Result.SUCCESS) {
            System.err.println("Could not set pin as output, exiting");
            return;
        }

        // loop forever toggling the on board LED every second
        while (true) {
            pin.write(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted: " + e.toString());
            }
            pin.write(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Sleep interrupted: " + e.toString());
            }
        }
	}
	
	
	public static void light(int port) throws InterruptedException {
		GroveLight gl = new GroveLight(port);
		while (true) {
			float raw_value = gl.raw_value();
			float value = gl.value();
			/*
			if (raw_value < 100) 
				buzzer(2);
				*/
			System.out.println("raw value: " + raw_value);
			System.out.println("value: " + value);


			Thread.sleep(1000);
		}
	}
	
	public static void temperature(int port) throws InterruptedException {
		upm_grove.GroveTemp temp = new upm_grove.GroveTemp(port);
		
		for (int i = 0; i < 10; ++i) {
			
			int celsius = temp.value();
			int fahrneheit = celsius * 2 + 32;

			System.out.println("Celsius: " + celsius);
			System.out.println("Fahrneheit: " + fahrneheit);
			
			Thread.sleep(1000);
		}
		temp.delete();
	}
	
	public static void led(int port) throws InterruptedException {
		 upm_grove.GroveLed led = new upm_grove.GroveLed(port);
			
			for (int i = 0; i < 10; ++i) {
				led.on();
				Thread.sleep(1000);
				led.off();
				Thread.sleep(1000);
			}
			led.delete();
			
			/*Gpio led1 = new Gpio(port);
			led1.dir(Dir.DIR_OUT);
			led1.write(1);*/
	}
	
	public static void buzzer(int port) throws InterruptedException {
		Gpio pin = new Gpio(port);
		pin.dir(Dir.DIR_OUT);
		pin.write(1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pin.write(0);
		
	}
	
	public static void irDetecte(int port) throws InterruptedException {
		RPR220 sensor = new RPR220(port);
		while(true) {
	
			
			if (sensor.blackDetected())
                System.out.println("Black detected"); // 
            else
                System.out.println("Black NOT detected"); // cos nguoi di qua
			Thread.sleep(1000);
		}
	}


	public static void montionSensor(int port) throws InterruptedException {
        Gpio gpio = new Gpio(port);
        gpio.dir(Dir.DIR_IN);

        while (true) {
        	if (gpio.read()==1) {
        		System.out.println("Personne detected ! \n");
        	}
            //System.out.format("Gpio is %d\n", gpio.read());
            Thread.sleep(1000);
        }
		
	}
	
    public static void main(String[] args) {
       
    	//RPR220 sensor = new RPR220(3);
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

    	while (true) {
    	//try {
			//temperature(3);
    		try {
				led(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//buzzer(6);
    		//light(0);
    		//if (sensor.blackDetected())
    		//	buzzer(4);
			//irDetecte(8);
    		//accelerateur();

    		
            
    	      
           
            
    		/*
            if (uart.dataAvailable()) {
            	byte [] arra = uart.readStr(1000).getBytes();
            	for (byte c : arra) {
            		System.out.format("%d", c);
            		
            	}
            	System.out.println("\n");
            }
            */
    	}
    }
}
            