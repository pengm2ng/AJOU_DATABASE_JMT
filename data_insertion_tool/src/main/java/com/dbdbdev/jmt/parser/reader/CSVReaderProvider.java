package com.dbdbdev.jmt.parser.reader;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.opencsv.CSVReader;

/**
 * CSV File Iteration Provider
 */
public class CSVReaderProvider {
    private static final String ROOT_PATH = "./resources/csv";
    private List<File> csvFileList;

    public CSVReaderProvider() {
        csvFileList = Arrays.asList(new File(ROOT_PATH).listFiles());
    }

    public Stream<CSVReader> getCSVFileStream() {
        return csvFileList.stream().map(t -> {
            try {
                return new FileReader(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).map(CSVReader::new);
    }

    public Stream<String[]> getCSVFileLineStream() {
        return getCSVFileStream().flatMap(t -> {
            try {
                return t.readAll().stream();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
