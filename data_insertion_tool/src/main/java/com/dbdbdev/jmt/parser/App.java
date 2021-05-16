package com.dbdbdev.jmt.parser;

import com.dbdbdev.jmt.parser.reader.CSVReaderProvider;

public class App {
    public static void main(String[] args) {
        var csvProvider = new CSVReaderProvider();

        csvProvider.getCSVFileStream();
    }
}
