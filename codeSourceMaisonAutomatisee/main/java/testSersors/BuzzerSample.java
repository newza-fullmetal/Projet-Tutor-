package testSersors;
import upm_buzzer.Buzzer;
public class BuzzerSample {
	public static void main(String[] args) throws InterruptedException {
		// ! [Interesting]
		int chord[] = {
				upm_buzzer.javaupm_buzzer.DO,
				upm_buzzer.javaupm_buzzer.RE,
				upm_buzzer.javaupm_buzzer.MI,
				upm_buzzer.javaupm_buzzer.FA,
				upm_buzzer.javaupm_buzzer.SOL,
				upm_buzzer.javaupm_buzzer.LA,
				upm_buzzer.javaupm_buzzer.SI};

		// Instantiate a buzzer on digital pin D5
		Buzzer sound = new Buzzer(6);

		// print sensor name
		System.out.println(sound.name());

		for (int i = 0; i < chord.length; i++) {
			// play each note for one half second
			int note = sound.playSound(chord[i], 500000);
			System.out.println(note);

			Thread.sleep(100);
		}
		// ! [Interesting]
		sound.stopSound();
	}
}
