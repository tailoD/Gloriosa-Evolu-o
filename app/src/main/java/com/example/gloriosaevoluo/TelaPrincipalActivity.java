package com.example.gloriosaevoluo;

import static com.example.gloriosaevoluo.R.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gloriosaevoluo.databinding.ActivityTelaPrincipalBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipalActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private RecyclerView recyclerView;
    private SpeedrunAdapter adapter;
    private List<Speedrun> allSpeedruns;
    private List<Speedrun> filteredList;

    private ActivityTelaPrincipalBinding binding;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaPrincipalActivity.this, RegistrarSpeedrunActivity.class);
                startActivity(intent);

            }
        });

        db = FirebaseFirestore.getInstance();

        editTextSearch = findViewById(R.id.editTextSearch);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        filteredList = new ArrayList<>();

        allSpeedruns = new ArrayList<>();
        adapter = new SpeedrunAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        // Fetch speedruns from Firestore
        fetchSpeedruns();

        binding.buttonSearch.setOnClickListener(v -> onSearch());
        binding.bntMeusJogos.setOnClickListener(v ->
                startActivity(new Intent(TelaPrincipalActivity.this, MeusJogosActivity.class)));
        binding.btnSair.setOnClickListener(v ->
                startActivity(new Intent(TelaPrincipalActivity.this, LoginActivity.class)));
    }
    private void fetchSpeedruns() {

        db.collection("speedrun")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            Speedrun speedrun = document.toObject(Speedrun.class);
                            allSpeedruns.add(speedrun);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(TelaPrincipalActivity.this, "Error fetching speedruns", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onSearch() {
        String query = editTextSearch.getText().toString().trim();

        if (TextUtils.isEmpty(query)) {
            // Show message and clear filter if search is empty
            filteredList.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Por favor, insira um termo de pesquisa", Toast.LENGTH_SHORT).show();
            return;
        }

        filteredList.clear();
        for (Speedrun speedrun : allSpeedruns) {
            if (speedrun.getGame().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(speedrun);
            }
        }

        // Atualiza o adaptador com a lista filtrada
        adapter.notifyDataSetChanged();

    }

}