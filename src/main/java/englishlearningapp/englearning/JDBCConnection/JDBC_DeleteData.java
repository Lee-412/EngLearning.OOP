package englishlearningapp.englearning.JDBCConnection;

import englishlearningapp.englearning.DictionaryPackage.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC_DeleteData {
    public static void deleteData(Word word) throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        String name = word.getName();
        String queryDefinition = "DELETE FROM dicttable WHERE word = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(queryDefinition);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
}
