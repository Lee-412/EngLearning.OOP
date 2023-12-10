package englishlearningapp.englearning.Controller;

import animatefx.animation.BounceIn;
import animatefx.animation.ZoomOut;
import englishlearningapp.englearning.API_Connection.TranslateAPIConnection;
import englishlearningapp.englearning.API_Connection.TranslateTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class TranslateViewController{
    @FXML
    private Button switchBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private Button gameBtn;
    @FXML
    private Button translateBtn;
    @FXML
    private TextArea inputText;
    @FXML
    private TextArea outputText;
    @FXML
    private TextArea inputLang;
    @FXML
    private TextArea outputLang;
    public TranslateViewController() throws IOException {
    }
    public void clickGame (ActionEvent event) throws IOException {
        new BounceIn(gameBtn).play();
        SceneController.switchScene(event, SceneController.gameRoot);
    }
    public void clickSearch (ActionEvent event) throws IOException {
        new BounceIn(searchBtn).play();
        SceneController.switchScene(event, SceneController.searchRoot);
    }

    String res = "";
    public void onTranslate() throws Exception {
        inputText.setWrapText(true);
        outputText.setWrapText(true);
        if(!TranslateAPIConnection.isIsConnected()) {
            Platform.runLater(() -> {
                AlertController.showNotConnectInternet();
            });
        } else {
            String translation = inputText.getText();
            TranslateTask task = new TranslateTask(inputLang, outputLang, translateBtn, translation);
            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            task.setOnSucceeded(event -> {
                res = task.getValue();
                outputText.setText(res);
            });
        }
    }

    public void switchLanguage() {
        ZoomOut zoomOut = new ZoomOut(switchBtn);
        zoomOut.setSpeed(2);
        zoomOut.playOnFinished(new BounceIn(switchBtn)).play();
        if(inputLang.getText().equals("vi")) {
            inputLang.setText("en");
            outputLang.setText("vi");
        }else {
            inputLang.setText("vi");
            outputLang.setText("en");
        }
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
