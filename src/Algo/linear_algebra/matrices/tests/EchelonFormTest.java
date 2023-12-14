package Algo.linear_algebra.matrices.tests;

import Algo.linear_algebra.matrices.LUDecomposition;
import Algo.linear_algebra.matrices.Matrix;

import java.util.*;

public class EchelonFormTest {

    public static void main(String[] args){
        // m20: matrix2x2-0
        double[][] m20 = {{1, 0}, {0, 1}};
        double[][] m21 = {{1, 2}, {2, 2}};
        double[][] m30= {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        double[][] m31= {{0, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        double[][] m32 = {{2, 2, 1}, {1, 7, 2}, {3, -3, 0}};
        double[][] m33 = {{4, 5, 6}, {8, 10, 12}, {12, 15, 18}};
        double[][] m34 = {{1, 2, 3}, {2, 2, 1}, {3, 4, }};
        double[][] m35 = {{4, 2, -2}, {2, 2, -2}, {-2, -3, 13}};
        double[][] m40 = {{1 , 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        double[][] m41 = {{1, 1, 1, 1}, {1, 2, -1, 0}, {2, 1, 4, 3}, {2, 3, 0, 1}};
        double[][] matrix;
        Map<String, double[][]> examples = new HashMap<>();
        examples.put("m20", m20);
        examples.put("m21", m21);
        examples.put("m30", m30);
        examples.put("m31", m31);
        examples.put("m32", m32);
        examples.put("m33", m33);
        examples.put("m34", m34);
        examples.put("m35", m35);
        examples.put("m40", m40);
        examples.put("m41", m41);

        List<Double[][]> steps = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int N;

        while(true){
            System.out.print(":::>type 'eg' to view and use example matrices | or type the Size of new (square) matrix, e.g.: 2(means 2x2), 3(means 3x3).\n:::>Your Input: ");
            String s = sc.next();
            if(!s.contains("eg")){
                N = Integer.parseInt(s);
                matrix = new double[N][N];
                System.out.println("Enter elements row-wise and press enter after each element !!!");
                for(int r=0; r<N; r++){
                    System.out.println("Row"+(r+1)+":");
                    for(int c=0; c<N; c++){
                        //System.out.println("");
                        matrix[r][c] = sc.nextDouble();
                    }
                }
            } else {
                System.out.println("_______________________________________\n:::>EXAMPLE MATRICES: ");
                examples.forEach((k, v) -> { Matrix.print(v, k); });

                System.out.print("_______________________________________\n:::>enter matrix ID: ");
                String choice = sc.next();

                matrix = examples.get(choice);
            }
            //set matrix
            Matrix.print(matrix, "ORIGINAL STATE");
            LUDecomposition.visibleProcess();
            LUDecomposition.set(matrix);
            LUDecomposition.describe();
        }
    }

}
