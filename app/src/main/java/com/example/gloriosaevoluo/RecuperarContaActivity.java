package com.example.gloriosaevoluo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gloriosaevoluo.databinding.ActivityRecuperarContaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContaActivity extends AppCompatActivity {

    private ActivityRecuperarContaBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnCriarConta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            auth = FirebaseAuth.getInstance();

            binding.btnRecuperarConta.setOnClickListener(v1 -> validaDados());

            return insets;
        });

    }

    private void validaDados() {
        String email = binding.textInformeEmail.getText().toString().trim();
        if (!email.isEmpty()) {
            recuperaContaFirebase(email);
        } else {
            Toast.makeText(this, "Informe seu e-mail", Toast.LENGTH_SHORT).show();
        }
    }

    private void recuperaContaFirebase(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Já pode verificar o seu e-mail", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
