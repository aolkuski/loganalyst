package replacer;

import io.PropertyHandler;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Replacer {
    private static String text = "";


    /**
     * Method responsible for running replacements on given text.
     * It also reads all rules and stores them in memory.
     *
     * @param textToBeReplaced
     * @return
     */
    public String replace(String textToBeReplaced) {
        ExpressionsReader er = new ExpressionsReader();
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String path = props.getPropertyValue("regexRulesFilePath");
        try {
            er.loadExpressions(path);
        } catch (CorruptedRulesFileException e) {
            e.printStackTrace();
            Logger.getAnonymousLogger().log(Level.SEVERE, "Rules loading error. No replacement done.");
        }
        text = textToBeReplaced;
        LinkedList<Expression> expressions = er.getExpressions();
        for (Expression ex : expressions) {
            String replacement = format(ex.getReplacement());
            if (ex.isRepetetive()) {
                String tmp = text;
                text = text.replaceAll(ex.getMatcher(), replacement);
                while (!tmp.equals(text)) {
                    tmp = text;
                    text = text.replaceAll(ex.getMatcher(), replacement);
                }
            } else {
                text = text.replaceAll(ex.getMatcher(), replacement);
            }
        }
        return text;
    }

    private String format(String replacement) {
        char[] repl = replacement.toCharArray();
        for (int i = 0; i < repl.length; i++) {
            if (repl[i] == '\\') {
                if (repl[i + 1] == 'n') {
                    repl[i] = '\n';
                    repl[i + 1] = ' ';
                }
                if (repl[i + 1] == 'r') {
                    repl[i] = '\r';
                    repl[i + 1] = ' ';
                }
                if (repl[i + 1] == 't') {
                    repl[i] = '\t';
                    repl[i + 1] = ' ';
                }
                if (repl[i + 1] == 'R') {
                    repl[i] = '\n';
                    repl[i + 1] = ' ';
                }
            }
        }
        return String.valueOf(repl);
    }
}
