package ru.startandroid.smltest;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by ase911 on 06.05.2017.
 */

public class ThirdActivity extends Activity{
    Button btnUpd;
    EditText etID, etPercent;

    SimpleCursorAdapter thirdAdapter;
    ListView lvThirdData;
    Cursor forThirdC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        btnUpd = (Button) findViewById(R.id.buttonOk);
        etID = (EditText) findViewById(R.id.editText1);
        etPercent = (EditText) findViewById(R.id.editText2);

        DB db = new DB(this);
        db.open();
        forThirdC = db.getModifyString();
        startManagingCursor(forThirdC);

        String[] from = new String[] { DB.COLUMN_ID, DB.COLUMN_PERCENT };
        int[] to = new int[] { R.id.ivImg, R.id.tvText };

        thirdAdapter = new SimpleCursorAdapter(this, R.layout.item, forThirdC, from, to);
        lvThirdData = (ListView) findViewById(R.id.lvThirdData);
        lvThirdData.setAdapter(thirdAdapter);
        db.close();
    }

    public void onMyButtonClick(View view) {

        String id = etID.getText().toString();
        String percent = etPercent.getText().toString()+"%";
        switch (view.getId()) {
            case R.id.buttonOk:
                DB db = new DB(this);
                db.open();
                db.upDate(percent, id);
                forThirdC = db.getModifyString();
                startManagingCursor(forThirdC);

                String[] from = new String[] { DB.COLUMN_ID, DB.COLUMN_PERCENT };
                int[] to = new int[] { R.id.ivImg, R.id.tvText };

                thirdAdapter = new SimpleCursorAdapter(this, R.layout.item, forThirdC, from, to);
                lvThirdData = (ListView) findViewById(R.id.lvThirdData);
                lvThirdData.setAdapter(thirdAdapter);
                db.close();
        }
    }


}
