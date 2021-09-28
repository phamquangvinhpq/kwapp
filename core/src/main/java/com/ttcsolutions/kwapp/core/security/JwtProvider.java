package com.ttcsolutions.kwapp.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.ttcsolutions.kwapp.commons.model.KwAccount;
import com.ttcsolutions.kwapp.commons.util.Constant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class JwtProvider {
  private final JWTVerifier verifier;
  private final Algorithm algorithm;
  private final Duration jwtTtl;

  public JwtProvider(@Value("${authentication.jwt.private-key}") String jwtPrivateKey,
                     @Value("${authentication.jwt.public-key}") String jwtPublicKey,
                     @Value("${authentication.jwt.ttl}") Duration jwtTtl) throws NoSuchAlgorithmException, InvalidKeySpecException {
    algorithm = Algorithm.RSA256(loadPublicKey(jwtPublicKey), loadPrivateKey(jwtPrivateKey));
    verifier = JWT.require(algorithm).build();
    this.jwtTtl = jwtTtl;
  }

  private RSAPrivateKey loadPrivateKey(String privateKeyPem) throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeyFactory kf = KeyFactory.getInstance("RSA");
    byte[] decoded = Base64.getDecoder().decode(privateKeyPem);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
    return (RSAPrivateKey) kf.generatePrivate(keySpec);
  }

  private RSAPublicKey loadPublicKey(String publicKeyPem) throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeyFactory kf = KeyFactory.getInstance("RSA");
    byte[] decoded = Base64.getDecoder().decode(publicKeyPem);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
    return (RSAPublicKey) kf.generatePublic(spec);
  }

  public Authentication decode(String token) {
    try {
      var jwt = verifier.verify(token);
      var principal = new KwAccount()
        .setId(jwt.getClaim("id").asLong())
        .setEmail(jwt.getClaim("email").asString());
      var authentication = new UsernamePasswordAuthenticationToken(principal, token,
        List.of(new SimpleGrantedAuthority(Constant.USER_ROLE)));
      authentication.setDetails(jwt);
      return authentication;
    }
    catch (JWTVerificationException e) {
      return null;
    }
  }

  public String generateToken(KwAccount kwAccount) {
    Instant issueMoment = Instant.now();
    Instant expireMoment = issueMoment.plus(jwtTtl.toMillis(), ChronoUnit.MILLIS);
    Long id = kwAccount.getId();
    String token = JWT.create()
      .withIssuedAt(Date.from(issueMoment))
      .withClaim("id", id)
      .withClaim("email", kwAccount.getEmail())
      .withExpiresAt(Date.from(expireMoment))
      .withJWTId(UUID.randomUUID().toString())
      .sign(this.algorithm);

    return token;
  }


  private Instant getIssueMoment(String token) {
    return JWT.decode(token).getIssuedAt().toInstant();
  }

  private Instant getExpireMoment(String token) {
    return JWT.decode(token).getExpiresAt().toInstant();
  }

}
