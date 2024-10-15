package pb1;

public class Main {
    public static void main(String[] args) {

        Parabola p=new Parabola(4,-4,2);
        Parabola p1=new Parabola(-5,9,-3);
        p.varf_parabola();
        System.out.println(p.ToString());
        p1.varf_parabola();
        p.mij(p1);
        Parabola.mij2(p,p1);
        p.lungimea(p1);
        Parabola.lungimea2(p,p1);

    }
}