package com.productservice.examples;

public interface BookRequestJsonExample {
    String VALID_BOOK_1 = """
            {
              "isbn": "9780316769488",
              "title": "The Catcher in the Rye",
              "description": "The story of Holden Caulfield, a teenage boy who has been expelled from prep school and is wandering around New York City.",
              "publishDate": "1951-07-16",
              "pageCount": 224,
              "languageCode": "en",
              "authors": [
                {
                  "name": "J.D.",
                  "description": "Salinger"
                }
              ],
              "categories": [
                "FICTION"
              ],
              "publisher": {
                "publisherName": "Little, Brown and Company",
                "description": "Boston"
              }
            }
                        """;

}
