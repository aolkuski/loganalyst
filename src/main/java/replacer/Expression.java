package replacer;

public class Expression
{
    private String description;
    private String matcher;
    private String replacement;
    private boolean repeat;


    public Expression()
    {
    };

    public Expression(String pDesc, String pMatcher, String pReplace, boolean pRepeat)
    {
        this.description = pDesc;
        this.matcher = pMatcher;
        this.replacement = pReplace;
        this.repeat = pRepeat;
    }

    public String getDescription()
    {
        return description;
    }

    public String getMatcher()
    {
        return matcher;
    }

    public String getReplacement()
    {
        return replacement;
    }

    void setDescription(String description)
    {
        this.description = description;
    }

    void setMatcher(String matcher)
    {
        this.matcher = matcher;
    }

    void setReplacement(String replacement)
    {
        this.replacement = replacement;
    }
    
        
    void setRepeat(boolean repeat)
    {
        this.repeat = repeat;
    }

    @Override
    public String toString(){
        return this.getDescription()+"\nfind: "+this.getMatcher()+"\nreplace with: "+this.getReplacement()+"and will be repeated: "+this.isRepetetive();
    }

    public boolean isRepetetive()
    {
        return repeat;
    }

    public void setRepeat(Boolean pRepeat)
    {
        this.repeat = pRepeat;
    }


}
