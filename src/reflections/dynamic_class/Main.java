//package reflections.dynamic_class;
//
//import java.util.Scanner;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        fun();
//
//    }
//
//    private static void fun() {
//        final Scanner sc = new Scanner(System.in);
//        MyClass c = new MyClass();
//
//        while (true) {
//            title();
//            System.out.println("--->(1) create object\n--->(2) edit object\n--->(3) view object)");
//            System.out.print("Your choice: ");
//
//            switch (sc.nextLine()) {
//                case "1" :
//                    try {
//                        System.out.print("CMD> ");
//                        String cmd = sc.nextLine();
//                        c.newObject(cmd);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("Verify you command:\n\tOBJECT_NAME (ATTRIB_NAME1:VALUE1, ATTRIB_NAME2:VALUE2)");
//                    }
//                break;
//                case "2":
//                    System.out.print("Enter object name> ");
//                    String objName = sc.nextLine().replace(" ", "");
//                    if(MyClass.objects.get(objName) == null) {
//                        System.out.println("\nNon existing object");
//                        return;
//                    }
//                    System.out.print("Enter Attribute name> ");
//                    String attName = sc.nextLine().replace(" ", "");
//                    System.out.println("--------------------------------------------------------------------------");
//                    System.out.println(attName + "currently value : " + MyClass.objects.get(objName).get(attName));
//                    System.out.println("--------------------------------------------------------------------------");
//                    System.out.print("Redefine this attribute : ATTRIB_NAME:ATTRIB_VALUE\nCMD> ");
//                    try {
//                        String cmd = sc.nextLine();
//                        cmd = cmd.replace(" ", "");
//                        int sep = cmd.indexOf(":");
//                        String name = cmd.substring(0, sep);
//                        String value = cmd.substring(sep + 1, cmd.length());
//                        MyClass.objects.get(objName).put(name, value);
//                    } catch (Exception e) {
//                        System.out.println("The entered object was never created!");
//                    }
//                break;
//                case "3":
//                    System.out.print("Enter object name> ");
//                    String objName = sc.next();
//                    try {
//                        System.out.println(MyClass.objects.get(objName));
//
//                    } catch (Exception e) {
//                        System.out.println("The entered object was never created!");
//                    }
//                break;
//                default : System.out.println("INVALID CHOICE!!!");
//            }
//        }
//    }
//
//    private static void title() {
//        cls();
//        System.out.println("##############################################################");
//        System.out.println("#     D Y N A M I C   O B J E C T   C  R E A T I O N         #");
//        System.out.println("##############################################################");
//    }
//
//    private static void cls() {
//        System.out.print("\033[H\033[2J");
//        System.out.flush();
//    }
//
//}