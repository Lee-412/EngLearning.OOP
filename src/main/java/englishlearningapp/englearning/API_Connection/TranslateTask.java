package englishlearningapp.englearning.API_Connection;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


public class TranslateTask extends Task<String> {
    private TextArea inputLang;
    private TextArea outputLang;
    private Button translateBtn;
    private String text;
    public TranslateTask(TextArea inputLang, TextArea outputLang, Button translateBtn, String text) {
        this.inputLang = inputLang;
        this.outputLang = outputLang;
        this.translateBtn = translateBtn;
        this.text = text;
    }
    @Override
    protected String call() throws Exception {
        String res = "";
        if(inputLang.getText().equals("")) {
            inputLang.setText("vi");
            outputLang.setText("en");
            res = TranslateAPIConnection.translateText("vi", "en", text);
        }else if (inputLang.getText().equals("vi")) {
            res = TranslateAPIConnection.translateText("vi", "en", text);
        }else {
            res = TranslateAPIConnection.translateText("en", "vi", text);
        }
        return res;
    }
}
