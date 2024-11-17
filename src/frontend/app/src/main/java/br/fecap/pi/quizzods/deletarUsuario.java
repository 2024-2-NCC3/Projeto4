package br.fecap.pi.quizzods;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class deletarUsuario extends AppCompatActivity {



   private DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deletar_usuario);



        dbHelper = new DBHelper(this);  // Inicializando o banco de dados
        exibirUser();





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    //Vai exibir username e email após a busca no banco
    public void exibirUser(){
        String[] user = dbHelper.obterUser();  // Obtém o array com username e email
        String username = user [0];
        String email = user    [1];



        TextView textoNome = findViewById(R.id.textoNome);
        TextView textoEmail= findViewById(R.id.textoEmail);



        if (username != null && email != null) {
            textoNome.setText("Username: " + username);
            textoEmail.setText("Email: " + email);

        }


    }



}




