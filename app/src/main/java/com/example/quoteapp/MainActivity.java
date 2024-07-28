package com.example.quoteapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView tvQuote, tvHelp;
    private ImageView ivFavorite, ivCopy;
    private ListView listViewFavorites;
    private String[] quotes;
    private SharedPreferences sharedPreferences;
    private String currentQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHelp = findViewById(R.id.tvHelp);
        tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHelpEmail();
            }
        });

        tvQuote = findViewById(R.id.tvQuote);
        ivFavorite = findViewById(R.id.ivFavorite);
        ivCopy = findViewById(R.id.ivCopy);
        listViewFavorites = findViewById(R.id.listViewFavorites);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        Button btnShare = findViewById(R.id.btnShare);

        quotes = getResources().getStringArray(R.array.quotes);
        sharedPreferences = getSharedPreferences("quotes", MODE_PRIVATE);

        displayRandomQuote();
        displayFavoriteQuotes();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRandomQuote();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQuote();
            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
                displayFavoriteQuotes();
            }
        });

        ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyQuoteToClipboard();
            }
        });
    }

    private void sendHelpEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","help@example.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help Request");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void displayRandomQuote() {
        Random random = new Random();
        currentQuote = quotes[random.nextInt(quotes.length)];
        tvQuote.setText(currentQuote);
        updateFavoriteIcon();
    }

    private void shareQuote() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, currentQuote);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void toggleFavorite() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(currentQuote)) {
            editor.remove(currentQuote);
            ivFavorite.setImageResource(R.drawable.ic_star_border);
        } else {
            editor.putString(currentQuote, currentQuote);
            ivFavorite.setImageResource(R.drawable.ic_star);
        }
        editor.apply();
    }

    private void updateFavoriteIcon() {
        if (sharedPreferences.contains(currentQuote)) {
            ivFavorite.setImageResource(R.drawable.ic_star);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_star_border);
        }
    }

    private void copyQuoteToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("quote", currentQuote);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Quote copied to clipboard", Toast.LENGTH_SHORT).show();
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
