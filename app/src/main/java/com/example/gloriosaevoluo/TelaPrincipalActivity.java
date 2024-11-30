package com.example.gloriosaevoluo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipalActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private SpeedrunAdapter adapter;
    private List<Speedrun> allSpeedruns;
    private List<Speedrun> filteredList;
    private Button buttonSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);


        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        allSpeedruns = new ArrayList<>();
        filteredList = new ArrayList<>();


        adapter = new SpeedrunAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        allSpeedruns.add(new Speedrun("Super Mario Bros.", "Any%", "John Smith", "00:04:55", "USA"));
        allSpeedruns.add(new Speedrun("The Legend of Zelda", "100%", "Liam O'Connor", "00:45:23", "Ireland"));
        allSpeedruns.add(new Speedrun("Minecraft", "Any%", "Emma Brown", "01:30:12", "UK"));
        allSpeedruns.add(new Speedrun("Super Mario 64", "Any%", "Michael Johnson", "00:16:40", "USA"));
        allSpeedruns.add(new Speedrun("The Legend of Zelda: Ocarina of Time", "Any%", "Taro Yamada", "00:19:20", "Japan"));
        allSpeedruns.add(new Speedrun("Tetris", "Marathon", "Stefan Müller", "00:23:58", "Germany"));
        allSpeedruns.add(new Speedrun("Donkey Kong", "Any%", "Oliver Wilson", "00:04:10", "Australia"));
        allSpeedruns.add(new Speedrun("Sonic the Hedgehog", "Any%", "Lucas Pereira", "00:22:05", "Brazil"));
        allSpeedruns.add(new Speedrun("Halo: Combat Evolved", "Any%", "Jack Anderson", "01:05:12", "USA"));
        allSpeedruns.add(new Speedrun("Final Fantasy VII", "Any%", "Rachel Thompson", "10:34:56", "Canada"));
        allSpeedruns.add(new Speedrun("Half-Life", "Any%", "David Williams", "00:43:29", "UK"));
        allSpeedruns.add(new Speedrun("Super Mario World", "Any%", "Jessica Martinez", "00:11:23", "USA"));
        allSpeedruns.add(new Speedrun("Super Metroid", "Any%", "Marie Dubois", "00:19:50", "France"));
        allSpeedruns.add(new Speedrun("The Elder Scrolls V: Skyrim", "Any%", "Matthew Brown", "03:15:12", "USA"));
        allSpeedruns.add(new Speedrun("Grand Theft Auto V", "Any%", "Daniel Garcia", "06:45:33", "Spain"));
        allSpeedruns.add(new Speedrun("The Witcher 3: Wild Hunt", "Any%", "Katarzyna Nowak", "05:50:24", "Poland"));
        allSpeedruns.add(new Speedrun("Crash Bandicoot", "Any%", "Ben Adams", "00:28:40", "Australia"));
        allSpeedruns.add(new Speedrun("Red Dead Redemption 2", "Any%", "Oliver Brown", "08:00:25", "UK"));
        allSpeedruns.add(new Speedrun("Portal", "Any%", "Leonard Schneider", "00:12:15", "Germany"));
        allSpeedruns.add(new Speedrun("Celeste", "Any%", "Maria Silva", "00:50:45", "Brazil"));
        allSpeedruns.add(new Speedrun("Dark Souls", "Any%", "Ethan Harris", "01:52:30", "USA"));
        allSpeedruns.add(new Speedrun("Hollow Knight", "Any%", "Haruki Takahashi", "01:15:10", "Japan"));
        allSpeedruns.add(new Speedrun("Bloodborne", "Any%", "Pierre Lefevre", "01:10:34", "France"));
        allSpeedruns.add(new Speedrun("Resident Evil 2", "Any%", "Christopher Lee", "01:25:05", "Canada"));
        allSpeedruns.add(new Speedrun("Metal Gear Solid", "Any%", "James Wilson", "00:53:22", "UK"));


        buttonSearch.setOnClickListener(v -> onSearch());
    }


    public void onSearch() {
        String query = editTextSearch.getText().toString().trim();

        if (TextUtils.isEmpty(query)) {

            filteredList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Por favor, insira um termo de pesquisa", Toast.LENGTH_SHORT).show();
            return;
        }


        List<Speedrun> tempList = new ArrayList<>();
        for (Speedrun speedrun : allSpeedruns) {
            if (speedrun.getGame().equalsIgnoreCase(query)) {
                tempList.add(speedrun);
            }
        }


        if (tempList.isEmpty()) {
            Toast.makeText(this, "Nenhum jogo encontrado com esse nome", Toast.LENGTH_SHORT).show();
        }


        filteredList.clear();
        filteredList.addAll(tempList);
        adapter.notifyDataSetChanged();
    }
}
