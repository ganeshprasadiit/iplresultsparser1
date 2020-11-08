import java.util.Date;

public class CricinfoBowlingRowData {
    String playerName;
    Double overs;
    Integer ballsPerOver;
    Integer maidenOvers;
    Integer runsConceded;
    Integer wicketsTaken;
    Double economy;
    String oppositionTeam;
    String groundName;
    Date date;

    @Override
    public String toString() {
        return playerName +
                "," + overs +
                "," + ballsPerOver +
                "," + maidenOvers +
                "," + runsConceded +
                "," + wicketsTaken +
                "," + economy +
                "," + oppositionTeam +
                "," + groundName +
                "," + date;
    }
}
