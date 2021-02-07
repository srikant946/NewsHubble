package com.example.newshubble;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

// Loads a list of news by using an AsyncTask to perform the network request to the given URL.

public class NewsLoader extends AsyncTaskLoader<List<News>>
{

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
        Log.e(LOG_TAG, "Error with creating URL");
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground()
    {
        if (mUrl == null)
        {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<News> news = com.example.newshubble.QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}