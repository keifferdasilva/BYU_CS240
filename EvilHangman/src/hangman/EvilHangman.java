package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) {
        File dict = new File(args[0]);
        int wordLength = Integer.parseInt(args[1]);
        int numGuesses = Integer.parseInt(args[2]);
        EvilHangmanGame game = new EvilHangmanGame();
        try {
            game.startGame(dict, wordLength);
        }
        catch(IOException ex){
            System.out.println("There was a problem trying to read the file.");
            ex.printStackTrace();
        }
        catch(EmptyDictionaryException ex){
            System.out.println("Dictionary is empty or does not contain any words of the specified length.");
            System.exit(1);
            ex.printStackTrace();
        }

        game.setNumGuesses(numGuesses);

        while(game.getNumGuesses() != 0){
            try{
                System.out.print("Used letters: ");
                if(game.getGuessedLetters() != null){
                    for(char letter : game.getGuessedLetters()){
                        System.out.print(letter + " ");
                    }
                    System.out.println();
                }
                System.out.printf("You have %d guesses left\n", game.getNumGuesses());
                System.out.print("Word: ");
                System.out.print(game.getCurrentWord());
                System.out.print("\nEnter guess: ");
                String stringGuess = new Scanner(System.in).nextLine();
                while(stringGuess.isEmpty()){
                    System.out.print("Enter guess: ");
                    stringGuess = new Scanner(System.in).nextLine();
                }
                char guess = stringGuess.charAt(0);
                while(!Character.isAlphabetic(guess)){
                    System.out.print("Please enter a letter: ");
                    stringGuess = new Scanner(System.in).nextLine();
                    while(stringGuess.isEmpty()){
                        System.out.print("Guess is empty. Please enter a letter: ");
                        stringGuess = new Scanner(System.in).nextLine();
                    }
                    guess = stringGuess.charAt(0);
                }
                game.makeGuess(guess);
                if(!game.getCurrentWord().contains("-")){
                    System.out.println("You win! You guessed the word: " + game.getCurrentWord());
                    System.exit(0);
                }
            }
            catch(GuessAlreadyMadeException gex){
                System.out.println("You have already tried that letter.");
            }
        }

        System.out.print("You ran out of guesses. The word was ");
        System.out.print(game.losingWord());



    }

}
