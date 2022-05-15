package com.example.todoapplication.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapplication.R;
import com.example.todoapplication.adaptar.NotesAdapter;
import com.example.todoapplication.model.Note;
import com.example.todoapplication.viewmodel.NotesViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    SearchView searchView;
    LinearLayout notesNotFound;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String username = getIntent().getStringExtra("username");
         username = username.replace(username.charAt(0), (username.charAt(0) + "").toUpperCase().charAt(0));
        setTitle("Hello, " + username);

        addNotesBtn = findViewById(R.id.add_note);
        searchView = findViewById(R.id.search_view_bar);
        notesNotFound = findViewById(R.id.notes_not_found);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        addNotesBtn.setOnClickListener(event -> {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
        });

        recyclerView = findViewById(R.id.notes_recycler_view);

        notesViewModel.getAllNotes.observe(this, notes -> {

            //Logging all the added notes for debugging
            notes.forEach(note -> {
                Log.d("NOTE", "Created Note : " + note.title);
            });

            if(notes.size() > 0) {
                notesNotFound.setVisibility(View.GONE);
            }

            if(notes.size() <= 0 && notesNotFound.getVisibility() == View.GONE) {
                notesNotFound.setVisibility(View.VISIBLE);
            }

            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            notesAdapter = new NotesAdapter(MainActivity.this, notes);
            recyclerView.setAdapter(notesAdapter);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String s) {
                notesViewModel.getAllNotes.observe(MainActivity.this, notes -> {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                    List<Note> noteList = notes.stream().filter(note -> note.title.contains(s)).collect(Collectors.toList());
                    notesAdapter = new NotesAdapter(MainActivity.this, noteList);
                    recyclerView.setAdapter(notesAdapter);
                });
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if(item.getItemId() == R.id.exit_menu) {
            BottomSheetDialog sheetDialog = new BottomSheetDialog(MainActivity.this);
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.logout_sheet, (LinearLayout) findViewById(R.id.logout_sheet_linear_layout));
            sheetDialog.setContentView(view);

            TextView yesBtn, noBtn;

            yesBtn = view.findViewById(R.id.logout_true);
            noBtn = view.findViewById(R.id.logout_false);

            yesBtn.setOnClickListener(event -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                Toast.makeText(this, "Logged Out Successfully !", Toast.LENGTH_SHORT).show();
            });

            noBtn.setOnClickListener(event -> {
                sheetDialog.dismiss();
            });

            sheetDialog.show();
        }

        return true;
    }
}