package englishlearningapp.englearning.JDBCConnection;

import englishlearningapp.englearning.DictionaryPackage.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_ModifyData {
    public static void updateData(Word word) throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        String name = word.getName();
        String pronunciation = word.getPronunciation();
        String definition = word.getDefinition();
        String queryDefinition = "UPDATE dicttable SET pronunciation = ?, definition = ? WHERE word = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryDefinition);
        preparedStatement.setString(1, pronunciation);
        preparedStatement.setString(2, definition);
        preparedStatement.setString(3, name);
        int affectedRows = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        System.out.println("Updated " + affectedRows + " row(s) successfully!");
    }
}
