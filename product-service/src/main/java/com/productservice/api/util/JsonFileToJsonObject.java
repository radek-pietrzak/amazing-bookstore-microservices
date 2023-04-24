package com.productservice.api.util;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileToJsonObject {

    public JSONObject read(String fileName) throws IOException {
        String file = "product-service/src/main/resources/json_example/" + fileName + ".json";
        String content = new String(Files.readAllBytes(Paths.get(file)));
        return new JSONObject(content);
    }
}
