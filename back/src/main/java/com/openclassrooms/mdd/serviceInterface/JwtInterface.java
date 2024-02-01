package com.openclassrooms.mdd.serviceInterface;

import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtInterface {
  String generateToken(long id);

  Jwt decodeToken(String token);

  long getUserIdFromJwtLong(Jwt jwt);

  boolean areUserIdsMatching(
    long userIdFromToken,
    long userIdFromRequestPayload
  );
}
