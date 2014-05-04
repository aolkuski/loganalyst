package replacer;

public class Loader
{
    private static String pathToRules = "";
     
    static String getPathToRules()
    {
        return pathToRules;
    }

    static void setPathToRules(String pathToRules)
    {
        Loader.pathToRules = pathToRules;
    }

    public static void init(String pPathToRules) throws CorruptedRulesFileException{
        pathToRules = (pPathToRules == null) ? "/regexes/rules" : pPathToRules;
        ExpressionsReader er = new ExpressionsReader();
        er.loadExpressions(pathToRules);
    }
}
