package einis.stemmer;

//import javax.annotation.Resource;
import java.io.*;

public class Dictionary {

    private static volatile Dictionary instance = new Dictionary();

    private int dictionaryLength = 221633;
    private String[] dictionaryLines = new String[dictionaryLength];

    private Dictionary() {
        openDictionary();
    }

    public static Dictionary getDictionary() {
        return instance;
    }

    private void openDictionary(){
        BufferedReader br = null;
        try {
            String filePath = "/rootwords.txt";
            InputStream inputStream = Dictionary.class.getResourceAsStream(filePath);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            br = new BufferedReader(inputReader);
            for (int i = 0; i < dictionaryLines.length; i++) dictionaryLines[i] = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String findRoot(String word){
        if(word.length() < 3)
            return word.toLowerCase();
        for (String line : dictionaryLines) {
            String lineWords[] = line.split(", ");
            for (String lineWord : lineWords) {
                if (word.toLowerCase().equals(lineWord.toLowerCase())) {
                    return lineWords[0].toLowerCase();
                }
            }
        }
        return word.toLowerCase() + "*";
    }
}