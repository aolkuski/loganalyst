package replacer;

public class CorruptedRulesFileException extends Exception
{
    private String message;
    /**
     * 
     */
    private static final long serialVersionUID = 4333996370136618845L;

    public CorruptedRulesFileException(String msg){
        this.message = msg;
    }

    public String getMessage()
    {
        return message;
    }
    
    
    
}
