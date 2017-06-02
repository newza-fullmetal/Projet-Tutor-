package testSersors;

public class TemperatureSample {
	public static void main (String args[]) throws InterruptedException {
		//! [Interesting]
        upm_grove.GroveTemp temp = new upm_grove.GroveTemp(3);
		
		for (int i = 0; i < 10; ++i) {
			
			int celsius = temp.value();
			int fahrneheit = celsius * 2 + 32;

			System.out.println("Celsius: " + celsius);
			System.out.println("Fahrneheit: " + fahrneheit);
			
			Thread.sleep(1000);
		}
		temp.delete();
        //! [Interesting]
	}
}
