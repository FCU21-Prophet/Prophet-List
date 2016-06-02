package team.prophet.a3104.prophet_list;

public class PhList {
    private long id;
    private String listTitle;
    private String listContent;

    /*----------------------constructor----------------------*/
    public PhList()
    {
    }

    public PhList(long id, String listTitle, String listContent)
    {
        this.id = id;
        this.listTitle = listTitle;
        this.listContent = listContent;
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
}
