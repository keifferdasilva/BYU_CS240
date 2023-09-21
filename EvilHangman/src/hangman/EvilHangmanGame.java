package hangman;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{

    private Set<String> words;
    private int wordLength;
    private SortedSet<Character> guessedLetters;

    private int numGuesses;


    private String currentWord;

    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        words = new HashSet<>();
        Scanner scan = new Scanner(dictionary);
        this.wordLength = wordLength;
        currentWord = "-".repeat(wordLength);
        if(!scan.hasNext()){
            throw new EmptyDictionaryException();
        }
        while(scan.hasNext()){
            String word;
            word = scan.next();
            word = word.toLowerCase();
            if(word.length() == wordLength){
                words.add(word);
            }
        }
        if(words.isEmpty()){
            throw new EmptyDictionaryException();
        }
        guessedLetters = new TreeSet<>();
        scan.close();
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if(guessedLetters.contains(guess)){
            throw new GuessAlreadyMadeException();
        }
        guessedLetters.add(guess);
        int biggestSetLength = 0;
        Map<String, Set<String>> wordSets = partitionWords(guess);
        String biggestKey = "";
        for(String key: wordSets.keySet()){
            if(wordSets.get(key).size() > biggestSetLength){
                biggestSetLength = wordSets.get(key).size();
                biggestKey = key;
            }
            else if(wordSets.get(key).size() == biggestSetLength){
                String leastLetters = findLeastLetters(key, biggestKey, guess);
                if(leastLetters.equals("neither")){
                    biggestKey = findRightMost(key, biggestKey, guess);
                }
                else{
                    biggestKey = leastLetters;
                }
            }
        }
        words = wordSets.get(biggestKey);
        if(!biggestKey.contains("" + guess)){
            System.out.printf("Sorry there are no %s's\n\n", guess);
            --numGuesses;
        }
        else{
            StringBuilder newWord = new StringBuilder(currentWord);
            int letterCount = 0;
            for(int i = 0; i < biggestKey.toCharArray().length; i++){
                if(biggestKey.toCharArray()[i] == guess){
                    letterCount++;
                    newWord.setCharAt(i, guess);
                }
            }
            currentWord = newWord.toString();
            System.out.printf("Yes there is %d %s's\n\n", letterCount, guess);
        }
        return wordSets.get(biggestKey);
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guessedLetters;
    }

    private Map<String, Set<String>> partitionWords(char guess){
        Map<String, Set<String>> partitions = new HashMap<>();
        for(String word : words){
            Set<Integer> indexes = new HashSet<>();
            for(int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == guess) {
                    indexes.add(i);
                }
            }
            String temp = "-";
            temp = temp.repeat(wordLength);
            StringBuilder keyBuilder = new StringBuilder(temp);
            for(int i = 0; i < wordLength; i++){
                if(indexes.contains(i)){
                    keyBuilder.setCharAt(i, guess);
                }
            }
            String key = keyBuilder.toString();
            if(partitions.containsKey(key)){
                partitions.get(key).add(word);
            }
            else{
                Set<String> value = new HashSet<>();
                value.add(word);
                partitions.put(key, value);
            }
        }
        return partitions;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    private String findLeastLetters(String keyOne, String keyTwo, char guess) {
        int keyOneCount = 0;
        int keyTwoCount = 0;
        for (int i = 0; i < keyOne.length(); i++) {
            if (keyOne.toCharArray()[i] == guess) {
                keyOneCount++;
            }
        }

        for (int i = 0; i < keyTwo.length(); i++) {
            if (keyTwo.toCharArray()[i] == guess) {
                keyTwoCount++;
            }
        }
        if (keyOneCount < keyTwoCount) {
            return keyOne;
        } else if (keyTwoCount < keyOneCount) {
            return keyTwo;
        } else {
            return "neither";
        }
    }

    private String findRightMost(String keyOne, String keyTwo, char guess){
        for(int i = wordLength - 1; i > 0; i--){
            if(keyOne.charAt(i) == guess && !(keyTwo.charAt(i) == guess)){
                return keyOne;
            }
            else if(!(keyOne.charAt(i) == guess) && keyTwo.charAt(i) == guess){
                return keyTwo;
            }
        }
        return "";
    }

    public String losingWord(){
        Iterator<String> wordsIter = words.iterator();
        return wordsIter.next();
    }

    public int getNumGuesses(){
        return numGuesses;
    }

    public void setNumGuesses(int num){
        numGuesses = num;
    }
}
