package englishlearningapp.englearning.Game;

import englishlearningapp.englearning.App;


import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer = new Timer();
    private int counter;
    private Clip clip;
    public GameTimer () {

    }

    public GameTimer (int countdown) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        counter = countdown;
        AudioInputStream ad = AudioSystem.getAudioInputStream(App.class.getResource("src/sounds/mixkit-alarm-clock-beep-988.wav"));
        clip = AudioSystem.getClip();
        clip.open(ad);
    }

    public int getCounter() {
        return counter;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void excuteTask(TimerTask timerTask) throws IOException {
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
    public void playAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream ad = AudioSystem.getAudioInputStream(App.class.getResource("src/sounds/mixkit-alarm-clock-beep-988.wav"));
        clip = AudioSystem.getClip();
        clip.open(ad);
        clip.start();
    }
    public void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
/*    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        System.out.println("Starting up...");
        GameTimer gmt = new GameTimer(10);
        //one-time use timer: prints stuff after 10s
        final int[] counter = {10};
        Timer myTimer = new Timer();
        gmt.playAudio();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if(counter[0] > 0) {
                    System.out.println(counter[0]);
                    counter[0] --;
                }else {
                    myTimer.cancel();
                }
            }
        };

        Timer myRepeatingTimer = new Timer();
        myRepeatingTimer.scheduleAtFixedRate(task, 0, 1000);
    }*/

}
