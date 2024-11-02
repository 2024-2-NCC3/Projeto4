package br.fecap.pi.quizzods;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import android.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UpRequest {
    // classe do cadastro
    private String username;
    private String email;
    private String password;
    private String token;


    public UpRequest(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password =password;
        this.password = hashPassword(password);
    }





    // Método de has para a senha

    private String hashPassword(String password) {
        String SALT = "umsaltounico"; // salt será exclussivo por usuario

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
             return Base64.encodeToString(hash,Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao hashear senha: " + e.getMessage());

        }
        //return SALT
    }
    // a classe criada para a resposta do servidor

    public class SignUpResponse{
        private boolean success;
        private String message;

        public boolean isSuccess (){
            return success;
        }
        public String getMessage(){
            return message;
        }
        public  String getToken(){
            return token;
        }
    }
}
