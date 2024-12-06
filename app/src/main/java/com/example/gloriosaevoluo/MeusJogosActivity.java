package com.example.gloriosaevoluo;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MeusJogosActivity extends AppCompatActivity {

    private EditText editTextUserId;  // Campo para inserir o userId
    private RecyclerView recyclerView;
    private SpeedrunAdapter adapter;
    private List<Speedrun> allSpeedruns;
    private List<Speedrun> filteredList;

    private Button buttonSearch;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_jogos);  // Alterado para o layout novo

        db = FirebaseFirestore.getInstance();

        // Inicializa os componentes
        editTextUserId = findViewById(R.id.editTextUserId);
        recyclerView = findViewById(R.id.recyclerView);
        buttonSearch = findViewById(R.id.buttonSearchUserId);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filteredList = new ArrayList<>();
        allSpeedruns = new ArrayList<>();
        adapter = new SpeedrunAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        // Configura o botão de pesquisa
        buttonSearch.setOnClickListener(v -> onSearchUserId());
    }

    // Método para pesquisar speedruns com base no userId
    public void onSearchUserId() {
        String userId = editTextUserId.getText().toString().trim();

        if (TextUtils.isEmpty(userId)) {
            // Exibe mensagem se o campo estiver vazio
            Toast.makeText(this, "Por favor, insira um User ID válido", Toast.LENGTH_SHORT).show();
            return;
        }

        filteredList.clear();  // Limpa a lista filtrada

        // Busca no Firestore usando o userId fornecido
        fetchSpeedrunsByUserId(userId);
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
                            Toast.makeText(MeusJogosActivity.this, "Nenhuma speedrun encontrada para este User ID", Toast.LENGTH_SHORT).show();
                        }
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            Speedrun speedrun = document.toObject(Speedrun.class);
                            if (speedrun != null) {
                                filteredList.add(speedrun);
                                Log.d("MeusJogos", "Speedrun encontrada: " + speedrun.getGame()); // Log de depuração
                            }
                        }
                        // Atualiza a interface com os dados encontrados
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MeusJogosActivity.this, "Erro ao buscar speedruns: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("MeusJogos", "Erro na consulta", task.getException()); // Log de erro
                    }
                });
    }


}
