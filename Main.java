import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("ЧАСТЬ 1: Обычные массивы");
        
        System.out.println("Введите 6 элементов для первого массива:");
        double[] array1 = readArray(scanner, 6);
        
        System.out.println("Введите 5 элементов для второго массива:");
        double[] array2 = readArray(scanner, 5);

        double[] squaredArray1 = squareArray(array1);
        double[] squaredArray2 = squareArray(array2);

        System.out.print("Первый массив в квадрате: ");
        printArray(squaredArray1);
        System.out.println("Минимальный элемент: " + findMin(squaredArray1));

        System.out.print("Второй массив в квадрате: ");
        printArray(squaredArray2);
        System.out.println("Минимальный элемент: " + findMin(squaredArray2));
    }

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
}