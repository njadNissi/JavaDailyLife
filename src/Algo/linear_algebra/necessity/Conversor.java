package Algo.linear_algebra.necessity;

import java.util.Scanner;

public class Conversor {
    static Scanner scanner = new Scanner(System.in);

    // FROM BINARY
    public static String binToOct(String binValue) {
        return decToOct(binToDec(binValue));
    }

    public static String binToDec(String binaryValue) {
        return Integer.parseInt(binaryValue, 2) + "";
    }

    public static String binToHex(String binValue) {
        return Integer.toHexString(Integer.parseInt(binValue, 2)) + "";
    }

    //FROM OCTAL
    public static String octToBin(String octValue) {
        return Integer.toBinaryString(Integer.parseInt(octValue, 8));
    }

    public static String octToDec(String octValue) {// for all n>=0 && n<=9 => 'n'-(int)'0' = n;
        return Integer.parseInt(octValue, 8) + "";
    }

    public static String octToHex(String octValue) {//oct->bin and bin->oct
        return binToHex(octToBin(octValue));
    }

    //FROM DECIMAL
    public static String decToBin(String decimalValue) {
        return Integer.toBinaryString(Integer.parseInt(decimalValue));
    }

    public static String decToOct(String decimalValue) {
        return Integer.toOctalString(Integer.parseInt(decimalValue));
    }

    public static String decToHex(String decimalValue) {
        return Integer.toHexString(Integer.parseInt(decimalValue));
    }

    //FROM HEXADECIMAL
    public static String hexToBin(String hexValue) {
        return decToBin(hexToDec(hexValue));
    }

    public static String hexToOct(String hexValue) {//hex -> dec and dec -> oct
        return decToOct(hexToDec(hexValue));
    }

    public static String hexToDec(String hexValue) {
        return octToDec(hexToOct(hexValue));
    }

    //PRACTICE MODULE
    private static void practice() {
        String ans = "";
        String[] targetSystem = {"⟼【2】", "⟼【8】", "⟼【10】", "⟼【16】"};
        while (true) {
            int depsys = 0;
            int tarsys = 0;
            while (depsys == tarsys) {
                depsys = numSystem();
                tarsys = numSystem();
            }
            switch (depsys) {//depart system
                case 0:
                    String bin = genBin();
                    System.out.print("Convert :" + bin + "⟼【2】 ⟼⟼⟼ 【 】" + targetSystem[tarsys] + "\nanswer: ");
                    break;
                case 1:
                    String oct = genOct();
                    System.out.print("Convert " + oct + "⟼【8】 ⟼⟼⟼ 【 】" + targetSystem[tarsys] + "\nanswer: ");
                    ans = scanner.next();
                    check(oct, tarsys, ans);
                    break;
                case 2:
                    String dec = genDec();
                    System.out.print("Convert " + dec + "⟼【10】 ⟼⟼⟼ 【 】" + targetSystem[tarsys] + "\nanswer: ");
                    ans = scanner.next();
                    check(dec, tarsys, ans);
                    break;
                case 3:
                    String hex = genHex();
                    System.out.print("Convert " + hex + "⟼【16】 ⟼⟼⟼ 【 】" + targetSystem[tarsys] + "\nanswer: ");
                    break;
            }
            //depart system
//            System.out.print("continue?: "); 
//            if(scanner.next().equalsIgnoreCase("n")) Menu(); 
        }
    }

    private static void check(String qst, int tarsys, String ans) {
        switch (tarsys) {
            case 0:
                if ((binToDec(qst) + "").equals(ans)) System.out.println("Right!");
                else System.out.println("Wrong!| right: ⟼【" + binToDec(qst) + "】");
                break;
            case 1:
                if ((binToOct(qst) + "").equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + binToOct(qst) + "】");
                break;
            case 2:
                if ((binToHex(qst)).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + binToHex(qst) + "】");
                break;
            case 4:
                if (decToBin(qst).equals(ans)) System.out.println("Right!");
                else System.out.println("Wrong!| right: ⟼【" + decToBin(qst) + "】");
                break;
            case 5:
                if ((decToOct(qst)).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + decToOct(qst) + "】");
                break;
            case 6:
                if ((decToHex(qst)).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + decToHex(qst) + "】");
                break;
            case 7:
                if (octToBin(qst).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + octToBin(qst) + "】");
                break;
            case 8:
                if ((octToDec(qst)).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + octToDec(qst) + "】");
                break;
            case 9:
                if ((octToHex(qst)).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + octToHex(qst) + "】");
                break;
            case 10:
                if ((hexToBin(qst)).equals(ans)) System.out.println("Right");
                else System.out.println("Wrong!| right: ⟼【" + hexToBin(qst) + "】");
                break;
            case 11:
                if (hexToOct(qst).equals(ans)) System.out.println("Right");
                System.out.println("Wrong!| right: ⟼【" + hexToOct(qst) + "】");
                break;
            case 12:
                if ((hexToDec(qst)).equals(ans)) System.out.println("Right");
                System.out.println("Wrong!| right: ⟼【" + hexToDec(qst) + "】");
        }
    }

    public static int numSystem() {//depart and target systems //execute twice a ---> b
        return (int) (Math.random() * 4);// 4 systems
    }

    public static String genDec() {
        return (int) (Math.random() * 1000) + "";
    }

    public static String genBin() {
        return Conversor.decToBin(genDec());
    }

    public static String genHex() {
        return Conversor.decToHex(genDec());
    }

    public static String genOct() {
        return Conversor.decToOct(genDec());
    }

    public static String convert(String input, String output, String val) {
        String result = "";

        if (input.equals("Binary")) {
            switch (output) {
                case "Binary":
                case "二进制":
                    result = val;
                    break;
                case "Octal":
                case "八进制":
                    result = Conversor.binToOct(val);
                    break;
                case "Decimal":
                case "十进制":
                    result = Conversor.binToDec(val);
                    break;
                case "Hexadecimal":
                case "十六进制":
                    result = Conversor.binToHex(val);
                    break;
            }
        }
        if (input.equals("Octal")) {
            switch (output) {
                case "Binary":
                    result = Conversor.octToBin(val);
                    break;
                case "Octal":
                    result = val;
                    break;
                case "Decimal":
                    result = Conversor.octToDec(val);
                    break;
                case "Hexadecimal":
                    result = Conversor.octToHex(val);
                    break;
            }
        }
        if (input.equals("Decimal")) {
            switch (output) {
                case "Binary":
                    result = Conversor.decToBin(val);
                    break;
                case "Octal":
                    result = Conversor.decToOct(val);
                    break;
                case "Decimal":
                    result = val;
                    break;
                case "Hexadecimal":
                    result = Conversor.decToHex(val);
                    break;
            }
        }
        if (input.equals("Hexadecimal")) {
            switch (output) {
                case "Binary":
                    result = Conversor.hexToBin(val);
                    break;
                case "Octal":
                    result = Conversor.hexToOct(val);
                    break;
                case "Decimal":
                    result = Conversor.hexToDec(val);
                    break;
                case "Hexadecimal":
                    result = val;
                    break;
            }
        }
        return result;
    }
}