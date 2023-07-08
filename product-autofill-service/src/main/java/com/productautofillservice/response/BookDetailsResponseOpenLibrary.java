package com.productautofillservice.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class BookDetailsResponseOpenLibrary implements Response {
    private String bib_key;
    private String info_url;
    private String preview;
    private String preview_url;
    private Details details;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @EqualsAndHashCode
    public static class Details {
        private List<String> publishers;
        private String subtitle;
        private List<String> isbn_10;
        private List<String> series;
        private List<String> lc_classifications;
        private String key;
        private List<Author> authors;
        private List<String> publish_places;
        private List<String> contributions;
        private List<String> isbn_13;
        private String pagination;
        private List<String> source_records;
        private String title;
        private List<String> lccn;
        private Notes notes;
        private int number_of_pages;
        private List<Language> languages;
        private List<String> subjects;
        private List<String> subject_people;
        private String publish_country;
        private String publish_date;
        private String by_statement;
        private List<String> oclc_numbers;
        private List<Work> works;
        private Type type;
        private int latest_revision;
        private int revision;
        private DateTime created;
        private DateTime last_modified;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        public static class Author {
            private String key;
            private String name;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        public static class Notes {
            private String type;
            private String value;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        public static class Language {
            private String key;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        public static class Work {
            private String key;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        public static class Type {
            private String key;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @EqualsAndHashCode
        public static class DateTime {
            private String type;
            private String value;
        }
    }
}
