package englishlearningapp.englearning.questionGame;

import englishlearningapp.englearning.Controller.GameViewController;
import englishlearningapp.englearning.JDBCConnection.JDBC_RetrieveData;

import java.sql.SQLException;
import java.util.*;

public class BotAnswerGenerator {
    private static final ArrayList<String> chosse = new ArrayList<String>();

    public TreeMap<String, Integer> vocab = new TreeMap<>();
    public static final String[] vocabulary;

    static {
        try {
            vocabulary = JDBC_RetrieveData.retrieveVocabulary();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BotAnswerGenerator() throws SQLException {
    }

    private static final int vocabularySize = vocabulary.length;
    private static final Random random = new Random();
    static Random rd = new Random();
    public static String generateRandomBotAnswers() {

        int size = 150000;
        int index = rd.nextInt(size - 1);
        String botAnswers = vocabulary[index];
        while(!checkStyleWorld(botAnswers)) {
            index = rd.nextInt(size - 1);
            botAnswers = vocabulary[index];
        }
        return botAnswers;
    }

    public static boolean checkWord(ArrayList<String> array, String word) {

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).equals(word)) return true;
        }
        return false;
    }
    public static boolean checkPlayerWord(String word) {
        for(int i = 0; i < vocabulary.length; i++ ) {
            if(vocabulary[i].equals(word)) return true;
        }
        return false;
    }

    public static boolean checkStyleWorld(String word) {
        if (word.contains("-") || word.contains(".") || word.contains("'")
                || word.contains(",") || word.contains(" ")
                || word.contains("_")) {
            System.out.println("no");
            return false;
        }
        System.out.println("yes");
        return true;
    }

    public static String getWordStartingWith(char startChar) {
        String result = null;

        for (int i = 0; i < vocabulary.length; i++) {
            String word = String.valueOf(vocabulary[i]);
            if (word.toLowerCase().charAt(0) == Character.toLowerCase(startChar)) {
                if (checkStyleWorld(word) && !checkWord(chosse, word)) {
                    int lengthWord = rd.nextInt(7);
                    if (word.length() >= 3) {
                        result = word;
                        chosse.add(word);
                        break;
                    }
                }
            }
        }
        if (result == null) {
            System.out.println("null");
        }
        return result;
    }

}
