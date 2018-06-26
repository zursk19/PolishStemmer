package einis.stemmer;

import java.io.*;

public class appIO {

    private String filePath;
    private String filePathNew;

    public  appIO (String filePath) {
        this.filePath = filePath;
        this.filePathNew = newFileName();
    }

    public String readTextContent(){
        if(filePath != null) {
            BufferedReader br = null;
            String line;
            StringBuilder fullText = new StringBuilder("");
            try {
                br = new BufferedReader(new FileReader(filePath));
                while ((line = br.readLine()) != null) {
                    fullText.append(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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
            return fullText.toString();
        } else
            return "No file";
    }

    public String newFileName() {
        return filePath.substring(0, filePath.indexOf(".", filePath.length() - 5)) + "_stem.txt";
    }

    public void writeTextContent(String content) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathNew));
            writer.write(content);
            writer.close();
    }
}