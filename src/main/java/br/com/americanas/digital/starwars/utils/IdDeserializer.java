package br.com.americanas.digital.starwars.utils;

import java.io.IOException;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class IdDeserializer extends StdDeserializer<Long> {

    private final Pattern pattern;

    public IdDeserializer() {
        this(null);
    }

    public IdDeserializer(Class<?> vc) {
        super(vc);
        this.pattern = Pattern.compile("[\\d+$]");
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        var match = pattern.matcher(p.getText());
        StringBuilder result = new StringBuilder();
        while (match.find()) {
            result.append(match.group(0));
        }

        return Long.valueOf(result.toString());

    }

}
