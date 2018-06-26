package einis.stemmer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StemmerForm {

    private JPanel panelMain;
    private JButton runButton;
    private JTextArea useTheFileSelectorTextArea;
    private JTextArea statusTextArea;


    public StemmerForm() {
        setStatus("Status: Waiting for file selection");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setStatus("Status: In progress - Please Wait...");
                try {
                    JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new java.io.File("."));
                    fc.setDialogTitle("Select your text file");

                    if(fc.showOpenDialog(new JFrame("Select your .txt file")) == JFileChooser.APPROVE_OPTION) {
                        long startTime = System.currentTimeMillis();
                        String stringPath = fc.getSelectedFile().getAbsolutePath();
                        if (stringPath.endsWith(".txt")) {
                            appIO testFile = new appIO(stringPath);
                            StemmerText testStemmer = new StemmerText(testFile.readTextContent());
                            testFile.writeTextContent(testStemmer.getNewText());
                            setStatus("Status: Stemming completed!");
                        } else {
                            setStatus("Status: There was an error in your file selection");
                        }
                         long endTime = System.currentTimeMillis();

                        System.out.println(stringPath + "\n\nRun time: " + (endTime - startTime));
                    }
                } catch (Exception exc) {
                    System.out.println(exc.toString());
                }
            }
        });
    }

    private void setStatus(String status) {
        statusTextArea.setText(status);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Polish Language Stemmer");
        frame.setContentPane(new StemmerForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
