package com.autoshopping.authenticate.user;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "usuario", schema = "autenticacao")

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Timestamp data_alteracao;
    private Timestamp data_registro;
    private Boolean ativo;
    private String email;
    private String login;
    private String nome;
    private String path_foto;
    private String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
