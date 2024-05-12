package com.leobooth.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVToDealerZipList {

    public enum CSVHeaders {
        HAS_HEADERS, NO_HEADERS, KEEP_HEADERS, DISCARD_HEADERS
    }

    public static ArrayList<DealerZip> convertCSV(String csvFilePath, String delimiter,
                                                  CSVHeaders headerInfo, CSVHeaders headerUsage) throws IOException {
        boolean keepHeaders = false;

        if (headerInfo == CSVHeaders.HAS_HEADERS) {
            if (headerUsage == CSVHeaders.KEEP_HEADERS) {
                keepHeaders = true;
            }
        } else {
            keepHeaders = false;
        }

        return convertCSV(csvFilePath, delimiter, keepHeaders);

    }

    public static ArrayList<DealerZip> convertCSV(String csvFilePath, String delimiter, boolean keepHeaders) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
        List<String[]> data = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(delimiter);
            data.add(values);
        }
        br.close();

        ArrayList<DealerZip> dealerZipList = new ArrayList<>();
        int lineCount = 0;
        for (String[] nextLine : data) {
            if(lineCount == 0 && !keepHeaders) {
                lineCount++;
                continue;
            }

            String dealership = nextLine[0];
            String zipCode = nextLine[1];
            DealerZip dealerZip = new DealerZip(dealership, zipCode);
            dealerZipList.add(dealerZip);
            lineCount++;
        }

        return dealerZipList;
    }

    public static class DealerZip {
        private final String dealership;
        private final String zipCode;

        public DealerZip(String dealership, String zipCode) {
            this.dealership = dealership;
            this.zipCode = zipCode;
        }

        public String getDealership() {
            return this.dealership;
        }

        public String getZipCode() {
            return this.zipCode;
        }
    }
}
