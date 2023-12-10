package englishlearningapp.englearning.JDBCConnection;

import englishlearningapp.englearning.DictionaryPackage.Word;
import javafx.concurrent.Task;

public class InsertTask extends Task<Void> {
    private Word word = new Word();
    public InsertTask(Word word) {
        this.word = word;
    }
    @Override
    protected Void call() throws Exception {
        JDBC_InsertData.insertWord(word);
        return null;
    }
}
