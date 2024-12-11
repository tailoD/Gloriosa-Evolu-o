package com.example.gloriosaevoluo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gloriosaevoluo.databinding.ActivityMeusJogosBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MeusJogosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SpeedrunAdapter adapter;
    private List<Speedrun> filteredList;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private ActivityMeusJogosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMeusJogosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Inicializa os componentes
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filteredList = new ArrayList<>();
        adapter = new SpeedrunAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        binding.btnVoltar.setOnClickListener(v ->
                startActivity(new Intent(this, TelaPrincipalActivity.class)));


        // Obtém o userId do usuário autenticado
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId != null) {
            // Busca os speedruns do usuário autenticado
            fetchSpeedrunsByUserId(userId);
        } else {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    // Método que busca as speedruns do Firestore com base no userId
    private void fetchSpeedrunsByUserId(String userId) {
        db.collection("speedrun")
                .whereEqualTo("idUser", userId)  // Certifique-se de que o nome do campo é 'idUser'
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Verifica se algum documento foi retornado
                        if (task.getResult().isEmpty()) {
                            Toast.makeText(MeusJogosActivity.this, "Nenhuma speedrun encontrada para este usuário", Toast.LENGTH_SHORT).show();
                        }
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            Speedrun speedrun = document.toObject(Speedrun.class);
                            if (speedrun != null) {
                                filteredList.add(speedrun);
                            }
                        }
                        // Atualiza a interface com os dados encontrados
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MeusJogosActivity.this, "Erro ao buscar speedruns: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
