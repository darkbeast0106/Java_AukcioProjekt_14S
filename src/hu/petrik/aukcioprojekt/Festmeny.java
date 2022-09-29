package hu.petrik.aukcioprojekt;

import java.time.LocalDateTime;

public class Festmeny {
    private static final int KEZDETEI_LICIT = 100;
    private String cim;
    private String festo;
    private String stilus;
    private int licitekSzama;
    private int legmagasabbLicit;
    private LocalDateTime legutolsoLicitIdeje;
    private boolean elkelt;


    public Festmeny(String cim, String festo, String stilus) {
        this.cim = cim;
        this.festo = festo;
        this.stilus = stilus;
        this.licitekSzama = 0;
        this.legmagasabbLicit = 0;
        this.legutolsoLicitIdeje = null;
        this.elkelt = false;
    }

    public String getCim() {
        return cim;
    }

    public String getFesto() {
        return festo;
    }

    public String getStilus() {
        return stilus;
    }

    public int getLicitekSzama() {
        return licitekSzama;
    }

    public int getLegmagasabbLicit() {
        return legmagasabbLicit;
    }

    public LocalDateTime getLegutolsoLicitIdeje() {
        return legutolsoLicitIdeje;
    }

    public boolean isElkelt() {
        return elkelt;
    }

    public void setElkelt(boolean elkelt) {
        this.elkelt = elkelt;
    }

    public void licit() {
        if (this.elkelt) {
            throw new RuntimeException("A festmény már elkelt");
        }
        if (this.licitekSzama == 0) {
            this.legmagasabbLicit = KEZDETEI_LICIT;
        } else {
            int ujLicit = (int) (this.legmagasabbLicit * 1.1);
            this.legmagasabbLicit = getVeglegesLicitMatematikaiMuveletekkel(ujLicit);
        }
        this.licitekSzama++;
        this.legutolsoLicitIdeje = LocalDateTime.now();
    }

    private int getVeglegesLicitMatematikaiMuveletekkel(int ujLicit) {
        int osztasokSzama = 0;
        while (ujLicit > 100) {
            ujLicit = ujLicit / 10;
            osztasokSzama++;
        }
        return (int) (ujLicit * Math.pow(10, osztasokSzama));
    }

    private int getVeglegesLicitStringgeAlakitassal(int ujLicit) {
        String szovegesLicit = String.valueOf(ujLicit);
        int hossz = szovegesLicit.length();
        StringBuilder builder = new StringBuilder(szovegesLicit.substring(0,2));
        for (int i = 0; i < hossz - 2; i++) {
            builder.append(0);
        }
        return Integer.parseInt(builder.toString());
    }

    public void licit(int mertek) {
        // TODO: eljárás megvalósítása
    }
}
