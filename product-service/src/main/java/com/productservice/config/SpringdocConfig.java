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

        Example successfullyGetBook = new Example().value(BookResponseExample.VALID_BOOK_1);
        addResponseToComponents(components, "successfullyGetBook", successfullyGetBook);

        Example notFoundExceptionGet = new Example().value(ErrorResponseExample.NOT_FOUND_EXCEPTION_GET);
        addResponseToComponents(components, "notFoundExceptionGet", notFoundExceptionGet);

        Example successfullySavedBook = new Example().value(BookResponseExample.VALID_BOOK_1);
        addResponseToComponents(components, "successfullySavedBook", successfullySavedBook);

        Example validationExceptionSave = new Example().value(ErrorResponseExample.VALIDATION_EXCEPTION_SAVE);
        addResponseToComponents(components, "validationExceptionSave", validationExceptionSave);

        Example successfullyEditedBook = new Example().value(EditBookResponseExample.getEditBookResponse(true, BookResponseExample.VALID_BOOK_1));
        addResponseToComponents(components, "successfullyEditedBook", successfullyEditedBook);

        Example validationExceptionEdit = new Example().value(ErrorResponseExample.VALIDATION_EXCEPTION_EDIT);
        addResponseToComponents(components, "validationExceptionEdit", validationExceptionEdit);

        Example notFoundExceptionEdit = new Example().value(ErrorResponseExample.NOT_FOUND_EXCEPTION_EDIT);
        addResponseToComponents(components, "notFoundExceptionEdit", notFoundExceptionEdit);

        Example successfullyDeletedBook = new Example().value(BookResponseExample.DELETED_BOOK);
        addResponseToComponents(components, "successfullyDeletedBook", successfullyDeletedBook);

        Example notFoundExceptionDelete = new Example().value(ErrorResponseExample.NOT_FOUND_EXCEPTION_DELETE);
        addResponseToComponents(components, "notFoundExceptionDelete", notFoundExceptionDelete);

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

}
