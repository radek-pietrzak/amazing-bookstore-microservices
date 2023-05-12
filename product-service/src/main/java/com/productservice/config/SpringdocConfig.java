package com.productservice.config;

import com.productservice.api.util.JsonFileToJsonObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.io.IOException;

@OpenAPIDefinition
@Configuration
public class SpringdocConfig {

    JsonFileToJsonObject jsonFileToJsonObject = new JsonFileToJsonObject();

    @Bean
    public OpenAPI baseOpenAPI() {

        Components components = new Components();
        addResponseToComponents(components, "badRequest", "Bad request", "badBookRequestApi");
        addResponseToComponents(components, "internalServerError", "Internal server error", "internalErrorServerApi");
        addResponseToComponents(components, "successfullySavedBook", "Successfully saved book", "successfullySavedBook");
        addResponseToComponents(components, "successfullyGetBook", "Successfully get book", "successfullyGetBook");
        addResponseToComponents(components, "bookNotFound", "Book not found", "bookNotFound");
        addResponseToComponents(components, "successfullyDeletedBook", "Successfully deleted book", "successfullyDeletedBook");

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("Product Service")
                        .version("1.0.0")
                        .description("Spring doc"));
    }

    private void addResponseToComponents(
            Components components,
            String jsonKey,
            String description,
            String responseKey
    ) {

        ApiResponse apiResponse;
        try {
            apiResponse = new ApiResponse().content(
                    new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                            new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                    new Example().value(jsonFileToJsonObject.readByFileName("response").get(jsonKey).toString())
                                            .description(description)))
            );
        } catch (IOException e) {
            apiResponse = null;
        }
        components.addResponses(responseKey, apiResponse);
    }
}
