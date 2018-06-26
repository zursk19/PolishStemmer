package einis.stemmer;

public class StemmerWord implements Runnable {

    String word;
    String rootWord;
    Dictionary dictionary = Dictionary.getDictionary();

    public StemmerWord (String word) {
        this.word = word;
    }

    @Override
    public void run() {
        stemWord();
    }

    // Gets word at current instance
    public String getWord() {
        return word;
    }

    public String getRootWord() {
        return rootWord;
    }

    public void stemWord () {
        rootWord = dictionary.findRoot(word);
    }
}
