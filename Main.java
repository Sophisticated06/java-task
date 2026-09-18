import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // --- ЧАСТЬ 1 ---
        System.out.println("ЧАСТЬ 1: Обычные массивы");
        
        System.out.println("Введите 6 элементов для первого массива:");
        double[] array1 = readArray(scanner, 6);
        
        System.out.println("Введите 5 элементов для второго массива:");
        double[] array2 = readArray(scanner, 5);

        double[] squaredArray1 = squareArray(array1);
        double[] squaredArray2 = squareArray(array2);

        //Действия с массивами по заданию
        System.out.print("Первый массив в квадрате: ");
        printArray(squaredArray1);
        System.out.println("Минимальный элемент: " + findMin(squaredArray1));

        System.out.print("Второй массив в квадрате: ");
        printArray(squaredArray2);
        System.out.println("Минимальный элемент: " + findMin(squaredArray2));

        // --- ЧАСТЬ 2 ---
        System.out.println("\nЧАСТЬ 2: Динамические массивы");

        System.out.println("Введите 6 элементов для ArrayList:");
        List<Double> list1 = readList(scanner, 6, new ArrayList<>());

        System.out.println("Введите 5 элементов для LinkedList:");
        List<Double> list2 = readList(scanner, 5, new LinkedList<>());

        List<Double> squaredList1 = squareList(list1, new ArrayList<>());
        List<Double> squaredList2 = squareList(list2, new LinkedList<>());

        System.out.print("ArrayList в квадрате: ");
        printList(squaredList1);
        System.out.println("Минимальный элемент: " + findMinList(squaredList1));

        System.out.print("LinkedList в квадрате: ");
        printList(squaredList2);
        System.out.println("Минимальный элемент: " + findMinList(squaredList2));
    }

    // Методы для обычных массивов
    public static double[] readArray(Scanner scanner, int size) {
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = scanner.nextDouble();
        }
        return arr;
    }

    public static void printArray(double[] arr) {
        for (double num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static double[] squareArray(double[] arr) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] * arr[i];
        }
        return result;
    }

    public static double findMin(double[] arr) {
        double min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    // Методы для списков List
    public static List<Double> readList(Scanner scanner, int size, List<Double> list) {
        for (int i = 0; i < size; i++) {
            list.add(scanner.nextDouble());
        }
        return list;
    }

    public static void printList(List<Double> list) {
        for (double num : list) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static List<Double> squareList(List<Double> original, List<Double> result) {
        for (double num : original) {
            result.add(num * num);
        }
        return result;
    }

    public static double findMinList(List<Double> list) {
        double min = list.get(0);
        for (double num : list) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }
}