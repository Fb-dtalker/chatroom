package com.fb.chatroom.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonUtil {
    public static Map readValueToMap(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        Map valueMap = null;
        try {
            valueMap = mapper.readValue(jsonString,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valueMap;
    }

    public static String writeMapToJson(Map mapObject){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(mapObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
