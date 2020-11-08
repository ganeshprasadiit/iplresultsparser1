import java.util.Date;

public class CricinfoBattingRowData {
    String playerName;
    String playerCountry;
    Integer runs;
    Boolean isNotOut;
    Integer ballsFaced;
    Integer minutesBatted;
    Integer fours;
    Integer sixes;
    Double strikeRate;
    String opposition;
    String groundName;
    Date date;

    @Override
    public String toString() {
        return playerName + "," + playerCountry + "," +
                runs +
                "," + isNotOut +
                "," + ballsFaced +
                "," + minutesBatted +
                "," + fours +
                "," + sixes +
                "," + strikeRate +
                "," + opposition + '\'' +
                "," + groundName + '\'' +
                "," + date;
    }

    public String toCsvString() {
        return playerName + ","
                + playerCountry + ","
                + runs + ","
                + isNotOut + ","
                + ballsFaced + ","
                + minutesBatted + ","
                + fours +  ","
                + sixes + ","
                + strikeRate + ","
                + opposition + ","
                + groundName + ","
                + date;
    }

    public String getCsvHeader() {
        return "Player Name,Country,Runs,IsNotOut,Balls,Minutes,Fours,Sixes,Strike Rate,Opposition,Ground,Date";
    }
}
