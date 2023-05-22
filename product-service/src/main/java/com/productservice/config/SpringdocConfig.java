package com.productservice.config;

import com.productservice.api.util.JsonFileToJsonObject;
import com.productservice.example.BookResponseExample;
import com.productservice.example.EditBookResponseExample;
import com.productservice.example.ErrorResponseExample;
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
    Components components = new Components();


    @Bean
    public OpenAPI baseOpenAPI() {

        Example successfullyGetBook = new Example().value(EditBookResponseExample.getEditBookResponse(true, BookResponseExample.VALID_BOOK_1));
        addResponseToComponents(components, "successfullyGetBook", successfullyGetBook);

        Example successfullyEditedBook = new Example().value(BookResponseExample.VALID_BOOK_1);
        addResponseToComponents(components, "successfullyEditedBook", successfullyEditedBook);


        Example validationException = new Example().value(ErrorResponseExample.VALIDATION_EXCEPTION);
        addResponseToComponents(components, "400", validationException);

        Example notFoundException = new Example().value(ErrorResponseExample.NOT_FOUND_EXCEPTION);
        addResponseToComponents(components, "404", notFoundException);

        addResponseToComponents(components, "badRequest", "Bad request", "badBookRequestApi");
        addResponseToComponents(components, "internalServerError", "Internal server error", "internalErrorServerApi");
        addResponseToComponents(components, "successfullySavedBook", "Successfully saved book", "successfullySavedBook");
        addResponseToComponents(components, "bookNotFound", "Book not found", "bookNotFound");
        addResponseToComponents(components, "successfullyDeletedBook", "Successfully deleted book", "successfullyDeletedBook");
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
            String responseKey,
            Example example
    ) {

        ApiResponse apiResponse;
        apiResponse = new ApiResponse().content(
                new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
                        new io.swagger.v3.oas.models.media.MediaType()
                                .addExamples("default", example)
                )
        );
        components.addResponses(responseKey, apiResponse);
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
