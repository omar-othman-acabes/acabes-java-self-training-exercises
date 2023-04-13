package org.acabes.training.fundamentalsExercise;

import org.acabes.training.Exercise;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * - Create large array.
 *      Read (n) number from the user using Scanner class (should be more than 10,000)
 *      Create Array with the (n) number as the size
 *      Fill the array with random numbers using java Random class (numbers should be between 1 and 1000)
 *      [optional] Write array to text file where each file should be written on single line
 * - Calculate and Print
 *      Sum of all the array items.
 *      Average of all the array items
 *      Most frequent three numbers
 */
public class FundamentalsExercise implements Exercise {
    @Override
    public void solve() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please Enter a number bigger than 10,000: ");
            int n = scanner.nextInt();

            if (isValid(n)) {
                int[] array = new int[n];

                for (int i = 0; i < n; i++) {
                    array[i] = generateRandom();
                }

                extractToFile(array);
                System.out.println("Sum of all array items: " + sumOfArrayValues(array));
                System.out.println("Average of all array items: " + averageOfArrayValues(array));
                System.out.println("Most frequent three numbers: " + Arrays.toString(mostFreqThreeValues(array)));
                break;
            } else {
                System.out.println("invalid number!");
            }
        }

    }

    /**
     * @return A random number between 1 and 1000
     */
    private int generateRandom() {
        return (int) (Math.random() * 1000) + 1;
    }

    /**
     * A valid number must be bigger than 10,000
     *
     * @param n number entered by the user
     * @return true if the number is valid, otherwise return false
     */
    private boolean isValid(int n) {
        return n > 10000;
    }

    /**
     * @return summation of all values in the given array.
     */
    private int sumOfArrayValues(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    /**
     * @return average of array values
     */
    private double averageOfArrayValues(int[] array) {
        return (double) sumOfArrayValues(array) / array.length;
    }

    /**
     * @return array of size 3: most frequent three values in the given array.
     */
    private int[] mostFreqThreeValues(int[] array) {
        HashMap<Integer, Integer> valFreqMap = new HashMap<>();

        for (int value : array) {
            if (valFreqMap.containsKey(value)) {
                valFreqMap.put(value, valFreqMap.get(value) + 1);
            } else {
                valFreqMap.put(value, 1);
            }
        }

        int maxFreq1 = 0;
        int maxFreq2 = 0;
        int maxFreq3 = 0;
        int[] result = new int[3];

        for (Map.Entry<Integer, Integer> entry : valFreqMap.entrySet()) {
            int value = entry.getKey();
            int freq = entry.getValue();

            if (freq > maxFreq1) {
                maxFreq1 = freq;
                result[0] = value;
            } else if (freq > maxFreq2) {
                maxFreq2 = freq;
                result[1] = value;
            } else if (freq > maxFreq3) {
                maxFreq3 = freq;
                result[2] = value;
            }
        }

        return result;
    }

    /**
     * Extracts the given array to a file where each value is in a single line.
     */
    private void extractToFile(int[] array) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                Files.newOutputStream(Paths.get("fundamentalsExercise.txt")), StandardCharsets.UTF_8))) {

            for (int value : array) {
                writer.append(String.valueOf(value)).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
