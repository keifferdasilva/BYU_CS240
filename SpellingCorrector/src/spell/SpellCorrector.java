package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector{

    Trie dictionary;
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        dictionary = new Trie();
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNext()) {
            String nextWord = scanner.next();
            dictionary.add(nextWord);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        SortedSet<String> editDistanceOneWords = new TreeSet<>();
        SortedSet<String> editDistanceTwoWords = new TreeSet<>();
        inputWord = inputWord.toLowerCase();
        if(dictionary.find(inputWord) != null){
            return inputWord;
        }
        else{
            editInsertion(editDistanceOneWords, inputWord);
            editAlteration(editDistanceOneWords, inputWord);
            editDeletion(editDistanceOneWords, inputWord);
            editTransposition(editDistanceOneWords, inputWord);

            String result = null;
            int highestCount = 0;
            for(String word : editDistanceOneWords){
                if(dictionary.find(word) != null){
                    if(dictionary.find(word).getValue() > highestCount) {
                        highestCount = dictionary.find(word).getValue();
                        result = word;
                    }
                }
            }

            if(result == null){
                for(String editOneWord : editDistanceOneWords){
                    editInsertion(editDistanceTwoWords, editOneWord);
                    editTransposition(editDistanceTwoWords, editOneWord);
                    editAlteration(editDistanceTwoWords, editOneWord);
                    editDeletion(editDistanceTwoWords, editOneWord);
                }
                for(String word : editDistanceTwoWords){
                    if(dictionary.find(word) != null){
                        if(dictionary.find(word).getValue() > highestCount) {
                            highestCount = dictionary.find(word).getValue();
                            result = word;
                        }
                    }
                }
            }
            return result;
        }
    }
    //find edit distance 1 insert words
    private void editInsertion(SortedSet<String> set, String input){
        for(int i = 0; i <= input.length(); i++){
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            for (char newLetter : alphabet) {
                StringBuilder word = new StringBuilder(input);
                word.insert(i, newLetter);
                set.add(word.toString());
            }
        }
    }

    //find edit distance 1 deletion words
    private void editDeletion(SortedSet<String> set, String input){
        for(int i = 0; i < input.length(); i++){
            StringBuilder word = new StringBuilder(input);
            StringBuilder newWord = word.deleteCharAt(i);
            set.add(newWord.toString());
        }
    }

    //find edit distance 1 transposition words
    private void editTransposition(SortedSet<String> set, String input){
        for(int i = 0; i < input.length() - 1; i++){
            StringBuilder word = new StringBuilder(input);
            char temp = word.charAt(i);
            word.setCharAt(i, word.charAt(i+1));
            word.setCharAt(i+1, temp);
            set.add(word.toString());
        }
    }

    //find edit distance 1 wrong character words
    private void editAlteration(SortedSet<String> set, String input){
        for(int i = 0; i < input.length(); i++){
            StringBuilder word = new StringBuilder(input);
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
            for (char newLetter : alphabet) {
                word.setCharAt(i, newLetter);
                set.add(word.toString());
            }
        }
    }

}


