package com.openclassrooms.mdd.serviceInterface;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Interface for managing JWT tokens.
 */
public interface JwtInterface {
  /**
   * Generates a JWT token for a user ID.
   *
   * @param id The ID of the user.
   * @return The generated JWT token.
   */
  String generateToken(long id);

  /**
   * Decodes a JWT token.
   *
   * @param token The JWT token to decode.
   * @return The decoded JWT.
   */
  Jwt decodeToken(String token);

  /**
   * Extracts the user ID from a JWT.
   *
   * @param jwt The JWT from which to extract the user ID.
   * @return The user ID extracted from the JWT.
   */
  long getUserIdFromJwtLong(Jwt jwt);

  /**
   * Checks if the user IDs extracted from a JWT and a request payload match.
   *
   * @param userIdFromToken The user ID extracted from the JWT.
   * @param userIdFromRequestPayload The user ID from the request payload.
   * @return True if the user IDs match, false otherwise.
   */
  boolean areUserIdsMatching(
    long userIdFromToken,
    long userIdFromRequestPayload
  );
}
