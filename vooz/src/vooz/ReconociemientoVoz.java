package vooz;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class ReconociemientoVoz {

	public static void main(String[] args) throws Exception {
        
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us"); 
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                configuration);
        InputStream stream = new FileInputStream(new File("test.wav"));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n",  result.getHypothesis());
        }
        recognizer.stopRecognition();
    
	
	
	LiveSpeechRecognizer recognizer2 = new  LiveSpeechRecognizer(configuration);
	// Start recognition process pruning previously cached data.
	recognizer2.startRecognition(true);
	SpeechResult result2 = recognizer.getResult();
	// Pause recognition process. It can be resumed then with  startRecognition(false).
	recognizer2.stopRecognition();
	
	}
}
