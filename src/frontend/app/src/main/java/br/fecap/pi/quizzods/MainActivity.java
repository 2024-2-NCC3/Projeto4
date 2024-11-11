package br.fecap.pi.quizzods;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);  // Inicializando o banco de dados



        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginBtn = findViewById(R.id.btnEntrar);

       /* loginBtn.setOnClickListener(v -> {
                    String username = usernameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    Log.i("Login", "Username: " + username + ", Password: " + password);
                }); */

        createAccountBtn = findViewById(R.id.btnCriar);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }


                String salt = generateSalt(username);


                // Hashear a senha antes de enviar para o servidor
                String hashedPassword = hashPassword(password, salt);





                // Criando a requisição de login

                UpRequest.LoginRequest loginRequest = new UpRequest.LoginRequest(username, password);


                // Configurando o Retrofit
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://h6wr2f-3000.csb.app/") // URL base da sua API
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService apiService = retrofit.create(ApiService.class);

                // Fazendo a requisição de login
                Call<UpRequest.LoginResponse> call = apiService.login(loginRequest);
                call.enqueue(new Callback<UpRequest.LoginResponse>() {
                    @Override
                    public void onResponse(Call<UpRequest.LoginResponse> call, Response<UpRequest.LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            UpRequest.LoginResponse loginResponse = response.body();
                            if (loginResponse.isSuccess()) {
                                Toast.makeText(MainActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();

                                // Salvar o token no SharedPreferences, caso necessário
                                TokenManager tokenManager = new TokenManager(MainActivity.this);
                                tokenManager.saveToken(loginResponse.getToken());

                                // Navegar para a próxima atividade
                                Intent intent = new Intent(MainActivity.this, menuJogo.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Erro no login: " + response.message(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<UpRequest.LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    // Função para gerar salt com base no username
    private String generateSalt(String username) {
        // Pode ser mais seguro, mas aqui é gerado com um prefixo simples
        return "salt-" + username;

    }
    private String hashPassword(String password, String salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // Em vez de lançar uma exceção, vamos apenas logar o erro e retornar null
            Log.e("HashingError", "Erro ao hashear a senha", e);
            return null; // Retorna null em caso de erro
        }
    }


}


