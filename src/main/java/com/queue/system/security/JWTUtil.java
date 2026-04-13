package com.queue.system.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(String email) {
      try{

          byte[] signingKey = secret.getBytes();
          JWSSigner signer = new MACSigner(signingKey);

          JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                  .subject(email)
                  .issueTime(new Date())
                  .expirationTime(new Date(System.currentTimeMillis() + expiration))
                  .build();

          SignedJWT  signedJWT = new SignedJWT(
                  new JWSHeader(JWSAlgorithm.HS256),
                  claimsSet
          );
          signedJWT.sign(signer);

          return signedJWT.serialize();

      } catch (KeyLengthException e) {
          throw new RuntimeException("Secret key terlalu pendek! Minimal 32 karakter untuk HS256", e);
      } catch (JOSEException e) {
          throw new RuntimeException("Error creating JWT token", e);
      }

    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret.getBytes());

            // Cek signature
            if (!signedJWT.verify(verifier)) {
                return false;
            }

            // Cek expired
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expirationTime.after(new Date());

        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try{
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        }catch (ParseException e) {
            throw new RuntimeException("Invalid JWT token", e);
        }
    }
}
