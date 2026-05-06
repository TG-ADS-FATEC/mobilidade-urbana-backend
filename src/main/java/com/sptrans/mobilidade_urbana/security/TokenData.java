package com.sptrans.mobilidade_urbana.security;

import java.util.UUID;

public record TokenData(UUID deviceId, Integer tokenVersion) {

}
