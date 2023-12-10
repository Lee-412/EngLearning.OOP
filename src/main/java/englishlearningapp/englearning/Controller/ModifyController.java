package englishlearningapp.englearning.Controller;

import englishlearningapp.englearning.App;
import englishlearningapp.englearning.DictionaryPackage.Word;
import englishlearningapp.englearning.JDBCConnection.ModifyTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Optional;

public class ModifyController {
    @FXML
    private TextArea nameArea;
    @FXML
    private TextArea pronunciationArea;
    @FXML
    private TextArea definitionArea;
    public TextArea getNameArea() {
        return nameArea;
    }

    public void setNameArea(String nameArea) {
        this.nameArea.setText(nameArea);
    }

    public TextArea getPronunciationArea() {
        return pronunciationArea;
    }

    public void setPronunciationArea(String pronunciationArea) {
        this.pronunciationArea.setText(pronunciationArea);
    }

    public TextArea getDefinitionArea() {
        return definitionArea;
    }

    public void setDefinitionArea(String definitionArea) {
        this.definitionArea.setText(definitionArea);
    }

    public void modify(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("Views/LookingUpView.fxml"));
        AnchorPane searchRoot = loader.load();
        LookingUpController controller = loader.getController();
        String wordSelected = nameArea.getText();
        Word find = new Word();
        find.setName(wordSelected);
        int idSelected = App.getDictionary().findWord(find);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure to modify this word ?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeYes) {
            String newPronouce = pronunciationArea.getText();
            String newDef = definitionArea.getText();
            Word jdbcword = new Word(wordSelected, newPronouce, newDef);
            ModifyTask task = new ModifyTask(jdbcword);
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
            App.getDictionary().get(idSelected).setPronunciation(newPronouce);
            App.getDictionary().get(idSelected).setDefinition(newDef);
            controller.setTextInput(wordSelected);
            controller.setDefinitionArea(pronunciationArea.getText()
                    + "\n" + definitionArea.getText());
            SceneController.switchScene(event, searchRoot);
        }

    }

    public void exit(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.searchRoot);
    }
}
