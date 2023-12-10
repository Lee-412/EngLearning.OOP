package englishlearningapp.englearning.Game;

import englishlearningapp.englearning.App;
import englishlearningapp.englearning.Controller.AlertController;
import englishlearningapp.englearning.questionGame.BotAnswerGenerator;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ConnectGame extends Game {

    private int Score;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public final List<String> EnteredWord = new ArrayList<>();

    public boolean checkEnterWord(String word) {
        for (int i = 0; i < EnteredWord.size() - 1; i++) {
            if (EnteredWord.get(i).equals(word)) return false;
        }
        return true;
    }

    public final GameTimer gmt;

    {
        try {
            gmt = new GameTimer(8);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public GameTimer getTimer() {
        return gmt;
    }
    private TimerTask currentTask;

    public void setCurrentTask(TimerTask currentTask) {
        this.currentTask = currentTask;
    }

    @Override
    public void resetGame(TextField playerAnswerTextField, Button answerTextArea, TextArea timerNumber, TextField score, Circle c1) {
        playerAnswerTextField.clear();
        String spawnWord = BotAnswerGenerator.generateRandomBotAnswers();
        answerTextArea.setText(spawnWord);
        timerNumber.clear();
        score.setText(String.valueOf(0));
        EnteredWord.clear();
        gmt.stopAudio();
        stopAudio();
        stopTimer();
        setScore(0);
        System.out.println("oke");
    }

    public void stopTimer() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
    }

    private Clip clip;
    public void playAudio(String relativeUrl) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream
                = AudioSystem.getAudioInputStream(App.class.getResource(relativeUrl));
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();

    }

    public void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    @Override
    public void playTimer(KeyEvent eventkey, TextArea timerNumber, Circle c1, TextField score, Button botAnswer, TextField playanswer) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        gmt.stopAudio();
        final int[] counter = {8};
        gmt.playAudio();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (counter[0] >= 0) {
                    timerNumber.setText(String.valueOf(counter[0]));
                    counter[0]--;

                } else {
                    Platform.runLater(() -> {
                        try {
                            stopAudio();
                            String point = score.getText();
                            resetGame(playanswer, botAnswer, timerNumber, score,c1);

                            playAudio("src/sounds/explosion.wav");
                            AlertController.alertEndGame(eventkey, "The number of bombs that you defused: ", point);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (UnsupportedAudioFileException e) {
                            throw new RuntimeException(e);
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    currentTask.cancel();

                }

            }

        };

        if (currentTask != null) {
            currentTask.cancel();
        }
        currentTask = timerTask;
        gmt.excuteTask(currentTask);
        setRotate(c1, false, 360, 8);

    }


public static RotateTransition rt;
    public static void setRotate(Circle c1, boolean reverse, int angle, int duration) {
         rt = new RotateTransition(Duration.seconds(8), c1);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDelay(Duration.millis(0));
        rt.setRate(duration);
        rt.setCycleCount(duration + 1);
        rt.play();

    }

    public String checkBotAnswer(String playerAnswer) {
        char keyword = playerAnswer.charAt(playerAnswer.length() - 1);
        String botAnswer = BotAnswerGenerator.getWordStartingWith(keyword);
        while (!checkEnterWord(botAnswer)) {
            botAnswer = BotAnswerGenerator.getWordStartingWith(keyword);

            if (botAnswer == null || botAnswer.isEmpty()) {
                break;
            }
        }
        return botAnswer;

    }
    @Override
    public void playTimer(ActionEvent event, Button answerA, Button answerB, TextArea questionVocab, TextArea scoregame, TextArea timerbox, Button handleGame) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

    }

    @Override
    public void resetGame(ActionEvent event, Button answerA, Button answerB, TextArea questionVocab, TextArea scoregame, TextArea timerbox, Button handleGame) {

    }
}