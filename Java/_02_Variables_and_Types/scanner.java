package _02_Variables_and_Types;

import java.util.Scanner;

public class scanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scanInt = scanner.nextInt();
        String scanLine = scanner.nextLine();

        System.out.println(scanInt);
        System.out.println(scanLine);
    }
}
