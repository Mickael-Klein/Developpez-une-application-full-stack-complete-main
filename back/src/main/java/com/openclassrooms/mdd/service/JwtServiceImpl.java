package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.serviceInterface.JwtInterface;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtInterface {

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private JwtDecoder jwtDecoder;

  @Override
  public String generateToken(long id) {
    String idToString = String.valueOf(id);
    Instant instantNow = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet
      .builder()
      .issuer("self")
      .issuedAt(instantNow)
      .expiresAt(instantNow.plus(30, ChronoUnit.DAYS))
      .subject(idToString)
      .build();

    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
      JwsHeader.with(MacAlgorithm.HS256).build(),
      claims
    );
    return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }

  @Override
  public Jwt decodeToken(String token) {
    return jwtDecoder.decode(token);
  }

  @Override
  public long getUserIdFromJwtLong(Jwt jwt) {
    return Long.parseLong(jwt.getSubject());
  }

  @Override
  public boolean areUserIdsMatching(
    long userIdFromToken,
    long userIdFromRequestPayload
  ) {
    return userIdFromToken == userIdFromRequestPayload;
  }
}
