package data.model;

public class MetaData {
    private final String event;
    private final String site;
    private final String date;
    private final String round;
    private final String white;
    private final String black;
    private final String result;

    public MetaData(String event, String site, String date, String round, String white, String black, String result) {
        this.event = event;
        this.site = site;
        this.date = date;
        this.round = round;
        this.white = white;
        this.black = black;
        this.result = result;
    }

    public String getEvent() {
        return event;
    }

    public String getSite() {
        return site;
    }

    public String getDate() {
        return date;
    }

    public String getRound() {
        return round;
    }

    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }

    public String getResult() {
        return result;
    }
}
