package com.example.testing;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    TextView tvPopName, tvPopNumber, tvPopType, tvFandom, tvOn, tvUltimate, tvPrice;
    EditText etPopName, etPopNumber, etPopType, etFandom, etOn, etUltimate, etPrice;
    Button button, deleteButton;
    ListView listLV;

    SimpleCursorAdapter adapter;

    Cursor cursor;

    String[] fromColumns = {
            FunkoPopCP.COL1_NAME,
            FunkoPopCP.COL2_NAME,
            FunkoPopCP.COL3_NAME,
            FunkoPopCP.COL4_NAME,
            FunkoPopCP.COL5_NAME,
            FunkoPopCP.COL6_NAME,
            FunkoPopCP.COL7_NAME
    };
    int[] toViews = {
            R.id.tv_list_name,
            R.id.tv_list_number,
            R.id.tv_list_type,
            R.id.tv_list_fandom,
            R.id.tv_list_on,
            R.id.tv_list_ultimate,
            R.id.tv_list_price
    };

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPopName = findViewById(R.id.tv_popName);
        etPopName = findViewById(R.id.et_popName);
        tvPopNumber = findViewById(R.id.tv_popNumber);
        etPopNumber = findViewById(R.id.et_popNumber);
        tvPopType = findViewById(R.id.tv_popType);
        etPopType = findViewById(R.id.et_popType);
        tvFandom = findViewById(R.id.tv_fandom);
        etFandom = findViewById(R.id.et_fandom);
        tvOn = findViewById(R.id.tv_on);
        etOn = findViewById(R.id.et_on);
        tvUltimate = findViewById(R.id.tv_ultimate);
        etUltimate = findViewById(R.id.et_ultimate);
        tvPrice = findViewById(R.id.tv_price);
        etPrice = findViewById(R.id.et_price);
        button = findViewById(R.id.button);
        deleteButton = findViewById(R.id.button_delete);
        listLV = findViewById(R.id.list_LV);

    button.setOnClickListener(to_read_list);
    deleteButton.setOnClickListener(delete_from_list);
    }

    public void updateListUI() {
        Cursor cursor = getContentResolver().query(FunkoPopCP.CONTENT_URI, null, null, null, null);
        adapter = new SimpleCursorAdapter(
                getApplicationContext(),
                R.layout.list_item_funko,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listLV.setAdapter(adapter);
    }

    View.OnClickListener to_read_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Create a ContentValues object to hold the data
            ContentValues values = new ContentValues();
            values.put(FunkoPopCP.COL1_NAME, etPopName.getText().toString());
            values.put(FunkoPopCP.COL2_NAME, Integer.parseInt(etPopNumber.getText().toString()));
            values.put(FunkoPopCP.COL3_NAME, etPopType.getText().toString());
            values.put(FunkoPopCP.COL4_NAME, etFandom.getText().toString());
            values.put(FunkoPopCP.COL5_NAME, Boolean.parseBoolean(etOn.getText().toString()));
            values.put(FunkoPopCP.COL6_NAME, etUltimate.getText().toString());
            values.put(FunkoPopCP.COL7_NAME, Double.parseDouble(etPrice.getText().toString()));

            Uri newUri = getContentResolver().insert(FunkoPopCP.CONTENT_URI, values);

            updateListUI();
            Toast.makeText(getApplicationContext(), "FunkoPOP Stored", Toast.LENGTH_LONG).show();
        }
    };

    View.OnClickListener delete_from_list = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String selection = "_id = (SELECT MIN(_id) FROM " + FunkoPopCP.TABLE_NAME + ")";


            int deletedRows = getContentResolver().delete(FunkoPopCP.CONTENT_URI, selection, null);

            if (deletedRows > 0) {
                Toast.makeText(getApplicationContext(), "Oldest FunkoPop deleted", Toast.LENGTH_SHORT).show();
                updateListUI();
            } else {
                Toast.makeText(getApplicationContext(), "No FunkoPops to delete", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
