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

import br.fecap.pi.quizzods.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {

    // DBHelper interno para gerenciamento do banco de dados
    private static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "UserDB.db";
        private static final String TABLE_NAME = "users";
        private static final String COL_1 = "ID";
        private static final String COL_2 = "username";
        private static final String COL_3 ="email";
        private static final String COL_4 ="senha";
        private Context context;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
            this.context = context;
        }



        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, senha TEXT)");
                Toast.makeText(context, "Banco de dados criado com sucesso!", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "Erro ao criar o banco de dados: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context, "Erro ao atualizar o banco de dados: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        // Inserção de usuário com tratamento de erro
        public boolean insertUser(String username, String email, String senha) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_2, username);
                contentValues.put(COL_3, email);
                contentValues.put(COL_4, senha);
                long result = db.insert(TABLE_NAME, null, contentValues);
                Log.d("SQLite", "Inserido: nome: " + username + ", Email: " + email + ", Senha: " + senha);
                if (result == -1) {
                    throw new SQLException("Erro ao inserir os dados");
                }
                Toast.makeText(context, "Usuário inserido com sucesso!", Toast.LENGTH_SHORT).show();
                return true;
            } catch (SQLException e) {
                Toast.makeText(context, "Erro ao inserir no banco de dados: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return false;
            } finally {
                db.close();
            }
        }

        // Consulta de todos os usuários
        public Cursor getAllUsers() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = null;
            try {
                res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
                if (res == null) {
                    throw new SQLException("Erro ao consultar os dados");
                }
                return res;
            } catch (SQLException e) {
                Toast.makeText(context, "Erro ao consultar o banco de dados: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

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




        // Configuração do Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://h6wr2f-3000.csb.app/")
                .addConverterFactory(GsonConverterFactory.create())
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
            Call<UpRequest.SignUpResponse> call = apiService.signUp(signUpRequest);
            call.enqueue(new Callback<UpRequest.SignUpResponse>() {
                @Override
                public void onResponse(Call<UpRequest.SignUpResponse> call, Response<UpRequest.SignUpResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignUp.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Salvando os dados no banco de dados localmente
                        boolean isInserted = dbHelper.insertUser(usernameText, emailText, senhaText);
                        if (isInserted) {
                            Toast.makeText(SignUp.this, "Dados salvos localmente", Toast.LENGTH_SHORT).show();
                        }

                        // Navegando para a MainActivity após o cadastro

                        Intent intent = new Intent(SignUp.this, menuJogo.class);
                        startActivity(intent);
                    } else {
                        Log.e("API_ERROR", "Erro no cadastro: " + response.code() + " " + response.message());

                        Toast.makeText(SignUp.this, "Erro no cadastro: " + response.message(), Toast.LENGTH_SHORT).show();
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