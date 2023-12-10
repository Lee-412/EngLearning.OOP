package englishlearningapp.englearning.TextToSpeech;

import javafx.concurrent.Task;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TexttoSpeechTask extends Task<Void> {
    private String wordSelected;
    public TexttoSpeechTask(String wordSelected) {
        this.wordSelected = wordSelected;
    }
    @Override
    protected Void call() throws Exception {
        try {
            TTS.initApiVoice(wordSelected);
            TTS.playSpeaker();
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
