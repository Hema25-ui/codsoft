package com.example.quoteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {

    private EditText etNotes;
    private SharedPreferences sharedPreferences;
    private String quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        etNotes = findViewById(R.id.etNotes);
        sharedPreferences = getSharedPreferences("notes", MODE_PRIVATE);

        quote = getIntent().getStringExtra("quote");
        String notes = sharedPreferences.getString(quote, "");
        etNotes.setText(notes);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(quote, etNotes.getText().toString());
        editor.apply();
    }
}
