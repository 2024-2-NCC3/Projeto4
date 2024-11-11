package br.fecap.pi.quizzods;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import android.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UpRequest {
    // Classe de cadastro
    private String username;
    private String email;
    private String password;
    private String token;

    // Construtor do UpRequest
    public UpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = hashPassword(password); // Hashing da senha no construtor
    }

    // Método para hash da senha
    public String hashPassword(String password) {
        String salt = generateSalt(); // Gerar salt único para o usuário

        try {
            // Criação de KeySpec para o algoritmo PBKDF2
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.encodeToString(hash, Base64.DEFAULT); // Codificação do hash para armazenamento
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Erro ao hashear senha: " + e.getMessage());
        }
    }

    // Método para gerar salt único de maneira mais segura usando SecureRandom
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // Salt de 16 bytes (128 bits)
        random.nextBytes(salt); // Preenche o array salt com bytes aleatórios
        return Base64.encodeToString(salt, Base64.DEFAULT); // Codificando salt para armazenamento
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // A classe para resposta do servidor (Cadastro)
    public static class SignUpResponse {
        private boolean success;
        private String message;
        private String token;

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }

    // Classe para a requisição de login
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }

        // Getters
        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    // Classe para a resposta do servidor (Login)
    public static class LoginResponse {
        private boolean success;
        private String message;
        private String token;

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public String getToken() {
            return token;
        }
    }
}
