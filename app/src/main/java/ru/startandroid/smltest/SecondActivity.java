package ru.startandroid.smltest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by ase911 on 24.03.2017.
 */

public class SecondActivity extends Activity{
    SimpleCursorAdapter secondAdapter;
    ListView lvSecondData;
    Cursor forSecondC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        String field;
        field = getIntent().getExtras().getString("key");

        DB forSecond = new DB(this);
        forSecond.open();

        forSecondC = forSecond.getOneString(field);
        startManagingCursor(forSecondC);
        // формируем столбцы сопоставления
        String[] from = new String[] { DB.COLUMN_ID, DB.COLUMN_PERCENT };
        int[] to = new int[] { R.id.ivImg, R.id.tvText };

        // создаем адаптер и настраиваем список
        secondAdapter = new SimpleCursorAdapter(this, R.layout.item, forSecondC, from, to);
        lvSecondData = (ListView) findViewById(R.id.lvSecondData);
        lvSecondData.setAdapter(secondAdapter);
        forSecond.close();
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
