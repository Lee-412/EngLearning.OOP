package englishlearningapp.englearning.JDBCConnection;

import englishlearningapp.englearning.DictionaryPackage.Word;

import java.sql.*;

public class JDBC_InsertData {
    public static void insertWord (Word word) throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        String name = word.getName();
        String pronunciation = word.getPronunciation();
        String definition = word.getDefinition();
        String queryDefinition = "INSERT INTO dicttable (word, pronunciation, definition, id) VALUES"
                + "(" + "'" + name + "'" +", " + "'" + pronunciation + "'" + ", " + "'" + definition + "'" + ", " + 0 +")";
        PreparedStatement preparedStatement = connection.prepareStatement(queryDefinition);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}
