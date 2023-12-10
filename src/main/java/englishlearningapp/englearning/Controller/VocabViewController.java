//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package englishlearningapp.englearning.Controller;

import englishlearningapp.englearning.App;
import englishlearningapp.englearning.Game.VocabGame;
import englishlearningapp.englearning.questionGame.Question_answer_vocab;

import java.io.IOException;
import java.sql.PseudoColumnUsage;
import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class VocabViewController {

    @FXML
    private MediaView leftMedia;
    private Media media;
    private MediaPlayer mediaPlayer;
    @FXML
    private TextArea timerbox;
    @FXML
    private TextArea questionVocab;
    @FXML
    private Button answerA;
    @FXML
    private Button answerB;
    @FXML
    private Button handleGame;

    @FXML
    private TextArea Scoregame = new TextArea();
    private VocabGame vocabGame = new VocabGame();
    Question_answer_vocab questionAnswer = new Question_answer_vocab();

    public VocabViewController() throws UnsupportedAudioFileException, LineUnavailableException, IOException, SQLException {
    }

    @FXML
    public void handleAnswerA(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, SQLException {
        System.out.println("answerA");
        if (vocabGame.checkCorrect(questionVocab, answerA)) {
            init(App.class.getResource("src/media/congratulate.mp4").toString());
            playMedia();
            vocabGame.playAudio("src/sounds/correctSound.wav");
            int scoretmp = vocabGame.getScore();
            vocabGame.setScore(scoretmp + 1);
            Scoregame.setText(String.valueOf(vocabGame.getScore()));
        } else {
            init(App.class.getResource("src/media/wrong.mp4").toString());
            playMedia();
            vocabGame.playAudio("src/sounds/incorrectSound.wav");
            int scoretmp = vocabGame.getScore();
            vocabGame.setScore(scoretmp - 1);
            Scoregame.setText(String.valueOf(vocabGame.getScore()));

        }
        if (Integer.parseInt(Scoregame.getText()) < 0) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Notification");
                alert.setContentText("Your Score is 0. Game Over!");
                ButtonType buttonTypeCancel = new ButtonType("OKE", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                try {
                    SceneController.switchScene(event, SceneController.gameRoot);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            vocabGame.stopTimer();
            stopMedia();
            vocabGame.resetGame(event, answerA, answerB, questionVocab, Scoregame, timerbox, handleGame);
            return;
        }
        handleInformation(event);

    }

    @FXML
    public void handleAnswerB(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, SQLException {
        System.out.println("answerB");
        if (vocabGame.checkCorrect(questionVocab, answerB)) {
            init(App.class.getResource("src/media/congratulate.mp4").toString());
            playMedia();
            vocabGame.playAudio("src/sounds/correctSound.wav");
            int scoretmp = vocabGame.getScore();
            vocabGame.setScore(scoretmp + 1);
            Scoregame.setText(String.valueOf(vocabGame.getScore()));
        } else {
            init(App.class.getResource("src/media/wrong.mp4").toString());
            playMedia();
            vocabGame.playAudio("src/sounds/incorrectSound.wav");
            int scoretmp = vocabGame.getScore();
            vocabGame.setScore(scoretmp - 1);
            Scoregame.setText(String.valueOf(vocabGame.getScore()));

        }
        if (Integer.parseInt(Scoregame.getText()) < 0) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Notification");
                alert.setContentText("Your Score is 0. Game Over!");
                ButtonType buttonTypeCancel = new ButtonType("OKE", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonTypeCancel);
                Optional<ButtonType> result = alert.showAndWait();
                try {
                    SceneController.switchScene(event, SceneController.gameRoot);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            vocabGame.stopTimer();
            vocabGame.stopAudio();
            stopMedia();
            vocabGame.resetGame(event, answerA, answerB, questionVocab, Scoregame, timerbox, handleGame);
            return;
        }

        handleInformation(event);

    }
    /*    public void initialize() throws SQLException {
        Thread th = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                vocabGame.loadRandomQuestion(questionVocab, answerA, answerB, Scoregame);
                return null;
            }
        });
        th.setDaemon(true);
        th.start();
    }*/

    public void handleInformation(ActionEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException, SQLException {
        vocabGame.playTimer(event, answerA, answerB, questionVocab, Scoregame, timerbox, handleGame);
        Scoregame.setText(String.valueOf(vocabGame.getScore()));
        int questionnumber = vocabGame.getQuesNumber();
        vocabGame.setRandom(950);
        int index = vocabGame.getRandom();
        String question = questionAnswer.getQuestion(index);
        questionVocab.setText(question);
        String correctAnswer = questionAnswer.getAnswer(index);
        vocabGame.setRandom(950);
        int countAnswer = vocabGame.getRandom();
        String answerRandom = questionAnswer.getrandomAnswer(index);

        if (countAnswer % 2 == 1) {
            answerA.setText(correctAnswer);
            answerB.setText(answerRandom);
        } else {
            answerA.setText(answerRandom);
            answerB.setText(correctAnswer);
        }
        questionnumber++;
        vocabGame.setQuesNumber(questionnumber);

    }


    public void onExit(ActionEvent event) throws IOException, UnsupportedAudioFileException, SQLException, LineUnavailableException {
        vocabGame.resetGame(event, answerA, answerB, questionVocab, Scoregame, timerbox, handleGame);
        stopMedia();
        SceneController.switchScene(event, SceneController.gameRoot);
    }

    public void clickStart(ActionEvent event) throws SQLException {
        vocabGame.loadRandomQuestion(questionVocab, answerA, answerB, Scoregame);
        handleGame.setDisable(true);
        answerA.setDisable(false);
        answerB.setDisable(false);

        System.out.println("click");
    }


    public void init(String file) {
        media = new Media(file);
        mediaPlayer = new MediaPlayer(media);
        leftMedia.setMediaPlayer(mediaPlayer);
    }

    public void playMedia() {
        mediaPlayer.play();
    }

    public void stopMedia() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }


}
