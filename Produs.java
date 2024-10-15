package pb2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Produs {
    private String denumire;
    private double pret;
    private int cantitate;
    private LocalDate dataExpirarii;
    public static double incasari = 0;

    public Produs(String denumire, double pret, int cantitate, LocalDate dataExpirarii) {
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
        this.dataExpirarii = dataExpirarii;
    }

    public String getDenumire() {
        return denumire;
    }

    public double getPret() {
        return pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public LocalDate getDataExpirarii() {
        return dataExpirarii;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return denumire + ", " + pret + ", " + cantitate + ", " + dataExpirarii;
    }

    public void vinde(int cantitateVanduta) {
        if (cantitate >= cantitateVanduta) {
            cantitate -= cantitateVanduta;
            incasari += pret * cantitateVanduta;
        } else {
            System.out.println("Stoc insuficient pentru produsul: " + denumire);
        }
    }
}

