package edu.cmu.sphinx.demo.dialog;

import java.util.HashMap;
import java.util.Map;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class Musica extends Thread{
	
	boolean estado = true;
	
	public void run() {
		
		while(estado){
			try {
				FileInputStream fis;
				Player player;
				fis = new FileInputStream(
						"/home/coke/Documentos/Beethoven__Piano_Sonata_Pathetique__Arthur_Rubenstein_64kb.mp3");
				BufferedInputStream bis = new BufferedInputStream(fis);

				player = new Player(bis); // Llamada a constructor de la clase Player
				player.play();          // Llamada al método play
			} catch (JavaLayerException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void detener(){
		estado = false;
	}

}
