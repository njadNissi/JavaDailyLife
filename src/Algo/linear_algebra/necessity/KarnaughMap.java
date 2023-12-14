/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algo.linear_algebra.necessity;

import java.util.Scanner;

/**
 * @author njad
 */
public class KarnaughMap {
    private int varNo = 0, termsNo = 0;
    private String terms[], canTerm[] = new String[16];
    ;//canonic terms
    private char Map[][];
    private String function = "";
    private String vars = "";
    private char minTerms[] = new char[16];

    public KarnaughMap(String function) {//small letters for negative vars
        this.function = function;
        System.out.println("entered Expression Y = " + function);
        termsNo = countTerms();
        System.out.println("terms = " + this.countTerms() + ", vars = " + this.countVars());
        this.setTerms();
        this.Map = new char[this.varNo][this.varNo];//square matrix
        for (int i = 0; i < 16; i++) canTerm[i] = "";
    }

    public static void inform() {
        System.out.println("Karnaugh map is used to simplifify logical functions of 2, 3 and 4 vars.");
        System.out.println("vars are chosen A,B,C,D or a,b,c,d");
        System.out.println("positive vars are in Capital and negative in Small");
        System.out.println("====================================================\n");
    }

    public static void main(String[] args) {
        KarnaughMap.inform();
        Scanner sc = new Scanner(System.in);
        KarnaughMap k;
        System.out.print("enter a valid logical function\n\t Y = ");
        k = new KarnaughMap(sc.next());
        // k.getTerms();
        System.out.println("canonical form : Y = " + k.CanonicForm());

    }

    public String CanonicForm() {
        int x = 0;
        String newterm1, newterm2, newterm3 = "", newterm4 = ""; //F = abc = newterm1=abcD //newterm2 = abcd
        for (int i = 0; i < terms.length; i++) {
            newterm1 = terms[i];
            newterm2 = terms[i];
            if (this.varNo > 1 && this.varNo <= 4) {
                if (!(terms[i].contains("A") || terms[i].contains("a"))) {
                    newterm1 += "A";
                    newterm2 += "a";
                }
                if (!(terms[i].contains("B") || terms[i].contains("b"))) {
                    newterm1 += "B";
                    newterm2 += "b";
                }
                if (this.varNo == 2) {
                    canTerm[x++] = newterm1;
                    canTerm[x++] = newterm2;
                    continue;
                }
            }
            if (this.varNo == 3 || this.varNo == 4) {
                if (!(terms[i].contains("C") || terms[i].contains("c"))) {
                    newterm3 += newterm1 + "c";
                    newterm4 += newterm3 + "C";
                    newterm1 += "C";
                    newterm2 += "c";
                }
                if (this.varNo == 3) {
                    canTerm[x++] = newterm1;
                    canTerm[x++] = newterm2;
                    canTerm[x++] = newterm3;
                    canTerm[x++] = newterm4;
                    continue;
                }
            }
            if (this.varNo == 4) {
                if (!(terms[i].contains("D") || terms[i].contains("d"))) {
                    newterm3 += newterm1 + "d";
                    newterm4 += newterm3 + "D";
                    newterm1 += "D";
                    newterm2 += "d";
                }
                if (this.varNo == 4) {
                    if (this.unique(newterm1)) this.canTerm[x++] = newterm1;
                    if (this.unique(newterm2)) this.canTerm[x++] = newterm2;
                    if (this.unique(newterm3)) this.canTerm[x++] = newterm3;
                    if (this.unique(newterm4)) this.canTerm[x++] = newterm4;
                }
            }
        }
        String Canonic = "";
        for (int i = 0; i < canTerm.length; i++)
            if (canTerm[i] != "")
                Canonic += canTerm[i] + " + ";

        return Canonic;
    }

    public void Simplify() {

    }

    private boolean unique(String term) {
        boolean unique = true;
        for (String can : canTerm)
            if (canTerm.equals(term)) unique = false;
        return unique;
    }

    private int countTerms() {
        int terms = 0;
        boolean exception = false;
        if (function.contains("++")) {
            System.out.println("binary operators are put between two terms!");
            exception = true;
        }
        if (!exception) {
            for (int i = 0; i < function.length(); i++)
                if (function.charAt(i) == '+') {
                    try {
                        if (isLetter(function.charAt(i - 1)) && isLetter(function.charAt(i + 1)))
                            terms++;// A+B
                    } catch (Exception ex) {
                        if (i == 0) System.out.println("a valid expression doesn't start with an operator!");
                        else if (i == function.length() - 1)
                            System.out.println("a valid expression doesn't end with an operator!");
                        exception = true;
                        break;
                    }
                }
            terms++;
        }
        if (exception) return -1;
        return terms;
    }

    private boolean isLetter(char c) {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }

    private int countVars() {
        boolean A = false, B = false, C = false, D = false;
        int vars = 0;
        for (int i = 0; i < function.length(); i++)
            switch (function.charAt(i)) {
                case 'A':
                case 'a':
                    A = true;
                    break;
                case 'B':
                case 'b':
                    B = true;
                    break;
                case 'C':
                case 'c':
                    C = true;
                    break;
                case 'D':
                case 'd':
                    D = true;
                    break;
                case '+':
                    vars += 0;
                    break;
                default:
                    System.out.println("number of variables of the max(4)!");
            }
        if (A) vars++;
        if (B) vars++;
        if (C) vars++;
        if (D) vars++;
        this.varNo = vars;
        return vars;
    }

    private void setTerms() {
        String t = "";
        int x = 0;
        terms = new String[this.termsNo];
        for (int i = 0; i < function.length(); i++) {
            if (function.charAt(i) == '+') {
                terms[x++] = t;
                t = "";
            } else t += function.charAt(i);
        }
        terms[x++] = t;
    }

    public void getTerms() {
        for (String t : terms)
            System.out.println(t);
    }
    //ABD+aBCd+abCd+aBcD+abcd
    //ABD+aCd+bCd+aBcD+abd
}
