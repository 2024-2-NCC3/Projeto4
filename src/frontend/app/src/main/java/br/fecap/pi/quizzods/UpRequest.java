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


    // Construtor do UpRequest
    public UpRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password; // Envie a senha em texto puro para que o backend faça o hashing

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

        public class SaltResponse {
        }
    }

    // Classe para a resposta do servidor (Login)
    public static class LoginResponse {
        private boolean success;
        private String message;
        private String salt;
        private String hashedPassword;


        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
        public String getSalt(){
            return salt;
        }
        public String getHashedPassword(){
            return hashedPassword;
        }

        public static class SaltResponse{
            private boolean success;
            private String message;
            private String salt;

            public boolean isSuccess() {
                return success;
            }

            public String getMessage() {
                return message;
            }
            public String getSalt(){
                return salt;
            }
        }



    }
}
