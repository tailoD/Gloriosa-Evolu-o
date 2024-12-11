package com.example.gloriosaevoluo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gloriosaevoluo.databinding.ActivityCadastroBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput, passwordInput;

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.editEmail);
        passwordInput = findViewById(R.id.editSenha);
        Button registerButton = findViewById(R.id.btnCriarConta);

        registerButton.setOnClickListener(v -> criarContaFirebase());

        binding.bntVolteLogin.setOnClickListener(v ->
                startActivity(new Intent(CadastroActivity.this, LoginActivity.class)));
    }

    private void criarContaFirebase() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Senha muito curta", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Erro no cadastro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
