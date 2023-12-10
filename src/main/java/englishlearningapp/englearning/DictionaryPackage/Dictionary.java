package englishlearningapp.englearning.DictionaryPackage;

import englishlearningapp.englearning.JDBCConnection.JDBC_RetrieveData;
import java.sql.SQLException;
import java.util.*;
class WordComparator implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getName().compareTo(o2.getName());
    }
}

public class Dictionary extends ArrayList<Word> {
    public Dictionary() throws SQLException {
        JDBC_RetrieveData.retrieveWordData();
        JDBC_RetrieveData.retrievePronunciation();
        JDBC_RetrieveData.retrieveDefinition();
        TreeMap<String,Integer> inputWords = JDBC_RetrieveData.getDataWords();
        HashMap<Integer, String> inputPronuns = JDBC_RetrieveData.getPronuntiations();
        HashMap<Integer,String> inputDefs = JDBC_RetrieveData.getDefinitons();

        for (Map.Entry<String,Integer> entry : inputWords.entrySet()) {
            Word word = new Word();
            word.setId(entry.getValue());
            word.setName(entry.getKey());
            word.setPronunciation(inputPronuns.get(entry.getValue()));
            word.setDefinition(inputDefs.get(entry.getValue()));
            this.add(word);
        }
    }

    public void sort() {
        this.sort(new WordComparator());
    }

    public int findWord(Word word) {
        String nameToFind = word.getName();
        int left = 0;
        int right = this.size() - 1;
        while(left < right) {
            int distance = (right - left) / 2;
            if(distance < 1) distance++;
            int mid = left + distance ;
            if(this.get(mid).getName().equals(nameToFind)) return mid;
            else if(this.get(mid).getName().compareTo(nameToFind) < 0) left = mid + 1;
            else right = mid - 1;
        }
        if(!this.get(left).getName().equals(nameToFind)) return -1;
        return left;
    }

}
