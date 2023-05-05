package com.github.teambuilding.encoder;

import java.math.BigInteger;
import java.util.Base64;
import javax.inject.Singleton;

@Singleton
public class Base64IdEncoder implements IdEncoder {

  private final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
  private final Base64.Decoder decoder = Base64.getUrlDecoder();

  @Override
  public String encode(Long id) {
    return encoder.encodeToString(BigInteger.valueOf(id).toByteArray());
  }

  @Override
  public Long decode(String encodedId) {
    return new BigInteger(decoder.decode(encodedId)).longValueExact();
  }

}
