public class TennisMatch {
    String matchDate;
    String player1Name;
    Integer player1Sets;
    String player2Name;
    Integer player2Sets;

    @Override
    public String toString() {
        return "TennisMatch{" +
                "matchDate='" + matchDate + '\'' +
                ", player1Name='" + player1Name + '\'' +
                ", player1Sets=" + player1Sets +
                ", player2Name='" + player2Name + '\'' +
                ", player2Sets=" + player2Sets +
                '}';
    }
}
