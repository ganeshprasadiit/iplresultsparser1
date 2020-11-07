import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TennisCSVReader {

    public static void main(String[] args) {
        TennisCSVReader reader = new TennisCSVReader();
        List<TennisMatch> matchList = reader.parseCSV();
        Collections.reverse(matchList);
        for (TennisMatch match : matchList) {
            System.out.println(match);
        }

    }
    public List<TennisMatch> parseCSV() {

        String csvFile = "C:\\Users\\ganes\\Downloads\\australianopen 2019.csv";

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";

        List<TennisMatch> matchList = new ArrayList<>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int lineNumber = 0;
            TennisMatch match = new TennisMatch();
            while ((line = br.readLine()) != null) {
                lineNumber++;
                System.out.println(line);
                String[] dataArray = line.split(cvsSplitBy);
                switch(lineNumber%5) {
                    case 1:
                        matchList.add(match);
                        match = new TennisMatch();
                        match.matchDate = dataArray[3];
                        break;
                    case 2:
                        match.player1Name = dataArray[3];
                        break;
                    case 3:
                        match.player1Sets = Integer.parseInt(dataArray[3]);
                        break;
                    case 4:
                        match.player2Sets = Integer.parseInt(dataArray[3]);
                        break;
                    case 0:
                        match.player2Name = dataArray[3];
                        break;
                    default:
                        break;

                }
           }
           matchList.add(match);

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

        return matchList;
    }

}