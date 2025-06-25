package com.app.ecom.common.response;

import java.time.Instant;
import java.util.Map;

public record ApiResponse(Instant timestamp,
                          int status,
                          String message,
                          Map<String, ?> data) {
}
