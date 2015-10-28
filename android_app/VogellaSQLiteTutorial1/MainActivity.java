package com.example.minda.vogellasqlitetutorial1;

/**
 * Created by Minda on 10/24/2015.
 */
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
public class MainActivity extends ListActivity {
    // uses CommentsDataSource to create database
    private CommentsDataSource datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // pulls up last existing data
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creates new datasource, opens it
        datasource = new CommentsDataSource(this);
        datasource.open();

        // function to display all data in the datasource
        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;

        switch (view.getId()) {
            case R.id.add:
                // generates comments to load into database
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = datasource.createComment(comments[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    // deletes top comment of database
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
