package br.fecap.pi.quizzods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDB.db";
    private static final int DATABASE_VERSION = 1; // Adicionando versão do banco de dados
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "username";
    private static final String COL_3 = "email";
    private static final String COL_4 = "password";
    private static final String COL_5 = "salt"; // FOI ADICIONADO POR CAUSA DA MUDANÇA NO BANCO
    private Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Cria a tabela "users" com as colunas especificadas
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT,  " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT,"+
                COL_5 + "TEXT)";

        try {
            db.execSQL(createTable);
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



    // Função para gerar um salt seguro de 16 bytes

    public String getSaltForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT salt FROM users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            String salt = cursor.getString(cursor.getColumnIndexOrThrow("salt"));
            cursor.close();
            return salt;
        }
        cursor.close();
        return null;  // Retorna null se o usuário não for encontrado
    }


    public boolean insertUser(String username, String email, String password, String salt) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {


            //String salt = generateSalt();


            // Aplica hashing na senha ante de armazenar
            String hashedPassword = hashPassword(password, salt);

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, username);
            contentValues.put(COL_3, email);
            contentValues.put(COL_4, hashedPassword); // Armazena a senha
            contentValues.put(COL_5, salt);

            // Insere os dados e armazena o resultado na tabela
            long result = db.insert(TABLE_NAME, null, contentValues);
            Log.d("SQLite", "Inserido: nome: " + username + ", Email: " + email + ", Senha: " + password);
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


    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Executa uma consulta SQL para obter a senha e o salt do usuário fornecido
        Cursor cursor = db.rawQuery("SELECT " + COL_4 + ", " + COL_5 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            try {
                // Obtem a senha e o salt armazenados para o usuário
                String storedHash = cursor.getString(cursor.getColumnIndexOrThrow(COL_4));
                String salt = cursor.getString(cursor.getColumnIndexOrThrow(COL_5));

                // Gera o hash da senha fornecida com o salt armazenado
                String hashedPassword = hashPassword(password, salt);

                return storedHash.equals(hashedPassword);  // Compara os hashes
            } finally {
                cursor.close();
            }
        }
        cursor.close();
        return false;  // Retorna false se o usuário não foi encontrado


    }

    public static String hashPassword(String password, String salt) {
       // String SALT = "umsaltounico"; // Salt fixo, para simplificação

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // Codificação do hash para o armazenamento
            return Base64.encodeToString(hash, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao hashear senha: " + e.getMessage());
        }
    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


    }

