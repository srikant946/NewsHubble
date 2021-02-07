// This is a custom Adapter java file where the Objects of the class 'News' would be created and the list items would be created over here.
// The final arrayList however that consists of all the list items would be actually created in 'QueryUtils.java' File and that would be passed to 'NewsActivity.java' File where that (i.e the one that was created in QueryUtils.java) Arraylist would be stored in a variable and that variable would then be used for linking to custom array adapter.
// The data would come from 'QueryUtils.java' class which would passed to the constructor of 'News.java' File  and then that data would be accessed by the 'public' methods whose definition is present in the 'News.java' File.

package com.example.newshubble;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News>  // <News> is passed beside the ArrayAdapter to indicate that the ArrayAdapter source input is the 'News' Class
{

    public NewsAdapter(Context context, ArrayList<News> pNews)
    {
        super(context, 0, pNews);  // ArrayAdapter's constructor is being called here.. the Resource Id is kept 0 here so that the ArrayAdapter does not create a list item view for us manually, but we ourselves would create a list item view via our own getView() implementation.
    }

    // View Recycling
    // The below code would decide how the views would be shown up an hence it is written over here

    // This below part is crucial when we are implementing the use of custom adapters in our code.
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        // Creation/Reusing of List item view

        // Storing a view in the 'listItemView' variable
        View listItemView = convertView;

        // 'null' here means that if no views are there to reuse..if no views are there to be reused, we would inflate a new view according to the XML Layout file passed in the inflate() method
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        // The received object at the desired position fetched via the 'getItem()' method would be stored in a variable named 'news' and that would have a return Data type of Custom Class 'News' because that is the source input of our Superclass.
        News news = getItem(position);

        // Getting the Main TextView by its id
        TextView mainTextView = (TextView) listItemView.findViewById(R.id.main_text_view);
        mainTextView.setText(news.getName());   // The position that was stored in our variable..that gets passed to our getName() method and the data in the Textview gets set in our layout

        // Getting the Sub TextView by its id
        TextView subTextView = (TextView) listItemView.findViewById(R.id.sub_text_view);
        subTextView.setText(news.getNewsId());  // The position that was stored in our variable..that gets passed to our getNewsId() method and the data in the Textview gets set in our layout

        return listItemView;  // The list is then finally returned once its populated..
    }
}
