package br.fecap.pi.quizzods;

public class UpRequest {
    // classe do cadastro
    private String username;
    private String email;
    private String password;

    public UpRequest(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password =password;
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
    }
}
