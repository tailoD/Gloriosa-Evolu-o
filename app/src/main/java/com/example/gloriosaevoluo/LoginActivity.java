package com.example.gloriosaevoluo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gloriosaevoluo.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnCriarConta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            return insets;
        });

        binding.textCriarConta.setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.btnLogin.setOnClickListener(v -> validaDados());

        binding.textRecuperarConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));


    }
    private void validaDados(){
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty()){
            if(!senha.isEmpty()){



                loginFirebase(email, senha);

            }else {
                Toast.makeText(this, "Informe sua senha", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginFirebase(String email, String senha){
        mAuth.signInWithEmailAndPassword(
                email,senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "pode entrar, chefia", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
            }
        });
    }


}