package englishlearningapp.englearning.JDBCConnection;

import englishlearningapp.englearning.DictionaryPackage.Word;
import javafx.concurrent.Task;

public class ModifyTask extends Task<Void> {
    private Word word = new Word();
    public ModifyTask(Word word) {
        this.word = word;
    }
    @Override
    protected Void call() throws Exception {
        JDBC_ModifyData.updateData(word);
        return null;
    }
}
