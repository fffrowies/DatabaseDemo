package com.fffrowies.databasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper myHelper;

    private ListView listView;

    private SimpleCursorAdapter adapter;

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

        Cursor c = myHelper.getAllEmployees();

        adapter = new SimpleCursorAdapter(
                this, R.layout.activity_view_record, c, from, to, 0);

        listView.setAdapter(adapter);
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
}
