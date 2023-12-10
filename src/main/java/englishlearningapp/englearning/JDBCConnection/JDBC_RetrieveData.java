package englishlearningapp.englearning.JDBCConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JDBC_RetrieveData {
    private static TreeMap<String, Integer> dataWords = new TreeMap<>();
    private static HashMap<Integer, String> pronuntiations = new HashMap<>();
    private static HashMap<Integer, String> definitons = new HashMap<>();
    private static List<String> questionWords = new ArrayList<>();
    private static List<String> questionIPA = new ArrayList<>();
    private static List<String> questionMeaning = new ArrayList<>();
    public static void retrieveQuestionWords() throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        Statement statement = connection.createStatement();
        String queryName = "SELECT Vocabulary FROM vocab";
        ResultSet resultSet = statement.executeQuery(queryName);
        while (resultSet.next()) {
            String item = resultSet.getString("Vocabulary").toLowerCase();
            questionWords.add(item);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
    public static void retrieveQuestionIPA() throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        Statement statement = connection.createStatement();
        String queryName = "SELECT Pronunciation FROM vocab";
        ResultSet resultSet = statement.executeQuery(queryName);
        while (resultSet.next()) {
            String item = resultSet.getString("Pronunciation").toLowerCase();
            questionIPA.add(item);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
    public static void retrieveQuestionMeaning() throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        Statement statement = connection.createStatement();
        String queryName = "SELECT Meaning FROM vocab";
        ResultSet resultSet = statement.executeQuery(queryName);
        while (resultSet.next()) {
            String item = resultSet.getString("Meaning");
            questionMeaning.add(item);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
    public static void retrieveWordData() throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        Statement statement = connection.createStatement();
        String queryName = "SELECT word FROM dicttable";
        ResultSet resultSetName = statement.executeQuery(queryName);
        while (resultSetName.next()) {
            int rowNum = resultSetName.getRow();
            String wordName = resultSetName.getString("word").toLowerCase();
            dataWords.put(wordName, rowNum);
        }
        resultSetName.close();
        statement.close();
        connection.close();
    }
    public static void retrievePronunciation () throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        Statement statement = connection.createStatement();
        String queryPronunciation = "SELECT pronunciation FROM dicttable";
        ResultSet resultSetPronounce = statement.executeQuery(queryPronunciation);
        while (resultSetPronounce.next()) {
            int rowNum = resultSetPronounce.getRow();
            String wordPronounce = resultSetPronounce.getString("pronunciation");
            pronuntiations.put(rowNum, wordPronounce);
        }
        resultSetPronounce.close();
        statement.close();
        connection.close();
    }
    public static void retrieveDefinition () throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();
        Statement statement = connection.createStatement();
        String queryDefinition = "SELECT definition FROM dicttable";
        ResultSet resultSetDefinition = statement.executeQuery(queryDefinition);
        while (resultSetDefinition.next()) {
            int rowNum = resultSetDefinition.getRow();
            String wordPronounce = resultSetDefinition.getString("definition");
            definitons.put(rowNum, wordPronounce);
        }
        resultSetDefinition.close();
        statement.close();
        connection.close();
    }

    public static List<String> getQuestionWords() {
        return questionWords;
    }

    public static List<String> getQuestionIPA() {
        return questionIPA;
    }

    public static List<String> getQuestionMeaning() {
        return questionMeaning;
    }

    public static TreeMap<String, Integer> getDataWords() {
        return dataWords;
    }

    public void setDataWords(TreeMap<String , Integer> dataWords) {
        this.dataWords = dataWords;
    }

    public static HashMap<Integer, String> getPronuntiations() {
        return pronuntiations;
    }

    public static void setPronuntiations(HashMap<Integer, String> pronuntiations) {
        JDBC_RetrieveData.pronuntiations = pronuntiations;
    }

    public static HashMap<Integer, String> getDefinitons() {
        return definitons;
    }
    public static void setDefinitons(HashMap<Integer, String> definitons) {
        JDBC_RetrieveData.definitons = definitons;
    }
    public static String[] retrieveVocabulary() throws SQLException {
        Connection connection = JDBC_Connect.getJDBCConnection();

        Statement statement = connection.createStatement();
        String queryVocabulary = "SELECT word FROM dicttable";
        ResultSet resultSetVocabulary = statement.executeQuery(queryVocabulary);

        List<String> vocabularyList = new ArrayList<>();
        while (resultSetVocabulary.next()) {
            String word = resultSetVocabulary.getString("word").toLowerCase();
            vocabularyList.add(word);
        }
        resultSetVocabulary.close();
        statement.close();
        connection.close();

        return vocabularyList.toArray(new String[0]);
    }



    public static void main (String[] args) throws SQLException {
        int i = 0;
        JDBC_RetrieveData.retrieveQuestionWords();
        for (int j = 0; j < 10; j++) {
            System.out.println(questionWords.get(j));
        }
        /*for (Map.Entry<String, Integer> entry : dataWords.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
            if(i == 9) break;
            i++;
        }*/
    }
}
