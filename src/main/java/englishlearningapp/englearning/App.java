package englishlearningapp.englearning;

import englishlearningapp.englearning.DictionaryPackage.Dictionary;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;

public class App extends Application {
    private static Dictionary dictionary;
    public static Dictionary getDictionary(){return dictionary;}

    @Override
    public void init() throws SQLException {
        dictionary = new Dictionary();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Views/DefaultView.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("G:\\BTL_OOP_30-10\\src\\main\\resources\\englishlearningapp\\englearning\\src\\image\\trans-icon.png");
        stage.getIcons().add(icon);
        scene.getStylesheets().add(getClass().getResource("src/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main (String[] args) {
        launch(args);
    }
}
