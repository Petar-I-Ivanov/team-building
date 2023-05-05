package com.github.teambuilding.encoder;

public interface IdEncoder {

  String encode(Long id);

  Long decode(String encodedId);
}
