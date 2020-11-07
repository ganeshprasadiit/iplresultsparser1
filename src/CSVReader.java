import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "D:\\DataTheory\\ipldataagain.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            Integer size = 0;
            List<MetaData> metaDataList = new ArrayList<>();
            MetaData metaData = new MetaData();
            int posCount = 1;
            String seasonName = "";
            Map<String, List<MetaData>> seasonsMap = new HashMap<>();
            while ((line = br.readLine()) != null) {
                size++;
                // use comma as separator
                String[] dataArray = line.split(cvsSplitBy);
                if (seasonsMap.get(dataArray[3]) == null) {
                    metaDataList = new ArrayList<>();
                    seasonsMap.put(dataArray[3], metaDataList);
                }
                    if (dataArray[6].equals("Yes")) {
                        if (dataArray[5].length() > 20 && (dataArray[5].contains("(N)") || (dataArray[5].contains("(D/N)")))) {
                            metaDataList.add(metaData);
                            metaData = new MetaData();
                            metaData.matchTitle = dataArray[5];
                            metaData.seasonName = dataArray[3];
                            posCount = 1;
                        }

                        if (posCount == 2) { metaData.team1Name = dataArray[5]; }
                        if (posCount == 3) { metaData.team1Overs = dataArray[5];}
                        if (posCount == 4) { metaData.team1Score = dataArray[5];}
                        if (posCount == 5) { metaData.team2Name = dataArray[5];}
                        if (posCount == 6) { metaData.team2Overs = dataArray[5]; }
                        if (posCount == 7) { metaData.team2Score = dataArray[5];}

                        posCount++;

                    }

                //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

            }

            System.out.println("total matches :" + metaDataList.size());
            for (Map.Entry<String, List<MetaData>> entry : seasonsMap.entrySet()) {
                if (entry.getKey().equals("2009")) {
                  for (MetaData data : entry.getValue()) {
                      System.out.println(data.matchTitle);
                  }
                }
                //System.out.println("Season name :" + entry.getKey() + ", matches : " + entry.getValue().size());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}