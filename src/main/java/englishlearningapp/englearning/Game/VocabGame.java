package englishlearningapp.englearning.Game;

import englishlearningapp.englearning.App;
import englishlearningapp.englearning.Controller.AlertController;
import englishlearningapp.englearning.questionGame.Question_answer_vocab;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;

import javax.sound.sampled.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;
import java.util.TimerTask;

public class VocabGame extends Game {
    private Clip clip;
    private GameTimer gameTimer = new GameTimer(10);
    private int score = 0;
    private int quesnumber = 0;
    private Random random = new Random();
    private int randomIndex;
    private TimerTask currentTask;

    public GameTimer getGameTimer() {
        return gameTimer;
    }


    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public VocabGame() throws UnsupportedAudioFileException, LineUnavailableException, IOException, SQLException {
    }

    @Override
    public void resetGame(TextField playerAnswerTextField, Button answerTextArea, TextArea timerNumber, TextField score, Circle c1) {

    }

    @Override
    public void playTimer(KeyEvent eventkey, TextArea timerNumber, Circle c1, TextField score, Button botAnswer, TextField playanswer) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

    }

    public int getRandom() {
        return this.randomIndex;
    }

    public void setRandom(int size) {
        this.randomIndex = this.random.nextInt(size - 1) + 1;
    }


    public int getQuesNumber() {
        return this.quesnumber;
    }

    public void setQuesNumber(int quesnumber) {
        this.quesnumber = quesnumber;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    Question_answer_vocab questionAnswer = new Question_answer_vocab();

    public boolean checkCorrect(TextArea questionVocab, Button answerA) {
        String question = questionVocab.getText();
        String correctAnswer = questionAnswer.getAnswer(question);
        if (answerA.getText().equals(correctAnswer)) {
            return true;
        } else return false;
    }

    public void loadRandomQuestion(TextArea questionVocab, Button answerA, Button answerB, TextArea ScoreGame) throws SQLException {

        ScoreGame.setText(String.valueOf(0));
        this.setRandom(950);
        int index = this.getRandom();
        questionVocab.setText(questionAnswer.getQuestion(index));
        String correctAnswer = questionAnswer.getAnswer(index);
        String answerRandom = questionAnswer.getrandomAnswer(index);
        answerB.setDisable(true);
        answerA.setDisable(true);
        this.setRandom(950);
        int countAnswer = this.getRandom();

        if (countAnswer % 2 == 1) {
            answerA.setText(correctAnswer);
            answerB.setText(answerRandom);
        } else {
            answerA.setText(answerRandom);
            answerB.setText(correctAnswer);
        }
    }

    @Override
    public void playTimer(ActionEvent event, Button answerA, Button answerB, TextArea questionVocab, TextArea scoregame, TextArea timerbox, Button handleGame) throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        final int[] counter = {gameTimer.getCounter()};
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if ( counter[0] >= 0) {
                    timerbox.setText(String.valueOf(counter[0]));
                    counter[0]--;
                } else {
                    Platform.runLater(() -> {
                        try {
                            String point = scoregame.getText();
                            resetGame(event,answerA,answerB,questionVocab,scoregame,timerbox,handleGame);
                            AlertController.alertEndGame(event, "YOUR SCORE IS: ", point);
                        } catch (IOException e) {
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
        gameTimer.excuteTask(currentTask);
    }


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
    public void stopTimer() {
        if (currentTask != null) {
            currentTask.cancel();
            currentTask = null;
        }
    }

    @Override
    public void resetGame(ActionEvent event, Button answerA, Button answerB, TextArea questionVocab, TextArea scoregame, TextArea timerbox, Button handleGame) {
        handleGame.setDisable(false);
        answerA.setText("");
        answerA.setDisable(true);
        answerB.setText("");
        answerB.setDisable(true);
        questionVocab.clear();
        scoregame.setText("");
        setScore(0);
        timerbox.clear();
        gameTimer.stopAudio();
        stopTimer();
        System.out.println("done reset");

    }
}
