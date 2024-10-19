package com.csse.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UniqueIdGenerator {
    public String generateUniqueId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
