package com.funnyboyroks.DataStructures.Trees.TwentyQuestions;

import java.io.IOException;
import java.util.Scanner;

public class QuestionsMain {

    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);

        System.out.println("Welcome to CSE 143 Game of N-Questions!");
        System.out.println();

        System.out.print("Which questions file would you like to use?\n> ");
        QuestionsGame game = new QuestionsGame(console.nextLine().trim());

        System.out.print("Let's play!");
        boolean again = true;
        while (again) {
            System.out.println();
            System.out.println();
            System.out.println("Please choose your object, and I'll start guessing.");
            System.out.println("Press Enter when you're ready to begin!");
            console.nextLine();
            game.play();
            System.out.println();
            game.save();
            again = game.confirm("Do you want to play again?");
        }
    }
}
