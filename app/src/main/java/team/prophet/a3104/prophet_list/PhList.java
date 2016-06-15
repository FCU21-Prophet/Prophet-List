package team.prophet.a3104.prophet_list;

public class PhList {
    private long id;
    private String listTitle;
    private String listContent;
    private String tag;
    private String date;
    private String time;

    /*----------------------constructor----------------------*/
    public PhList()
    {
    }

    public PhList(long id, String listTitle, String listContent, String tag, String date, String time)
    {
        this.id = id;
        this.listTitle = listTitle;
        this.listContent = listContent;
        this.tag = tag;
        this.date = date;
        this.time = time;
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

    public String getTime()
    {
        return time;
    }

    public String getDate()
    {
        return date;
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

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setTime(String time)
    {
        this.time = time;
    }
}
