package com.fffrowies.databasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private DatabaseHelper myHelper;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    private static final int LOADER_ID = 1976;

    final String[] from =
            new String[] { DatabaseHelper._ID, DatabaseHelper.NAME, DatabaseHelper.ADDRESS };

    final int[] to = new int[] { R.id.id, R.id.name, R.id.address };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        myHelper = new DatabaseHelper(this);
        myHelper.open();

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(
                this, R.layout.activity_view_record, null, from, to, 0);

        listView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView nameTextView = view.findViewById(R.id.name);
                TextView addressTextView = view.findViewById(R.id.address);

                String id = idTextView.getText().toString();
                String title = nameTextView.getText().toString();
                String desc = addressTextView.getText().toString();

                Intent modify_intent =
                        new Intent(getApplicationContext(), ModifyEmployeeActivity.class);
                modify_intent.putExtra("name", title);
                modify_intent.putExtra("address", desc);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {
            Intent add_mem = new Intent(this, AddEmployeeActivity.class);
            startActivity(add_mem);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new MyLoader(this, myHelper);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in. (The fragment will take care of closing the
        // old cursor once we return).
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed. We need to make sure we are no
        // longer using it.
        adapter.swapCursor(null);
    }
}
