package com.example.gloriosaevoluo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrarSpeedrunActivity extends AppCompatActivity {

    private EditText gameInput;
    private EditText categoryInput;
    private EditText playerNameInput;
    private EditText timeInput;
    private EditText regionInput;
    private Button registerButton;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_speedrun);

        gameInput = findViewById(R.id.game_input);
        categoryInput = findViewById(R.id.category_input);
        playerNameInput = findViewById(R.id.player_name_input);
        timeInput = findViewById(R.id.time_input);
        regionInput = findViewById(R.id.region_input);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSpeedrun();
            }
        });

    }

    private void registerSpeedrun() {
        String game = gameInput.getText().toString();
        String category = categoryInput.getText().toString();
        String playerName = playerNameInput.getText().toString();
        String time = timeInput.getText().toString();
        String region = regionInput.getText().toString();

        //retornar o id do usuário
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();

        // Crie um objeto Speedrun
        Speedrun speedrun = new Speedrun(game, category, playerName, time, region, userId);

        db.collection("speedrun")
                .add(speedrun)
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    Toast.makeText(this, "Speedrun registrado com sucesso!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, TelaPrincipalActivity.class));
                })
                .addOnFailureListener(e -> {
                    Log.w("TAG", "Error adding document", e);
                    Toast.makeText(this, "Erro ao registrar Speedrun", Toast.LENGTH_SHORT).show();
                });
    }
}

