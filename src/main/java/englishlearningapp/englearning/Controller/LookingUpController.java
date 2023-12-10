package englishlearningapp.englearning.Controller;

import animatefx.animation.BounceIn;
import englishlearningapp.englearning.App;
import englishlearningapp.englearning.DictionaryPackage.Dictionary;
import englishlearningapp.englearning.DictionaryPackage.Word;
import englishlearningapp.englearning.JDBCConnection.DeleteTask;
import englishlearningapp.englearning.TextToSpeech.TTS;
import englishlearningapp.englearning.TextToSpeech.TexttoSpeechTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Timer;

public class LookingUpController {
    public Button searchBtn;
    public Button gameBtn;
    public Button translateBtn;
    @FXML
    private TextField textInput;
    @FXML
    private TextArea definitionArea;
    private String queryString = "";
    private String wordSelected = "";
    public void setTextInput(String input){ textInput.setText(input);}

    public void setDefinitionArea(String newdef) {
        definitionArea.setText(newdef);
    }

    public LookingUpController() throws IOException, SQLException {
    }

    public void clickGame (ActionEvent event) throws IOException {
        new BounceIn(gameBtn).play();
        wordSelected = "";
        queryString = "";
        SceneController.switchScene(event, SceneController.gameRoot);
    }
    public void clickTranslate (ActionEvent event) throws IOException {
        new BounceIn(translateBtn).play();
        wordSelected = "";
        queryString = "";
        SceneController.switchScene(event, SceneController.translateRoot);
    }
    public void onAddBtn(ActionEvent event) throws IOException {
        SceneController.switchScene(event, SceneController.addViewRoot);
    }
    public void onDelete(ActionEvent event) {
        if(wordSelected.equals("")) return;
        Word finder = new Word();
        finder.setName(wordSelected);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure to delete this word ?");
        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeYes) {
            App.getDictionary().remove(App.getDictionary().findWord(finder));
            DeleteTask task = new DeleteTask(new Word(wordSelected, " ", " "));
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }
        definitionArea.setText("");
        wordSelected = "";
    }
    public void onCustom(ActionEvent event) throws IOException {
        if(wordSelected.equals("")) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("Views/ModifyWordView.fxml"));
        AnchorPane modifyRoot = loader.load();
        ModifyController controller = loader.getController();
        controller.setNameArea(wordSelected);
        Word find = new Word();
        find.setName(wordSelected);
        int idSelected = App.getDictionary().findWord(find);
        controller.setPronunciationArea(App.getDictionary().get(idSelected).getPronunciation());
        controller.setDefinitionArea(App.getDictionary().get(idSelected).getDefinition());
        SceneController.switchScene(event, modifyRoot);

    }
    public void inputWordHanddle (KeyEvent e) throws SQLException {
        ListView<String> resultListView = new ListView<>();
        // Render prefixes.
        ObservableList<String> wordNames = FXCollections.observableArrayList();
        for (int i = 0; i < App.getDictionary().size(); i++){
            wordNames.add(App.getDictionary().get(i).getName());
        }
        queryString = textInput.getText().toLowerCase().trim();
        ObservableList<String> filteredList = FXCollections.observableArrayList();

        for(String wordName : wordNames) {
            if(wordName.toLowerCase().startsWith(queryString)){
                filteredList.add(wordName);
            }
        }
        resultListView.setItems(filteredList);
        resultListView.getStyleClass().add("wordListView");
        SceneController.updateScene(e,"add",resultListView);
        // Render definition and pronunciation of items.
        resultListView.setOnMouseClicked((MouseEvent event) -> {
            Image img = null;
            try {
                img = new Image((App.class.getResource("src/image/speaker.png")).openStream());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ImageView speakerIcon = new ImageView();
            speakerIcon.setImage(img);
            speakerIcon.getStyleClass().add("speaker");
            speakerIcon.setFitHeight(26);
            speakerIcon.setFitWidth(29);
            Pane speakerContainer = new Pane();
            speakerContainer.setPrefWidth(26);
            speakerContainer.setPrefHeight(29);
            speakerContainer.setLayoutX(758);
            speakerContainer.setLayoutY(494);
            speakerContainer.getStyleClass().add("speaker-container");
            if(event.getClickCount() == 1) {
                SceneController.updateScene(e, "add", speakerIcon);
                SceneController.updateScene(e, "add", speakerContainer);
                wordSelected = resultListView.getSelectionModel().getSelectedItem();
                //set Definition area.
                for(int i = 0; i < App.getDictionary().size(); i++){
                    if(App.getDictionary().get(i).getName().equals(wordSelected)) {
                        definitionArea.setText(App.getDictionary().get(i).getPronunciation()
                                + "\n" + App.getDictionary().get(i).getDefinition() + "\n");
                        break;
                    }
                }
                //set speaker.
            } else {
                wordSelected = "";
            }
            try {
                speakerContainer.setOnMouseClicked((MouseEvent mevent) -> {
                        if (mevent.getClickCount() == 1) {
                            if(TTS.isIsConnected()) {
                                TexttoSpeechTask task = new TexttoSpeechTask(wordSelected);
                                Thread th = new Thread(task);
                                th.setDaemon(true);
                                speakerIcon.setDisable(true);
                                th.start();
                            } else {
                                Platform.runLater(() -> {
                                    AlertController.showNotConnectInternet();
                                });
                            }

                    }

                });
            } catch (Exception ex) {
                throw new RuntimeException(ex);
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
