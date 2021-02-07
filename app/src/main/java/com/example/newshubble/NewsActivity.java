/* This File i.e 'NewsActivity.java' Would mediate all the Interaction between the Custom class and the custom array Adapter
How, the ArrayList would be created and how the ArrayAdapter would be set up in conjunction with the ArrayList is setup from here
*/

package com.example.newshubble;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

// For Loader related stuff
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import android.widget.TextView;

// For network Connectivity status
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>>
{
    public static final String LOG_TAG = NewsActivity.class.getName();

    // API that would be queried is set up from here
    private static final String REQUEST_URL = "https://hubblesite.org/api/v3/news?page=all";

    // Loader assigned an Id
    private static final int NEWS_LOADER_ID = 1;

    // To access and modify the instance of the NewsAdapter, we need to make it a global variable in the NewsActivity.
    /** Adapter for the list of newss */
    private NewsAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if(networkInfo != null && networkInfo.isConnected())
        {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }
        else
        {
            // First, we hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // We have identified our TextView that would display the message of "No Data Found" by its id.
        // Now, the Text of 'No Data Found' would be shown only if the setText() message on our Textview in our onLoadFinished() method executes
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        // Since, we want to specify intents for clicks on list item, we would use the 'setOnItemClickListener' method.
        // We need to declare an OnItemClickListener on the ListView. OnItemClickListener is an interface, which contains a single method onItemClick().
        // We declare an anonymous class that implements this interface, and provides customized logic for what should happen in the onItemClick() method.

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News currentNews = mAdapter.getItem(position);

                // The Intent constructor (that we want to use) requires a Uri object, so we need to convert our URL (in the form of a String) into a URI. We know that our news URL is a more specific form of a URI, so we can use the Uri.parse method
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                // The Intent constructor also accepts a URI for the data resource we want to view, and Android will sort out the best app to handle this sort of content.
                // For instance, if the URI represented a location, Android would open up a mapping app.
                // In this case, the resource is an HTTP URL, so Android will usually open up a browser.
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle)
    {
        return new NewsLoader(this, REQUEST_URL);
    }
    @Override

    public void onLoadFinished(Loader<List<News>> loader, List<News> news)
    {

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // The message to be displayed if no News are found is declared here and the reason it is declared on the onLoadFinished() method is because we dont want to directly show up "No News found" when we are just starting the app.
        // We want to show the message once the loader has its work done and then if no data exists, then we would show the message
        mEmptyStateTextView.setText(R.string.no_news);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // We trigger the ListView to update.
        if (news != null && !news.isEmpty())
        {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader)
    {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}