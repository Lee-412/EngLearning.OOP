package englishlearningapp.englearning.Game;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public abstract class Game   {

    public Game() {
    }

    public abstract void resetGame(TextField playerAnswerTextField, Button answerTextArea, TextArea timerNumber, TextField score, Circle c1);

    public abstract void playTimer(KeyEvent eventkey, TextArea timerNumber, Circle c1, TextField score, Button botAnswer, TextField playanswer) throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    public abstract void playTimer(ActionEvent event, Button answerA, Button answerB, TextArea questionVocab, TextArea scoregame, TextArea timerbox, Button handleGame) throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    public abstract void resetGame(ActionEvent event, Button answerA, Button answerB, TextArea questionVocab, TextArea scoregame, TextArea timerbox, Button handleGame);
}
