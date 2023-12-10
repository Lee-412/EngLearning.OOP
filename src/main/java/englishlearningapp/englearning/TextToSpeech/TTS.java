package englishlearningapp.englearning.TextToSpeech;

import com.voicerss.tts.*;
import com.voicerss.tts.AudioFormat;
import englishlearningapp.englearning.Controller.AlertController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import javax.sound.sampled.*;

public class TTS {
    final private static VoiceProvider tts = new VoiceProvider("6f6bfb4a94174cd5b18f29d6febe6d6b");
    private static boolean isConnected = true;

    public static boolean isIsConnected() {
        return isConnected;
    }

    public static void initApiVoice(String text) throws IOException {
        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);

        byte[] voice = new byte[0];
        try {
            voice = tts.speech(params);
        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("khong co ket noi");
            Platform.runLater(() -> {
                AlertController.showNotConnectInternet();
            });
            isConnected = false;
        }
        FileOutputStream fos = new FileOutputStream("voice.wav");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
    }


    public static void playSpeaker() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File music = new File("voice.wav");
        AudioInputStream ad = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(ad);
        clip.start();
    }
    public static void main (String args[]) throws Exception {
       /* byte[] voice = tts.speech(new VoiceParameters("done", Languages.English_Australia));
        System.out.println(voice);*/
        initApiVoice("done");
    }
}
