package pb2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Magazin {
    private List<Produs> produse = new ArrayList<>();

    public void citesteProduseDinFisier(String numeFisier) {
        try (BufferedReader br = new BufferedReader(new FileReader(numeFisier))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] date = linie.split("\\s+");
                String denumire = date[0].trim();
                double pret = Double.parseDouble(date[1].trim());
                int cantitate = Integer.parseInt(date[2].trim());
                LocalDate dataExpirarii = LocalDate.parse(date[3].trim());
                produse.add(new Produs(denumire, pret, cantitate, dataExpirarii));
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fisierului: " + e.getMessage());
        }
    }

    public void afiseazaProduse() {
        produse.forEach(System.out::println);
    }

    public void afiseazaProduseExpirate() {
        LocalDate today = LocalDate.now();
        List<Produs> produseExpirate = produse.stream()
                .filter(produs -> produs.getDataExpirarii().isBefore(today))
                .collect(Collectors.toList());
        produseExpirate.forEach(System.out::println);
    }

    public void vindeProdus(String denumireProdus, int cantitateVanduta) {
        for (Produs produs : produse) {
            if (produs.getDenumire().equalsIgnoreCase(denumireProdus)) {
                produs.vinde(cantitateVanduta);
                if (produs.getCantitate() == 0) {
                    produse.remove(produs);
                }
                return;
            }
        }
        System.out.println("Produsul nu a fost gasit.");
    }

    public void afiseazaProduseCuPretMinim() {
        double pretMinim = produse.stream()
                .mapToDouble(Produs::getPret)
                .min()
                .orElse(Double.MAX_VALUE);

        List<Produs> produseCuPretMinim = produse.stream()
                .filter(produs -> produs.getPret() == pretMinim)
                .collect(Collectors.toList());
        produseCuPretMinim.forEach(System.out::println);
    }

    public void salveazaProduseCuCantitateSub(int limita, String numeFisier) {
        List<Produs> produseSubLimita = produse.stream()
                .filter(produs -> produs.getCantitate() < limita)
                .collect(Collectors.toList());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(numeFisier))) {
            for (Produs produs : produseSubLimita) {
                bw.write(produs.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la salvarea fisierului: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Magazin magazin = new Magazin();
        magazin.citesteProduseDinFisier("D:\\lucru_java_intellij\\lab3pjpb2\\src\\pb2\\1");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMeniu:");
            System.out.println("1. Afiseaza toate produsele");
            System.out.println("2. Afiseaza produsele expirate");
            System.out.println("3. Vinde un produs");
            System.out.println("4. Afiseaza produsele cu pretul minim");
            System.out.println("5. Salveaza produsele cu cantitate sub un anumit numar");
            System.out.println("6. Iesi");
            System.out.print("Alege o optiune: ");
            int optiune = scanner.nextInt();

            switch (optiune) {
                case 1:
                    magazin.afiseazaProduse();
                    break;
                case 2:
                    magazin.afiseazaProduseExpirate();
                    break;
                case 3:
                    System.out.print("Introdu denumirea produsului: ");
                    String denumireProdus = scanner.next();
                    System.out.print("Introdu cantitatea vanduta: ");
                    int cantitateVanduta = scanner.nextInt();
                    magazin.vindeProdus(denumireProdus, cantitateVanduta);
                    break;
                case 4:
                    magazin.afiseazaProduseCuPretMinim();
                    break;
                case 5:
                    System.out.print("Introdu limita cantitatii: ");
                    int limita = scanner.nextInt();
                    magazin.salveazaProduseCuCantitateSub(limita, "produse_sub_limita.txt");
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Optiune invalida! Te rog sa incerci din nou.");
            }
        }
        scanner.close();
        System.out.println("Incasari totale: " + Produs.incasari);
    }
}
