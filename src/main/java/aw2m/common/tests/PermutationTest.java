package aw2m.common.tests;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alejandro Téllez G. <java.util.fck@hotmail.com>
 * @date 16/10/2013 - 01:35:07 AM
 */
public class PermutationTest {

    /**
     *
     * @param arrayToPermutate An array of integers to permutate
     * @return
     */
    public static LinkedList<Byte[]> permutation(Byte[] arrayToPermutate) {
        //Create a list to store the permutations of the array
        LinkedList<Byte[]> permutations = new LinkedList<Byte[]>();

        //Call permutation method
        //Sending the original array, as well as its length
        //And the list where the permutations should be stored
        permutation(arrayToPermutate, arrayToPermutate.length, permutations);

        return permutations;
    }// permutation(Integer[])

    /**
     *
     * @param list
     * @param n
     * @param factorials
     */
    private static void permutation(Byte[] list, int n, List<Byte[]> factorials) {
        //If there is only one element, that same element is the only permutation
        /*
         if (1 == n) {
         factorials.add(Arrays.copyOf(list, list.length));
         }*/
        if (1 == n) {
            for (int i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }
        else {
            for (int i = 0; i < n; i++) {
                permutation(list, n - 1, factorials);
                if (0 == n % 2) {
                    swap(list, 0, n - 1);
                }
                else {
                    swap(list, i, n - 1);
                }
            }// end for
        }// end if
    }

    private static void swap(Byte[] list, int x, int y) {
        Byte t = list[x];
        list[x] = list[y];
        list[y] = t;
    }

    /**
     * DO NOT RUN THIS METHOD: OUT OF MEMORY EXCEPTION
     *
     * @param args
     */
    public static void main(String args[]) {
        //Integer[] array = new Integer[2];
        //Integer[] array = {2, 3, 1};
        Byte[] array = {50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        LinkedList<Byte[]> listOfPermutations = PermutationTest.permutation(array);

        for (Byte[] perm : listOfPermutations) {
            System.out.print("{");
            for (int i : perm) {
                System.out.print(i + ",");
            }
            System.out.print("} \n");
        }

    }
}
