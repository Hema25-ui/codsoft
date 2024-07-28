package com.example.quoteapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {

    private ListView listViewFavorites;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        listViewFavorites = findViewById(R.id.listViewFavorites);
        sharedPreferences = getSharedPreferences("quotes", MODE_PRIVATE);

        displayFavoriteQuotes();
    }

    private void displayFavoriteQuotes() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        ArrayList<String> favoritesList = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            favoritesList.add((String) entry.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoritesList);
        listViewFavorites.setAdapter(adapter);
    }
}
