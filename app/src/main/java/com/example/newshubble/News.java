package com.example.newshubble;

// Based on the API we got, we fetch out the fields we desire from the API and create a Separate 'News' custom class.
// From here, there would be 5 variables defined: 'news_id', 'name', and 'url'
public class News
{

    String news_id, name, url;
    public News() {}

    public News(String news_id, String name, String url)
    {
        this.news_id = news_id;
        this.name = name;
        this.url = url;
    }

    public String getNewsId()
    {
        return news_id;
    }

    public String getName()
    {
        return name;
    }
    public String getUrl() {return url;}

}
