package englishlearningapp.englearning.Controller;

import englishlearningapp.englearning.App;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class InstructionConnectController implements Initializable {
    @FXML
    private WebView webview;
    private WebEngine webEngine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webview.getEngine();

        webEngine.load(App.class.getResource("HTMLViews/InstructionConnect.html").toString());

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.FAILED) {
                System.out.println("Loading failed with error: " + webEngine.getLoadWorker().getMessage());
            }
        });
    }
}
