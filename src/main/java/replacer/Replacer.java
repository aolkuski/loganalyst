package replacer;

import java.util.LinkedList;

public class Replacer
{
    private static String text = "";

    public void replace()
    {
        ExpressionsReader er = new ExpressionsReader();
//        text = st.getContent();
        LinkedList<Expression> expressions = er.getExpressions();
        for (Expression ex : expressions)
        {
            String replacement = format(ex.getReplacement());
            if (ex.isRepetetive())
            {
                String tmp = text;
                text = text.replaceAll(ex.getMatcher(), replacement);
                while(!tmp.equals(text)){
                    tmp = text;
                    text = text.replaceAll(ex.getMatcher(), replacement);
                }
            } else {
                text = text.replaceAll(ex.getMatcher(), replacement);
            }
        }
//        st.setContent(text);
        return;
    }

    private String format(String replacement)
    {
        char[] repl = replacement.toCharArray();
        for (int i = 0; i < repl.length; i++)
        {
            if (repl[i] == '\\')
            {
                if (repl[i + 1] == 'n')
                {
                    repl[i] = '\n';
                    repl[i + 1] = ' ';
                }
                if (repl[i + 1] == 'r')
                {
                    repl[i] = '\r';
                    repl[i + 1] = ' ';
                }
                if (repl[i + 1] == 't')
                {
                    repl[i] = '\t';
                    repl[i + 1] = ' ';
                }
                if (repl[i + 1] == 'R')
                {
                    repl[i] = '\n';
                    repl[i + 1] = ' ';
                }
            }
        }
        return String.valueOf(repl);
    }
}
