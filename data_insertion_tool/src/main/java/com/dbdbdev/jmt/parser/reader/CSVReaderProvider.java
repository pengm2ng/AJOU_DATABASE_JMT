package com.dbdbdev.jmt.parser.reader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
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
        File[] fileList = new File(ROOT_PATH).listFiles();
        csvFileList = new ArrayList<>();

        for (File file : fileList) {
            if (file.getName().matches("\\.csv$")) {
                csvFileList.add(file);
            }
        }
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
}
