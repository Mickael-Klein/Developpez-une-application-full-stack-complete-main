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

/**
 * Implementation of the JwtInterface service.
 */
@Service
public class JwtServiceImpl implements JwtInterface {

  @Autowired
  private JwtEncoder jwtEncoder;

  @Autowired
  private JwtDecoder jwtDecoder;

  /**
   * Generates a JWT token.
   *
   * @param id The ID of the user for whom the token is generated.
   * @return The generated JWT token.
   */
  @Override
  public String generateToken(long id) {
    String idToString = String.valueOf(id);
    Instant instantNow = Instant.now();
    JwtClaimsSet claims = JwtClaimsSet
      .builder()
      .issuer("self")
      .issuedAt(instantNow)
      .expiresAt(instantNow.plus(1, ChronoUnit.DAYS))
      .subject(idToString)
      .build();

    JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
      JwsHeader.with(MacAlgorithm.HS256).build(),
      claims
    );
    return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
  }

  /**
   * Decodes a JWT token.
   *
   * @param token The JWT token to decode.
   * @return The decoded JWT.
   */
  @Override
  public Jwt decodeToken(String token) {
    return jwtDecoder.decode(token);
  }

  /**
   * Extracts the user ID from a JWT.
   *
   * @param jwt The JWT from which to extract the user ID.
   * @return The user ID extracted from the JWT.
   */
  @Override
  public long getUserIdFromJwtLong(Jwt jwt) {
    return Long.parseLong(jwt.getSubject());
  }

  /**
   * Checks if the user IDs from the token and the request payload match.
   *
   * @param userIdFromToken The user ID extracted from the token.
   * @param userIdFromRequestPayload The user ID from the request payload.
   * @return True if the user IDs match, false otherwise.
   */
  @Override
  public boolean areUserIdsMatching(
    long userIdFromToken,
    long userIdFromRequestPayload
  ) {
    return userIdFromToken == userIdFromRequestPayload;
  }
}
