package hu.petrik.aukcioprojekt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Festmeny> festmenyek = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        festmenyekHozzaadasa();
        try {
            festmenyekFelveteleKonzolrol();
        } catch (NumberFormatException e) {
            System.out.println("A darabszám csak természetes szám lehet.");
        }
        String fajlNev = "festmenyek.csv";
        try {
            festmenyekFelveteleSzovegesAllomanybol(fajlNev);
        } catch (FileNotFoundException e) {
            System.out.println("Hiba! nem található az alábbi fájl: " + fajlNev);
        } catch (IOException e) {
            System.out.println("Ismeretlen hiba történt a forrásfájl olvasása során");
            System.out.println(e.getMessage());
        }
        veletlenszeruLicit();
        try {
            konzolosLicitalas();
        } catch (NumberFormatException e) {
            System.out.println("Nem sorszámot adott meg, a licitálás leáll");
        }

        for (Festmeny festmeny : festmenyek) {
            System.out.println(festmeny);
        }
    }

    private static void konzolosLicitalas() {
        System.out.print("Kérem adja meg a festmény sorszámát (kilépéshet 0-t adjon meg): ");
        int sorszam = -1;
        while (sorszam != 0) {
            sorszam = Integer.parseInt(sc.nextLine());
            if (sorszam < 0) {
                throw new NumberFormatException();
            }
            if (sorszam > festmenyek.size()) {
                System.out.println("A listában nincs ennyi elem, a lista elemszáma: " + festmenyek.size());
            } else if (sorszam == 0) {
                System.out.println("A licitálás leáll");
            } else {
                Festmeny festmeny = festmenyek.get(sorszam - 1);
                if (festmeny.isElkelt()){
                    System.out.println("A festmény már elkelt, nem licitálhat rá");
                } else {
                    System.out.print("Kérem adja meg, hogy milyen mértékkel szeretne licitálni (10-100%): ");
                    String mertek = sc.nextLine();
                    if (mertek.isEmpty()){
                        festmeny.licit();
                    } else {
                        festmeny.licit(Integer.parseInt(mertek));
                    }
                }
            }
        }
        for (Festmeny festmeny: festmenyek) {
            if (festmeny.getLicitekSzama() > 0) {
                festmeny.setElkelt(true);
            }
        }
    }

    private static void veletlenszeruLicit() {
        for (int i = 0; i < 20; i++) {
            int index = (int) (Math.random() * festmenyek.size());
            festmenyek.get(index).licit();
        }
    }

    private static void festmenyekFelveteleSzovegesAllomanybol(String fajlNev) throws IOException {
        FileReader fr = new FileReader(fajlNev);
        BufferedReader br = new BufferedReader(fr);
        String sor = br.readLine();
        while (sor != null && !sor.isEmpty()) {
            String[] adatok = sor.split(";");
            Festmeny festmeny = new Festmeny(adatok[1], adatok[0], adatok[2]);
            festmenyek.add(festmeny);
            sor = br.readLine();
        }
        br.close();
        fr.close();
    }

    private static void festmenyekFelveteleKonzolrol() {
        System.out.print("Kérem adja meg, hogy hány festményt szeretne felvenni: ");
        int db = Integer.parseInt(sc.nextLine());
        if (db < 0) {
            throw new NumberFormatException();
        }
        for (int i = 0; i < db; i++) {
            System.out.printf("Kérem adja meg a(z) %d. festmény címét:\n", (i + 1));
            String cim = sc.nextLine();
            System.out.printf("Kérem adja meg a(z) %d. festmény festőjét:\n", (i + 1));
            String festo = sc.nextLine();
            System.out.printf("Kérem adja meg a(z) %d. festmény stílusát:\n", (i + 1));
            String stilus = sc.nextLine();
            Festmeny festmeny = new Festmeny(cim, festo, stilus);
            festmenyek.add(festmeny);
        }
    }

    private static void festmenyekHozzaadasa() {
        Festmeny festmeny = new Festmeny("Új festmény", "Gipsz Jakab", "futurizmus");
        Festmeny festmeny2 = new Festmeny("Másik festmény", "Gipsz Jakab", "futurizmus");
        festmenyek.add(festmeny);
        festmenyek.add(festmeny2);
    }
}
