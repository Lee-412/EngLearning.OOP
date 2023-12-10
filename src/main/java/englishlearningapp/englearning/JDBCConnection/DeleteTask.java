package englishlearningapp.englearning.JDBCConnection;

import englishlearningapp.englearning.DictionaryPackage.Word;
import javafx.concurrent.Task;

public class DeleteTask extends Task<Void> {
    private Word word = new Word();
    public DeleteTask(Word word) {
        this.word = word;
    }
    @Override
    protected Void call() throws Exception {
        JDBC_DeleteData.deleteData(word);
        return null;
    }
}
