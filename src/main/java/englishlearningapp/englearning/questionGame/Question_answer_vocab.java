package englishlearningapp.englearning.questionGame;

import englishlearningapp.englearning.JDBCConnection.JDBC_RetrieveData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Question_answer_vocab {

    private Random random = new Random();
    private int randomIndex;

    private List<String> question = new ArrayList<>();
    private List<String> answer = new ArrayList<>();
    private List<String> ipa;

    public Question_answer_vocab() throws SQLException {

        JDBC_RetrieveData.retrieveQuestionWords();
        JDBC_RetrieveData.retrieveQuestionIPA();
        JDBC_RetrieveData.retrieveQuestionMeaning();
        question = JDBC_RetrieveData.getQuestionWords();
        answer = JDBC_RetrieveData.getQuestionMeaning();
        ipa = JDBC_RetrieveData.getQuestionIPA();
    }

    public String getQuestion(int index) {

        String result = question.get(index);
        return result;
    }


    public int getRandom() {
        return this.randomIndex;
    }

    public void setRandom(int size) {

        randomIndex = random.nextInt(size - 1) + 1;
        while (randomIndex <= 0 || randomIndex >= 950) {
            randomIndex = random.nextInt(size - 1) + 1;
        }
    }

    public final List<String> EnteredWord = new ArrayList<>();

    public boolean checkEnterWord(String word) {
        for (int i = 0; i < EnteredWord.size() - 1; i++) {
            if (EnteredWord.get(i).equals(word)) return false;
        }
        return true;
    }

    public String getAnswer(int index) {
        return answer.get(index);
    }

    public String getAnswer(String s) {
        for (int i = 0; i < question.size(); i++) {
            if (question.get(i).equals(s)) {
                return getAnswer(i);
            }
        }
        return "khong";
    }

    public String getrandomAnswer(int idCorrect) {

        setRandom(950);
        int index = getRandom();
        String text = answer.get(index);
        while (!checkEnterWord(text)) {
            setRandom(950);
            index = getRandom();
            text = answer.get(index);
            System.out.println(index);
        }
        EnteredWord.add(text);
        return text;
    }
}
