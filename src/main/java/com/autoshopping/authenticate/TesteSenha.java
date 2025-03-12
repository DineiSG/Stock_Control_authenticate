package com.autoshopping.authenticate;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaDigitada = "aspa0273"; // A senha que você tentou usar no login
        String senhaNoBanco = "$2a$10$9RSF4i2KqGGIaSYCscbFQOBfbvx8zF1Iu6MWi0t4ISKgm.5fx7k9W"; // A senha salva no banco

        boolean senhaCorreta = encoder.matches(senhaDigitada, senhaNoBanco);
        System.out.println("A senha digitada está correta? " + senhaCorreta);
    }
}
