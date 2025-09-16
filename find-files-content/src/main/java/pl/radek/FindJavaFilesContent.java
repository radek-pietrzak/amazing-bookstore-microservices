package pl.radek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FindJavaFilesContent {

    public static void main(String[] args) {
//        Path startPath = Paths.get(".");
        Path startPath = Paths.get("./product-service");
        String outputFileNamePrefix = "find-files-content/file-content/";
        saveSeparated(startPath, outputFileNamePrefix);
        saveAllPaths(startPath, outputFileNamePrefix);

    }

    private static void saveAllPaths(Path startPath, String outputFileNamePrefix) {
        String outputFileName = outputFileNamePrefix + "all_paths.txt";
        saveFilePaths(startPath, outputFileName);
    }

    private static void saveSeparated(Path startPath, String outputFileNamePrefix) {

        String javaEndsWith = ".java";
        String outputFileNameJava = outputFileNamePrefix + "java_files_content.txt";

        String propertiesEndsWith = ".properties";
        String outputFileNameProperties = outputFileNamePrefix + "properties_files_content.txt";

        String yamlEndsWith = ".yaml";
        String outputFileNameYaml = outputFileNamePrefix + "yaml_files_content.txt";

        String sqlEndsWith = ".sql";
        String outputFileNameSql = outputFileNamePrefix + "sql_files_content.txt";

        String pomXml = "pom.xml";
        String outputFileNamePom = outputFileNamePrefix + "pom_files_content.txt";

        saveFileContent(startPath, javaEndsWith, outputFileNameJava);
        saveFileContent(startPath, propertiesEndsWith, outputFileNameProperties);
        saveFileContent(startPath, yamlEndsWith, outputFileNameYaml);
        saveFileContent(startPath, sqlEndsWith, outputFileNameSql);
        saveFileContent(startPath, pomXml, outputFileNamePom);
    }

    private static void saveFilePaths(Path startPath, String outputFileName) {
        try (Stream<Path> stream = Files.walk(startPath)) {
            List<Path> filesPaths = stream
                    .filter(Files::isRegularFile)
                    .toList();

            StringBuilder allContent = new StringBuilder();

            for (Path path : filesPaths) {
                allContent.append(path).append("\n");
            }

            Path outputFile = Paths.get(outputFileName);
            Path parentPath = outputFile.getParent();
            if (Files.notExists(parentPath)) {
                Files.createDirectories(parentPath);
            }

            Files.writeString(outputFile, allContent.toString());
            System.out.println("Przetworzono " + filesPaths.size() + " plików.");

        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas operacji na plikach: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private static void saveFileContent(Path startPath, String endsWith, String outputFileName) {
        try (Stream<Path> stream = Files.walk(startPath)) {
            List<Path> filesPaths = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(endsWith))
                    .toList();

            StringBuilder allContent = new StringBuilder();

            for (Path path : filesPaths) {
                allContent.append("// ===== START OF: ").append(path.toString()).append(" =====\n\n");
                System.out.println(path);
                String fileContent = Files.readString(path);
                allContent.append(fileContent);
                allContent.append("\n\n// ===== END OF: ").append(path.toString()).append(" =====\n\n");
            }

            Path outputFile = Paths.get(outputFileName);
            Path parentPath = outputFile.getParent();
            if (Files.notExists(parentPath)) {
                Files.createDirectories(parentPath);
            }

            Files.writeString(outputFile, allContent.toString());
            System.out.println("Przetworzono " + filesPaths.size() + " plików ." + endsWith);
            System.out.println("Ich połączona zawartość została zapisana do pliku: " + outputFile.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas operacji na plikach: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
