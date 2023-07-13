package com.neil.pay.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.Reader;

/**
 * @author nihao
 * @date 2023/7/12
 */
public class GsonParser {

    private static final JsonParser JSON_PARSER = new JsonParser();
    public static final Gson GSON = new GsonBuilder().create();


    public static JsonObject parse(String json) {
        return JSON_PARSER.parse(json).getAsJsonObject();
    }

    public static JsonObject parse(Reader json) {
        return JSON_PARSER.parse(json).getAsJsonObject();
    }

    public static JsonObject parse(JsonReader json) {
        return JSON_PARSER.parse(json).getAsJsonObject();
    }
}
