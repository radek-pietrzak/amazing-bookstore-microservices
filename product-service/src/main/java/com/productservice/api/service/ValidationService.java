package com.productservice.api.service;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    public Set<String> errorMessages(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
    }
}
