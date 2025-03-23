package com.gabi;

import com.gabi.api.AdminResource;
import com.gabi.api.HotelResource;
import com.gabi.menus.AdminMenu;
import com.gabi.menus.MainMenu;
import com.gabi.utilities.Filler;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Filler fill = new Filler();
        fill.seed();


        // Get/instantiate dependencies
        MainMenu mainMenu = new MainMenu();
        AdminMenu adminMenu = new AdminMenu();
        AdminResource adminResource = AdminResource.getInstance();
        HotelResource hotelResource = HotelResource.getInstance();


        // Scanner resource to read input
        Scanner sc = new Scanner(System.in);
        int input = 0;

        do {
            mainMenu.display();

            try {
                input = sc.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("Input must be a valid number");
                sc.nextLine();
                continue;
            }

            if (input >= 6 || input <= 0) {
                System.out.println("Input must be a valid number");
            }

            switch (input) {
                case 1: mainMenu.findAndReserve(sc);
                continue;
                case 2: mainMenu.seeReservations(sc);
            }
        }while (input != 5);

    }
}