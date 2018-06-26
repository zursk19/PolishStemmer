package einis.stemmer;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StemmerText {

    private String input;
    private String wordStringArray[];
    private StemmerWord stemmedWords[];
    private StringBuilder stemmedText = new StringBuilder();

    public StemmerText (String input) {
        this.input = input;                                     //  Input comes in as a string of text
        stripChars();                                           //  Remove characters that are not part of Polish Alphabet
        wordStringArray = makeWordStringArray();                //  Convert the single string into a String array
        stemmedWords = new StemmerWord[wordStringArray.length]; //
        convertToStemmerWordArray();                            //  Convert the String array into StemmerWord array
        stemWords();                                            //  Stem each word within StemmerWord array
        makeNewText();
    }

    // Strip characters that are not important to our Stemmer
    private void stripChars() {
        input = input.replaceAll("[^a-żA-Ż]*[^a-żA-Ż]", " ");
    }

    // Takes a string and returns a String array of words (based on spaces)
    public String[] makeWordStringArray () {
        return input.split(" ");
    }

    // Get StemmerWords array
    public StemmerWord[] getStemmerWords(){
        return stemmedWords;
    }

    // Takes word array and converts it into StemmerWord array
    private void convertToStemmerWordArray () {
        for (int i = 0; i < wordStringArray.length; i++) stemmedWords[i] = new StemmerWord(wordStringArray[i]);
    }

    // Calls for StemmerWord array to become converted in that class. Replaces existing array set
    private void stemWords() {
//        stemmedWords.stream().forEach()
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (StemmerWord word : stemmedWords) {
            executor.submit(word);
//            word.stemWord();
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeNewText() {
        stemmedText.append("Here is your stemmed text! Note that words followed by a '*' character have not been stemmed.\r\n\r\n");
        for (StemmerWord newWord : stemmedWords) {
            stemmedText.append(newWord.getRootWord() + " ");
        }
    }

    public String getNewText() {
        return stemmedText.toString();
    }
}
