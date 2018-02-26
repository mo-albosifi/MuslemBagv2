package ly.developer.mohammedalosifi1990.muslembagv2.data.enity;

/**
 * Created by pc on 26/02/18.
 */

public class PrayEntity {
    private int id;

    private String prayEnglishName;
    private String prayArbicName;
    private int prayHour;
    private int prayMints;
    private String amPm;
    private boolean soundState;

    public PrayEntity(String prayEnglishName, String prayArbicName, int prayHour, int prayMints, String amPm, boolean soundState) {
        this.prayEnglishName = prayEnglishName;
        this.prayArbicName = prayArbicName;
        this.prayHour = prayHour;
        this.prayMints = prayMints;
        this.amPm = amPm;
        this.soundState = soundState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrayEnglishName() {
        return prayEnglishName;
    }

    public void setPrayEnglishName(String prayEnglishName) {
        this.prayEnglishName = prayEnglishName;
    }

    public String getPrayArbicName() {
        return prayArbicName;
    }

    public void setPrayArbicName(String prayArbicName) {
        this.prayArbicName = prayArbicName;
    }

    public int getPrayHour() {
        return prayHour;
    }

    public void setPrayHour(int prayHour) {
        this.prayHour = prayHour;
    }

    public int getPrayMints() {
        return prayMints;
    }

    public void setPrayMints(int prayMints) {
        this.prayMints = prayMints;
    }

    public String getAmPm() {
        return amPm;
    }

    public void setAmPm(String amPm) {
        this.amPm = amPm;
    }

    public boolean isSoundState() {
        return soundState;
    }

    public void setSoundState(boolean soundState) {
        this.soundState = soundState;
    }
}


