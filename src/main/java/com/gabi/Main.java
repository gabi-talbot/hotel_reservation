package com.gabi;

import com.gabi.api.AdminResource;
import com.gabi.api.HotelResource;
import com.gabi.menus.AdminMenu;
import com.gabi.menus.MainMenu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Get/instantiate dependencies
        MainMenu mainMenu = new MainMenu();
        AdminMenu adminMenu = new AdminMenu();
        AdminResource adminResource = AdminResource.getInstance();
        HotelResource hotelResource = HotelResource.getInstance();



        Scanner sc = new Scanner(System.in);
        int input = 0;

        do {
            mainMenu.display();
            try {
                input = sc.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Input must be a valid number");
                break;
            }

            if (input >= 6) {
                System.out.println("Input must be a valid number");
            }

            switch (input) {
                case 1: mainMenu.findAndReserve(sc);
            }
        }while (input != 5);

    }
}