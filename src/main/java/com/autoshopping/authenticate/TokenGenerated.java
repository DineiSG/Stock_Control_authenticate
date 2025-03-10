package com.autoshopping.authenticate;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.security.Key;

public class TokenGenerated {

    public static void main(String[] args){
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String secureKey = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Chave gerada: " + secureKey);

    }
}
