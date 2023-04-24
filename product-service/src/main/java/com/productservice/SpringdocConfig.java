package com.productservice;

import com.productservice.api.util.ReadJsonFileToJsonObject;
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

    ReadJsonFileToJsonObject readJsonFileToJsonObject = new ReadJsonFileToJsonObject();

    @Bean
    public OpenAPI baseOpenAPI() throws IOException {

        ApiResponse badBookRequestApi = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("badRequestResponse").toString())
                                        .description("Bad request")))

        );

        ApiResponse internalErrorServerApi = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("internalServerErrorResponse").toString())
                                        .description("Internal server error")))

        );

        ApiResponse successfullySavedBook = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
                                new Example().value(readJsonFileToJsonObject.read().get("successfullySavedBookResponse").toString())
                                        .description("Successfully saved book")))

        );

        Components components = new Components();
        components.addResponses("badBookRequestApi", badBookRequestApi);
        components.addResponses("internalErrorServerApi", internalErrorServerApi);
        components.addResponses("successfullySavedBook", successfullySavedBook);

        return new OpenAPI()
                .components(components)
                .info(new Info()
                        .title("Spring doc")
                        .version("1.0.0")
                        .description("Spring doc"));
    }
}
