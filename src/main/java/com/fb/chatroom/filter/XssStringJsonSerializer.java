package com.fb.chatroom.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

public class XssStringJsonSerializer extends JsonSerializer<String> {
    @Override
    public Class<String> handledType(){
        return String.class;
    }

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (s != null){
            jsonGenerator.writeString(StringEscapeUtils.escapeHtml4(s));
        }
    }
}
