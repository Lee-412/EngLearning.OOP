package englishlearningapp.englearning.Controller;

import englishlearningapp.englearning.App;
import englishlearningapp.englearning.DictionaryPackage.Dictionary;
import englishlearningapp.englearning.DictionaryPackage.Word;
import englishlearningapp.englearning.JDBCConnection.InsertTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Optional;

public class AddWordController {
    @FXML
    private TextArea nameArea;
    @FXML
    private TextArea pronunciationArea;
    @FXML
    private TextArea definitionArea;

    public void addWord(Dictionary dictionary, Word word) {
        InsertTask task = new InsertTask(word);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public boolean validate(Word word) {
        Dictionary dictionary = App.getDictionary();
        if(dictionary.findWord(word) != -1) {
            return true;
        }
        return false;
    }

    public void onAddWord(ActionEvent event) throws IOException {
        if(nameArea.getText().trim().equals("")) return;
        Word word = new Word();
        word.setName(nameArea.getText().trim().toLowerCase());
        word.setPronunciation(pronunciationArea.getText().trim());
        word.setDefinition(definitionArea.getText().trim());
        if(!validate(word)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure to add this word ?");
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonTypeYes) {
                addWord(App.getDictionary(), word);
            }
            nameArea.setText("");
            pronunciationArea.setText("");
            definitionArea.setText("");
        } else {
            AlertController.CustomAlert(event);
        }

    }
    public void onExit(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.searchRoot);
    }
    public static void main(String[] args) {

    }

}
