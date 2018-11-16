package client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Cypher implements Cypher {
    @Override
    public String encode(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decode(String message) {
        return new String(Base64.getDecoder().decode(message), StandardCharsets.UTF_8);
    }
}
