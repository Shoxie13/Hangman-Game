
/**********************************************
Workshop 4
Course: JAC444 - Semester 4
Last Name: Abdi
First Name: Tareq
ID: 123809196
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature - TA
Date: 24/02/2022
**********************************************/

import java.io.*;
import java.util.*;

public class HangMan {
    private Scanner input = new Scanner(System.in);

    private static List<String> str;
    private static char[] hiddenWord;
    private static String word;

    private String filename = "";
    private int missCnt = 0;

    private ArrayList<Character> wrongLetters = new ArrayList<>();
    private ArrayList<Character> guessedLetters = new ArrayList<>();

    // no arg constructor
    HangMan() {
    };

    // one arg constructor
    HangMan(String filename) {
        try {
            this.filename = filename;

            BufferedReader buff = new BufferedReader(new FileReader(filename));

            str = new ArrayList<String>(Arrays.asList(buff.readLine().split("\\s+")));
            str.get((int) (Math.random() * str.size()));

            randomize(str);

            buff.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    };

    void createWord(String word) {
        hiddenWord = new char[word.length()];

        for (int i = 0; i < word.length(); i++) {
            hiddenWord[i] = '*';
        }
    }

    void randomize(List<String> str) {
        word = str.get((int) (Math.random() * str.size()));
    }

    void gameReset() {
        randomize(str);
        createWord(word);
        wrongLetters.clear();
        guessedLetters.clear();
        missCnt = 0;
    }

    void play() {
        // needed to get the index for specific character
        int index = 0;

        // GAME RESET
        gameReset();

        while (new String(hiddenWord).indexOf('*') != -1) {
            System.out.print("Enter your letter in the word ");
            for (int i = 0; i < word.length(); i++) {
                System.out.print(hiddenWord[i]);
            }
            System.out.print(" " + word + "> ");

            char guess = input.next().charAt(0);

            if (!guessedLetters.contains(guess)) {
                for (int i = 0; i < word.length(); i++) {
                    if (guess == word.charAt(i)) {
                        index = word.indexOf(guess);
                        hiddenWord[i] = word.charAt(i);
                        guessedLetters.add(guess);
                    }
                }

                if (guess != word.charAt(index)) {
                    missCnt++;

                    if (wrongLetters.contains(guess)) {
                        System.out.println("You have already tried *" + guess + "*! Please try a new letter...");
                    } else {
                        System.out.println("The letter *" + guess + "* is NOT in the word! Please try a new letter...");
                    }

                    wrongLetters.add(guess);
                }
            } else {
                System.out.println("The letter *" + guess + "* is already in the word! Please try a new letter...");
            }

        }

        System.out.println();
        System.out.println("You won! The word is *" + word + "*");
        System.out.println("Number of misses: " + missCnt);
        System.out.print("Enter a new word to be added in the memory: ");
        String addWord = input.next();

        while (str.contains(addWord)) {
            System.out.print("Your word is already in the list! Try another word: ");
            addWord = input.next();
        }

        try (FileWriter fw = new FileWriter(filename, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            System.out.println("Your word *" + addWord + "* was successfully added!");
            out.print(" " + addWord);
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.print("Do you want to guess another word? Enter y or n: ");
        char choice = input.next().charAt(0);

        while ((!(choice == 'y' || choice == 'Y') && !(choice == 'n' || choice == 'N'))) {
            System.out.print("Wrong input! Enter only y or n: ");
            choice = input.next().charAt(0);
        }

        if (choice == 'y' || choice == 'Y') {
            play();
        } else {
            System.out.println("Thank you for playing! Goodbye...");
        }
    }
}
