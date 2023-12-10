package englishlearningapp.englearning.Controller;

import englishlearningapp.englearning.App;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GuideController implements Initializable {
    @FXML
    private WebView webview;
    private WebEngine webEngine;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webview.getEngine();
        webEngine.load(App.class.getResource("HTMLViews/GuideText.html").toString());
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.FAILED) {
                System.out.println("Loading failed with error: " + webEngine.getLoadWorker().getMessage());
            }
        });
    }

    public void clickSearch(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.searchRoot);
    }

    public void onIconClicked(MouseEvent mouseEvent) throws IOException {
        SceneController.switchScene(mouseEvent, SceneController.defaultRoot);
    }

    public void clickGame(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.gameRoot);
    }

    public void clickTranslate(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.translateRoot);
    }

    public void onClickAbout(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.aboutRoot);
    }

}
