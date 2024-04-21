package org.example.business.menagement;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class InputDataCache {
    private static final String FILE_PATH = "./src/main/resources/trafic_simulation.md";


    private static final Map<String, List<String>> inputData;


    static {
        try {
            inputData = readFileContent();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Map<String, List<String>> readFileContent() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH))
                .stream()
                .filter(line -> line.startsWith("[//]: #"))
                .filter(line -> !line.isBlank())
                .toList();
        return lines.stream()
                .collect(Collectors.groupingBy(
                        line -> line.split("->")[0].trim(),
                        Collectors.mapping(
                                line -> line.substring(line.indexOf("->") + 1).trim(),
                                Collectors.toList()
                        )
                ));

    }

    public static Map<String,List<String>> getInputData(){
        return inputData;
    }

}