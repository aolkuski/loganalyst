package replacer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class ExpressionsReader
{
    private static LinkedList<Expression> expressions = new LinkedList<Expression>();

    void loadExpressions(String path) throws CorruptedRulesFileException
    {
        FileInputStream fis = null;
        InputStream is = null;
        BufferedReader br = null;
        try
        {
            is = getClass().getResourceAsStream(path);
            br = new BufferedReader(new InputStreamReader(is));
        }
        catch (NullPointerException npe)
        {
            try
            {
                fis = new FileInputStream(path);
                br = new BufferedReader(new InputStreamReader(fis));
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            while (br.ready())
            {
                String line = br.readLine();
                while (!line.startsWith("desc:"))
                {
                    line = br.readLine();
                    if(!br.ready()){
                        throw new CorruptedRulesFileException("\tMissing 'desc:' as the first line of each rule in rules file. \nLine: "+line);
                    }
                }
                Expression expr = new Expression();
                expr.setDescription(line.substring(5));

                line = br.readLine();
                if (!line.startsWith("find:"))
                {
                    System.err.println("\tNo 'find:' clause after line with description");
                    throw new CorruptedRulesFileException("\tWrong rules file - check if each rule has 3 lines, one after another: 'desc:', 'find:', 'replace:'");
                }
                expr.setMatcher(line.substring(5));

                line = br.readLine();
                if (!line.startsWith("replace:"))
                {
                    System.err.println("\tNo 'replace:' clause after line with matcher (regex phase to found");
                    throw new CorruptedRulesFileException("\tWrong rules file - check if each rule has 3 lines, one after another: 'desc:', 'find:', 'replace:'");
                }
                expr.setReplacement(line.substring(8));
                
                line = br.readLine();
                if (!line.startsWith("repeat:"))
                {
                    System.err.println("\tNo 'repeat:' clause after line with matcher (regex phase to found");
                    throw new CorruptedRulesFileException("\tWrong rules file - check if each rule has 4 lines, one after another: 'desc:', 'find:', 'replace:', 'repeat'.");
                }
                expr.setRepeat(Boolean.valueOf(line.substring(7)));
                expressions.add(expr);
            }
        }
        catch (CorruptedRulesFileException ex)
        {
            System.err.println("\tLoading rules was not performed correctly.");
            System.err.println(ex.getMessage());
            throw ex;
        }
        catch (IOException ioe){
            throw new CorruptedRulesFileException(ioe.getMessage());
        }
        finally
        {
            try
            {
                if(fis == null){
                    is.close();
                } else {
                    fis.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    LinkedList<Expression> getExpressions()
    {
        return expressions;
    }


}
