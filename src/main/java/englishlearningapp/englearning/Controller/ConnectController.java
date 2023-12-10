package englishlearningapp.englearning.Controller;


import englishlearningapp.englearning.Game.ConnectGame;
import englishlearningapp.englearning.Game.GameTimer;
import englishlearningapp.englearning.questionGame.BotAnswerGenerator;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.shape.Circle;

import javax.sound.sampled.*;
import java.io.IOException;
import java.sql.SQLException;

public class ConnectController {

    private final ConnectGame connectGame = new ConnectGame();
    @FXML
    private TextField playerAnswerTextField;

    @FXML
    private Button answerTextArea;
    @FXML
    private ImageView imageScore;
    @FXML
    protected Circle c1;
    @FXML
    TextArea timerNumber;

    @FXML
    private TextField score;
    @FXML
    private ImageView imageWay;

    public void startGame() {
        score.setText(String.valueOf(connectGame.getScore()));
        String spawnWord = BotAnswerGenerator.generateRandomBotAnswers();
        answerTextArea.setText(spawnWord);
        playerAnswerTextField.setOnKeyPressed(event -> {
            try {

                handlePlayer(event);

            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {

                throw new RuntimeException(e);
            }
        });
    }

    public void handlePlayer(KeyEvent event) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (event.getCode() == KeyCode.ENTER) {
            String playerAnswer = playerAnswerTextField.getText();
            String botText = answerTextArea.getText();
            if (connectGame.checkEnterWord(playerAnswer)
                    && BotAnswerGenerator.checkPlayerWord(playerAnswer)
                    && (playerAnswer.charAt(0) == botText.charAt(botText.length() - 1))) {
                connectGame.EnteredWord.add(playerAnswer);
                String botAnswer = connectGame.checkBotAnswer(playerAnswer);
                if (botAnswer != null && !botAnswer.isEmpty()) {
                    answerTextArea.setText(botAnswer);
                    connectGame.playAudio("src/sounds/defuse.wav");
                    connectGame.setScore(connectGame.getScore() + 1);
                    score.setText(String.valueOf(connectGame.getScore()));
                    connectGame.EnteredWord.add(botAnswer);
                    connectGame.playTimer(event, timerNumber, c1, score, answerTextArea, playerAnswerTextField);

                } else if (botAnswer == null) {
                    String point = score.getText();
                    AlertController.alertEndGame(event, "You Win", point);
                    connectGame.resetGame(playerAnswerTextField, answerTextArea, timerNumber, score, c1);
                }
                playerAnswerTextField.clear();
            } else {
                ActionEvent eventAlert = new ActionEvent();
                AlertController.alertWrong(eventAlert, "The word was entered previously or wrong");
                playerAnswerTextField.clear();
            }
        }
    }

    public void clickExitConnect(ActionEvent event) throws IOException, UnsupportedAudioFileException, SQLException, LineUnavailableException {

        connectGame.resetGame(playerAnswerTextField, answerTextArea, timerNumber, score, c1);
        if (ConnectGame.rt != null) {
            connectGame.resetGame(playerAnswerTextField, answerTextArea, timerNumber, score, c1);
            if (ConnectGame.rt != null) {
                ConnectGame.rt.stop();
            }
            SceneController.switchScene(event, SceneController.gameRoot);

        }
    }

    public void initialize() throws SQLException {
        Thread th = new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                startGame();
                return null;
            }
        });
        th.setDaemon(true);
        th.start();
    }
}