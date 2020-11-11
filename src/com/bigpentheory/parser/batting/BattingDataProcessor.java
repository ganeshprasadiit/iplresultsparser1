package com.bigpentheory.parser.batting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BattingDataProcessor {
    public static String csvFileLocation = "D:\\DataTheory\\odiBatting1605051324499.csv";

    public static void main(String[] args) throws Exception {
        BattingDataProcessor processor = new BattingDataProcessor();
        for (int i = 1971; i <=2020; i++) {
            processor.processData(csvFileLocation, i);
        }

    }

    public Map<String, CricinfoBattingRowData> processData(String csvFileLocation, Integer year) throws Exception {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<CricinfoBattingRowData> dataList = new ArrayList<>();

        br = new BufferedReader(new FileReader(csvFileLocation));
        while ((line = br.readLine()) != null) {
            dataList.add(battingRowMapper(line));
        }
        System.out.println("Number of total rows : " + dataList.size());
        Map<String, Integer> yearOnYearRunsMap = new HashMap<>();
        System.out.println("Starting processing batting data");
        for (CricinfoBattingRowData battingRowData : dataList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(battingRowData.date);
            if (calendar.get(Calendar.YEAR) == year) {
                if (yearOnYearRunsMap.get(battingRowData.playerName) == null) {
                    yearOnYearRunsMap.put(battingRowData.playerName, battingRowData.runs);
                } else {
                    yearOnYearRunsMap.put(battingRowData.playerName, yearOnYearRunsMap.get(battingRowData.playerName) + battingRowData.runs);
                }
            }
        }

        System.out.println("Done processing here is the summary : " + yearOnYearRunsMap.size());

        for (Map.Entry entry : yearOnYearRunsMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        return null;
    }

    public CricinfoBattingRowData battingRowMapper(String line) throws ParseException {
        String cvsSplitBy = ",";

        if (line != null) {
            CricinfoBattingRowData data = new CricinfoBattingRowData();
            String[] dataArray = line.split(cvsSplitBy);
            data.playerName = dataArray[0];
            data.playerCountry = dataArray[1];
            data.runs = dataArray[2].equals("null") ? null : Integer.parseInt(dataArray[2]);
            data.isNotOut = Boolean.parseBoolean(dataArray[3]);
            data.ballsFaced = dataArray[4].equals("null") ? null : Integer.parseInt(dataArray[4]);
            data.minutesBatted = dataArray[5].equals("null") ? null : Integer.parseInt(dataArray[5]);
            data.fours = dataArray[6].equals("null") ? null : Integer.parseInt(dataArray[6]);
            data.sixes = dataArray[7].equals("null") ? null : Integer.parseInt(dataArray[7]);
            data.strikeRate = dataArray[8].equals("null") ? null : Double.parseDouble(dataArray[8]);
            data.opposition = dataArray[9];
            data.groundName = dataArray[10].equals("null") ? null : dataArray[10];

            data.date = new SimpleDateFormat("dd/MM/yyyy").parse(dataArray[11]);

            return data;
        }
        return null;
    }
}
