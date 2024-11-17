package br.fecap.pi.quizzods;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import android.util.Base64;
import br.fecap.pi.quizzods.ApiService;
import br.fecap.pi.quizzods.R;
import br.fecap.pi.quizzods.TokenManager;
import br.fecap.pi.quizzods.UpRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    // DBHelper interno para gerenciamento do banco de dados


    private Context context;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DBHelper(this);  // Inicializando o banco de dados


        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email_input);
        EditText senha = findViewById(R.id.password_input);
        Button btnConta = findViewById(R.id.btnCriarConta);






        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {

                    TokenManager tokenManager = new TokenManager (this);
                    String token = tokenManager.getToken();
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer "+ token)
                            .build();
                    return chain.proceed(newRequest);


                })
                .build();

        // Configuração do Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://h6wr2f-3000.csb.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Listener para o botão de criar conta
        btnConta.setOnClickListener(v -> {
            String usernameText = username.getText().toString();
            String emailText = email.getText().toString();
            String senhaText = senha.getText().toString();
            if (usernameText.isEmpty() || emailText.isEmpty() || senhaText.isEmpty()) {
                Toast.makeText(SignUp.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }


            Toast.makeText(SignUp.this, "Seu username é " + usernameText, Toast.LENGTH_SHORT).show();

            // Criando a requisição de cadastro
            UpRequest signUpRequest = new UpRequest(usernameText, emailText, senhaText);



            // Fazendo a requisição ao servidor
            Call<UpRequest.SignUpResponse> call = apiService.  signUp(signUpRequest);
            call.enqueue(new Callback<UpRequest.SignUpResponse>() {
                @Override
                public void onResponse(Call<UpRequest.SignUpResponse> call, Response<UpRequest.SignUpResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {


                        // Obtém o token da resposta
                        String token = response.body().getToken();


                        TokenManager tokenManager = new TokenManager(SignUp.this);
                        tokenManager.saveToken(token); // Salva o token no SharedPreferences



                        Toast.makeText(SignUp.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();





                        // Salvando os dados no banco de dados localmente
                        String salt = generateSalt();
                        String hashedPassword = hashPassword(senhaText, salt);
                        boolean isInserted = dbHelper.insertUser(usernameText, emailText, hashedPassword, salt);
                        if (isInserted) {
                            Log.d("SignUp", "Dados salvos localmente");

                        }

                        // Navegando para a MainActivity após o cadastro

                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Log.e("API_ERROR", "Erro no cadastro: " + response.code() + " " + response.message());

                        Toast.makeText(SignUp.this, "Erro no cadastro: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                public String generateSalt() {
                    byte[] salt = new byte[16]; // 16 bytes é uma boa escolha para o salt
                    new SecureRandom().nextBytes(salt); // Gera um salt aleatório
                    return Base64.encodeToString(salt, Base64.DEFAULT); // Codifica o salt em Base64
                }


                public String hashPassword(String password, String salt) {
                    //String hashedPassword = hashPassword(senhaText, salt);
                    try {
                        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 192);
                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        byte[] hash = factory.generateSecret(spec).getEncoded();
                        return Base64.encodeToString(hash, Base64.DEFAULT); // Codifica o hash em Base64
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        Log.e("HashingError", "Erro ao hashear a senha", e);
                        return null;
                    }
                }


                @Override
                public void onFailure(Call<UpRequest.SignUpResponse> call, Throwable t) {
                    Log.e("NETWORK_ERROR", "Erro de rede: " + t.getMessage(), t);
                    Toast.makeText(SignUp.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Ajustando padding com base nos insets da janela
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}