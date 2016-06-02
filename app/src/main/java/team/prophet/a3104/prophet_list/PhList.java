package team.prophet.a3104.prophet_list;

public class PhList {
    private long id;
    private String listTitle;
    private String listContent;
    private String tag;

    /*----------------------constructor----------------------*/
    public PhList()
    {
    }

    public PhList(long id, String listTitle, String listContent, String tag)
    {
        this.id = id;
        this.listTitle = listTitle;
        this.listContent = listContent;
        this.tag = tag;
    }

    /*----------------------GETTER----------------------*/

    public long getId()
    {
        return id;
    }

    public String getListTitle()
    {
        return listTitle;
    }

    public String getListContent()
    {
        return listContent;
    }

    public String getTag()
    {
        return tag;
    }

    /*----------------------SETTER----------------------*/

    public void setId(long id)
    {
        this.id = id;
    }

    public void setListTitle(String listTitle)
    {
        this.listTitle = listTitle;

    }

    public void setListContent(String listContent)
    {
        this.listContent = listContent;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
