package pl.radek.productservice.config;

import pl.radek.productservice.example.BookResponseExample;
import pl.radek.productservice.example.BookResponseListExample;
import pl.radek.productservice.example.EditBookResponseExample;
import pl.radek.productservice.example.ErrorResponseExample;
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


@OpenAPIDefinition
@Configuration
public class SpringdocConfig {
    Components components = new Components();


    @Bean
    public OpenAPI baseOpenAPI() {

        Example successfullyGetBook = new Example().value(BookResponseExample.getValidBook1());
        addResponseToComponents(components, "successfullyGetBook", successfullyGetBook);

        Example notFoundExceptionGet = new Example().value(ErrorResponseExample.getNotFoundExceptionGet());
        addResponseToComponents(components, "notFoundExceptionGet", notFoundExceptionGet);

        Example successfullySavedBook = new Example().value(BookResponseExample.getValidBook1());
        addResponseToComponents(components, "successfullySavedBook", successfullySavedBook);

        Example validationExceptionSave = new Example().value(ErrorResponseExample.getValidationExceptionSave());
        addResponseToComponents(components, "validationExceptionSave", validationExceptionSave);

        Example successfullyEditedBook = new Example().value(EditBookResponseExample.getEditBookResponse(true, BookResponseExample.getValidBook1()));
        addResponseToComponents(components, "successfullyEditedBook", successfullyEditedBook);

        Example validationExceptionEdit = new Example().value(ErrorResponseExample.getValidationExceptionEdit());
        addResponseToComponents(components, "validationExceptionEdit", validationExceptionEdit);

        Example notFoundExceptionEdit = new Example().value(ErrorResponseExample.getNotFoundExceptionEdit());
        addResponseToComponents(components, "notFoundExceptionEdit", notFoundExceptionEdit);

        Example successfullyDeletedBook = new Example().value(BookResponseExample.getDeletedBook());
        addResponseToComponents(components, "successfullyDeletedBook", successfullyDeletedBook);

        Example notFoundExceptionDelete = new Example().value(ErrorResponseExample.getNotFoundExceptionDelete());
        addResponseToComponents(components, "notFoundExceptionDelete", notFoundExceptionDelete);

        Example successfullyGetBookList = new Example().value(BookResponseListExample.getBookList());
        addResponseToComponents(components, "successfullyGetBookList", successfullyGetBookList);

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
