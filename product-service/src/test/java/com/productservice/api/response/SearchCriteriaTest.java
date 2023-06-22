package com.productservice.api.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchCriteriaTest {

    private SearchCriteria searchCriteria;

    @BeforeEach
    public void setUp() {
        searchCriteria = new SearchCriteria();
    }

    @Test
    public void testGetQueryPage_DefaultValues() {
        //given
        //when
        searchCriteria.getQueryPage();

        //then
        assertEquals(0, searchCriteria.getPageNo());
        assertEquals(10, searchCriteria.getPageSize());
        assertEquals("", searchCriteria.getSearch());
        assertEquals(6, searchCriteria.getSearchKeys().size());
        assertTrue(searchCriteria.getSearchKeys().containsAll(
                Arrays.asList("isbn", "title", "authors.authorName", "description", "categories", "publisher.publisherName")));

    }

    @Test
    public void testGetQueryPage_CustomValues() {
        //given
        searchCriteria = SearchCriteria.builder()
                .search("test")
                .pageNo(2)
                .pageSize(20)
                .searchKeys(new HashSet<>(Arrays.asList("title", "authors.authorName")))
                .build();

        //when
        searchCriteria.getQueryPage();

        //then
        assertEquals(2, searchCriteria.getPageNo());
        assertEquals(20, searchCriteria.getPageSize());
        assertEquals("test", searchCriteria.getSearch());
        assertEquals(2, searchCriteria.getSearchKeys().size());
        assertTrue(searchCriteria.getSearchKeys().containsAll(
                Arrays.asList("title", "authors.authorName")));
    }
}

