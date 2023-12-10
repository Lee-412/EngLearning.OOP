package englishlearningapp.englearning.Controller;

import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import englishlearningapp.englearning.App;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class SceneController {
    protected static Stage stage;
    protected static Scene scene ;

    public static AnchorPane defaultRoot;

    static {
        try {
            defaultRoot = FXMLLoader.load(App.class.getResource("Views/DefaultView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane searchRoot;

    static {
        try {
            searchRoot = FXMLLoader.load(App.class.getResource("Views/LookingUpView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane gameRoot;

    static {
        try {
            gameRoot = FXMLLoader.load(App.class.getResource("Views/GameView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane translateRoot;

    static {
        try {
            translateRoot = FXMLLoader.load(App.class.getResource("Views/TranslateView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static AnchorPane vocabRoot;

    static {
        try {
            vocabRoot = FXMLLoader.load(App.class.getResource("Views/VocabView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static AnchorPane connectRoot;

    static {
        try {
            connectRoot = FXMLLoader.load(App.class.getResource("Views/ConnectView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane exitRoot;

    static {
        try {
            exitRoot = FXMLLoader.load(App.class.getResource("Views/GameView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane addViewRoot;
    static {
        try {
            addViewRoot = FXMLLoader.load(App.class.getResource("Views/AddwordView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnchorPane modifyViewRoot;
    static {
        try {
            modifyViewRoot = FXMLLoader.load(App.class.getResource("Views/ModifyWordView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static AnchorPane aboutRoot;
    static {
        try {
            aboutRoot = FXMLLoader.load(App.class.getResource("Views/AboutUsView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static AnchorPane guideRoot;
    static {
        try {
            guideRoot = FXMLLoader.load(App.class.getResource("Views/GuideView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public SceneController() throws IOException {
    }
    public static void switchSceneNormal (ActionEvent event, Parent sceneSwitch) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        scene.setRoot(sceneSwitch);
        scene.getStylesheets().add(App.class.getResource("src/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void switchScene (Event event, Parent sceneSwitch) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        FadeIn fadeEffect = new FadeIn(sceneSwitch);
        fadeEffect.setSpeed(2);
        fadeEffect.play();
        scene.setRoot(sceneSwitch);
        scene.getStylesheets().add(App.class.getResource("src/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static void switchScene (ActionEvent event, Parent sceneSwitch) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        FadeIn fadeEffect = new FadeIn(sceneSwitch);
        fadeEffect.setSpeed(2);
        fadeEffect.play();
        scene.setRoot(sceneSwitch);
        scene.getStylesheets().add(App.class.getResource("src/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static void switchScene (MouseEvent event, Parent sceneSwitch) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        FadeIn fadeEffect = new FadeIn(sceneSwitch);
        fadeEffect.setSpeed(1);
        fadeEffect.play();
        scene.setRoot(sceneSwitch);
        scene.getStylesheets().add(App.class.getResource("src/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static void switchScene (KeyEvent event, Parent sceneSwitch) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = ((Node)event.getSource()).getScene();
        FadeIn fadeEffect = new FadeIn(sceneSwitch);
        fadeEffect.setSpeed(1);
        fadeEffect.play();
        scene.setRoot(sceneSwitch);
        scene.getStylesheets().add(App.class.getResource("src/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public static AnchorPane getCurrentPane(KeyEvent event) {
        Scene tmpScene = ((Node)event.getSource()).getScene();
        return  (AnchorPane)tmpScene.getRoot();
    }
    public static void updateScene(KeyEvent event,String operation, Node item){
        if(Objects.equals(operation, "add")) {
            getCurrentPane(event).getChildren().add(item);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = ((Node)event.getSource()).getScene();
            scene.setRoot(getCurrentPane(event));
            stage.setScene(scene);
            stage.show();
        }else {
            Node deleteItem = getCurrentPane(event).lookup(item.toString());
            getCurrentPane(event).getChildren().removeAll(deleteItem);
        }
    }
}
