package pl.radek;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FindJavaFilesContent {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Błąd: Nie podano ścieżek startowych jako argumentów programu.");
            System.err.println("Przykład użycia: ./product-service ./inventory-service");
            return;
        }

        List<Path> startPaths = Arrays.stream(args)
                .map(Paths::get)
                .toList();

        String outputFileNamePrefix = "find-files-content/file-content/";
        saveSeparated(startPaths, outputFileNamePrefix);
        saveAllPaths(startPaths, outputFileNamePrefix);
    }

    private static void saveAllPaths(List<Path> startPaths, String outputFileNamePrefix) {
        String outputFileName = outputFileNamePrefix + "all_paths.txt";
        saveFilePaths(startPaths, outputFileName);
    }

    private static void saveSeparated(List<Path> startPaths, String outputFileNamePrefix) {
        saveFileContent(startPaths, ".java", outputFileNamePrefix + "java_files_content.txt");
        saveFileContent(startPaths, ".properties", outputFileNamePrefix + "properties_files_content.txt");
        saveFileContent(startPaths, ".yaml", outputFileNamePrefix + "yaml_files_content.txt");
        saveFileContent(startPaths, ".sql", outputFileNamePrefix + "sql_files_content.txt");
        saveFileContent(startPaths, "pom.xml", outputFileNamePrefix + "pom_files_content.txt");
    }

    private static void saveFilePaths(List<Path> startPaths, String outputFileName) {
        List<Path> allFilesPaths = new ArrayList<>();
        for (Path startPath : startPaths) {
            try (Stream<Path> stream = Files.walk(startPath)) {
                List<Path> filesInPath = stream
                        .filter(Files::isRegularFile)
                        .toList();
                allFilesPaths.addAll(filesInPath);
            } catch (IOException e) {
                System.err.println("Wystąpił błąd podczas przeszukiwania ścieżki " + startPath + ": " + e.getMessage());
            }
        }

        try {
            StringBuilder allContent = new StringBuilder();
            for (Path path : allFilesPaths) {
                allContent.append(path).append("\n");
            }

            Path outputFile = Paths.get(outputFileName);
            Path parentPath = outputFile.getParent();
            if (parentPath != null && Files.notExists(parentPath)) {
                Files.createDirectories(parentPath);
            }

            Files.writeString(outputFile, allContent.toString());
            System.out.println("Zapisano ścieżki " + allFilesPaths.size() + " plików do: " + outputFile.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas zapisu do pliku: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void saveFileContent(List<Path> startPaths, String endsWith, String outputFileName) {
        List<Path> allFilesToProcess = new ArrayList<>();
        for (Path startPath : startPaths) {
            try (Stream<Path> stream = Files.walk(startPath)) {
                List<Path> filesInPath = stream
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(endsWith))
                        .toList();
                allFilesToProcess.addAll(filesInPath);
            } catch (IOException e) {
                System.err.println("Wystąpił błąd podczas przeszukiwania ścieżki " + startPath + ": " + e.getMessage());
            }
        }

        try {
            StringBuilder allContent = new StringBuilder();
            for (Path path : allFilesToProcess) {
                allContent.append("// ===== START OF: ").append(path.toString()).append(" =====\n\n");
                String fileContent = Files.readString(path);
                allContent.append(fileContent);
                allContent.append("\n\n// ===== END OF: ").append(path.toString()).append(" =====\n\n");
            }

            if (!allFilesToProcess.isEmpty()) {
                Path outputFile = Paths.get(outputFileName);
                Path parentPath = outputFile.getParent();
                if (parentPath != null && Files.notExists(parentPath)) {
                    Files.createDirectories(parentPath);
                }
                Files.writeString(outputFile, allContent.toString());
                System.out.println("Przetworzono " + allFilesToProcess.size() + " plików kończących się na '" + endsWith + "' i zapisano do: " + outputFile.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas operacji na plikach: " + e.getMessage());
            e.printStackTrace();
        }
    }
}