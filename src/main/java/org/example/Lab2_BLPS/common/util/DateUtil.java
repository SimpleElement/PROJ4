package org.example.Lab2_BLPS.common.util;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class DateUtil {
    public Long currentUTCSeconds() {
        return OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond();
    }
}
