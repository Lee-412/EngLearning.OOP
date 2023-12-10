
package englishlearningapp.englearning.Controller;

import java.io.IOException;
import java.util.Random;

import animatefx.animation.BounceIn;
import englishlearningapp.englearning.App;
import englishlearningapp.englearning.CustomAnimation.FlipPageAnimation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.*;

import javax.sound.sampled.*;


public class GameViewController {
    public Button searchBtn;
    public Button gameBtn;
    public Button translateBtn;
    public ImageView vocabPic;
    public ImageView linkingPic;
    public Button vocabBtn;
    public Button connectBtn;
    @FXML
    private TextArea Scoregame = new TextArea();
    private int score = 0;
    private int quesnumber = 0;

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

    public String toString(int score) {
        return "[" + score + "]";
    }

    public void setTextScore(String s) {
        this.Scoregame.setText(s);
    }


    public void endGame(ActionEvent event, String s) throws IOException {
        if (s.equals("vocab") || s.equals("grammar") || s.equals("connect")) {
            SceneController.switchScene(event, SceneController.gameRoot);
            this.setScore(0);
            this.setQuesNumber(0);
            this.setTextScore(this.toString(this.getScore()));
        }
    }

    public void clickSearch(ActionEvent event) throws IOException, InterruptedException {
        new BounceIn(searchBtn).play();
        SceneController.switchScene(event, SceneController.searchRoot);

    }

    public void clickTranslate(ActionEvent event) throws IOException, InterruptedException {
        new BounceIn(translateBtn).play();
        SceneController.switchScene(event, SceneController.translateRoot);
    }
    public void clickGame(ActionEvent event) throws IOException, InterruptedException {
        new BounceIn(translateBtn).play();
        SceneController.switchScene(event, SceneController.gameRoot);
    }


    public void clickVocab(ActionEvent event) throws IOException, InterruptedException {
        FlipPageAnimation flp = new FlipPageAnimation(vocabPic);
        flp.play();
        vocabBtn.setDisable(true);
        flp.setOnFinished(() -> {
            try {
                SceneController.switchScene(event, SceneController.vocabRoot);
                vocabBtn.setDisable(false);
                Platform.runLater(() -> {
                    try {
                        AlertController.showCustomPopUp("IntrucstionVocab.fxml", "pane-instruction");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void clickConnect(ActionEvent event) throws IOException, InterruptedException {
        FlipPageAnimation flp = new FlipPageAnimation(linkingPic);
        flp.play();
        connectBtn.setDisable(true);
        flp.setOnFinished(() -> {
            try {
                connectBtn.setDisable(false);
                SceneController.switchScene(event, SceneController.connectRoot);
                Platform.runLater(() -> {
                    try {
                        AudioInputStream audioInputStream
                                = AudioSystem.getAudioInputStream(App.class.getResource("src/sounds/bombplanted.wav"));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                        AlertController.showCustomPopUp("InstructionConnectView.fxml", "instruction-connect");
                    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    public void onIconClicked(MouseEvent mouseEvent) throws IOException {
        SceneController.switchScene(mouseEvent, SceneController.defaultRoot);
    }

    public void onClickAbout(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.aboutRoot);
    }

    public void onClickGuide(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.guideRoot);
    }
}
