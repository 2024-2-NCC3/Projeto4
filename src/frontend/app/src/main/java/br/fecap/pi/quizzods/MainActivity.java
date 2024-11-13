package br.fecap.pi.quizzods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button createAccountBtn;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.btnEntrar);
        createAccountBtn = findViewById(R.id.btnCriar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://h6wr2f-3000.csb.app/") // URL do backend
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Realiza a chamada de login no servidor
                performLogin(username, password);
            }
        });

        createAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }

    private void performLogin(String username, String password) {
        UpRequest.LoginRequest loginRequest = new UpRequest.LoginRequest(username, password);

        Call<UpRequest.LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<UpRequest.LoginResponse>() {
            @Override
            public void onResponse(Call<UpRequest.LoginResponse> call, Response<UpRequest.LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpRequest.LoginResponse loginResponse = response.body();

                    // Verifica se a resposta indica sucesso
                    if ("Login bem-sucedido".equals(loginResponse.getMessage())) {
                        // Obtém o salt da resposta
                        String salt = loginResponse.getSalt();

                        // Gera o hash da senha com o salt retornado
                        String hashedPassword = hashPassword(password, salt);
                        //Log.d("AQUI TEM ALGUM ERRO", "ERRO NA SENHA?");

                        if (hashedPassword != null && hashedPassword.equals(loginResponse.getHashedPassword())) {


                        } else {
                            Toast.makeText(MainActivity.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Erro no login: " + loginResponse.getMessage(), Toast.LENGTH_SHORT).show(); // login está com algum erro 
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Falha ao fazer login", Toast.LENGTH_SHORT).show();
                }

                // Realiza o login com o hash da senha e vai para próxima tela (arrumar depois)
                Intent intent = new Intent(MainActivity.this, menuJogo.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UpRequest.LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LoginError", "Erro de rede", t);
            }
        });
    }

    private String hashPassword(String password, String salt) {
        // Verifica se a senha e o salt são válidos
        if (password == null || salt == null) {
            Log.e("HashingError", "Senha ou salt nulos");
            return null;
        }

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 192); // Ajuste para 192 bits
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.e("HashingError", "Erro ao hashear a senha", e);
            return null;
        }
    }
}