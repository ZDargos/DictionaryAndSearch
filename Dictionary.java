import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Zack Dragos
//I pledge my honor that I have abided by the Stevens Honor System
//CS284-C
public class Dictionary {
    private int INITIAL_CAPACITY = 1300;
    private ArrayList<String> wordList = new ArrayList<String>();
    private ArrayList<DictionaryItem> dictArrayList = new ArrayList<DictionaryItem>();
    private String FILENAME = "ionDictionary.txt";


    /**
     *Constructor for Dictionary Class using the unchanged FILENAME as input for readfile
     */
    public Dictionary(){
        wordList.ensureCapacity(INITIAL_CAPACITY);
        dictArrayList.ensureCapacity(INITIAL_CAPACITY);
        readFile(FILENAME);

    }

    /**
     * Constructor for Dictionary Class but uses parameter fileName to implement readFile with
     * @param fileName
     */
    public Dictionary(String fileName){
        wordList.ensureCapacity(INITIAL_CAPACITY);
        dictArrayList.ensureCapacity(INITIAL_CAPACITY);
        readFile(fileName);
    }

    /**
     * getter for WordList
     * @return wordList
     */
    public ArrayList<String> getWordList(){
        return wordList;
    }

    /**
     * Getter for getDictArrayList
     * @return dictArrayList
     */
    public ArrayList<DictionaryItem> getDictArrayList(){
        return dictArrayList;
    }

    /**
     * helper function for readFile in which the scanner goes through the text document line by
     * line and adds the word to wordList, and then creates a Dictionary Item using the word and its count
     * to be inserted into dictArrayList.
     * @param c the scanner
     */
    private void splitStoreLine(Scanner c){
        while(c.hasNextLine()){
            String[] x = c.nextLine().split("[ | ]");

            if(x.length>1 && (x[x.length-1].equals("Count") == false)) {
                wordList.add(x[0]);
                dictArrayList.add(new DictionaryItem(x[0], Integer.parseInt(x[x.length - 1])));
            }
        }
        c.close();
    }
    /**
     * Reads the textfile 'fileName' and stores the words + counts in the respective wordList and dictArrayList arrayLists
     * If fileName DNE then will print an error message alongside the stacktrace
     */
    public void readFile(String fileName){
        File file = new File(fileName);
        Scanner c;
        try{
            c = new Scanner(file);
            c.nextLine();
            splitStoreLine(c);

        }
        catch(FileNotFoundException e){
            System.out.println("Error! File Not Found!");
            e.printStackTrace();
        }
    }

    /**
     * Prints the menu of the Ion Dictionary, waits for the users response as to what they want to do,
     * must be a 1 2 or 3 input, and then sends those inputs into the processMenu function.
     * If there is an erroneous input, the program will print an error message and ask user to try again
     */
    public void printMenu(){
        boolean on = true;
        int ans;
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Ion Dictionary! This dictionary is created from the book Ion by Plato!\n ");
        while(on){
            System.out.println("Please choose one of the following menu items indicated with 1-3\n" +
                    "1: To print all the words in the dictionary, choose 1\n" +
                    "2: To search a word in the dictionary, choose 2\n" +
                    "3: To quit the program, choose 3");
            try {
                ans = input.nextInt();
                if(ans != 3 && ans != 2 && ans != 1)
                    System.out.println("Input must be a 1, 2, or 3!");
                else{on = processMenu(ans, input);}
            }
            catch(IllegalArgumentException e){
                System.out.println("Input must be a 1, 2, or 3!");
            }
        }
    }

    /**
     * Helper function for printMenu in which the user's input is acted upon. Depending on the input of
     * 1, 2, or 3, the system will either return true, true, or false respectively. If 1 is selected,
     * the entire wordList is printed out. If 2, the program will ask the user for another input as to
     * what word to search for in the dictionary. If 3, the program will return false, thusly ending the
     * whileloop in printMenu.
     * @param ans
     * @param input
     * @return boolean for boolean 'on' in printMenu
     */
    private boolean processMenu(int ans, Scanner input){
        if(ans == 3) {
            System.out.println("Thank you for using Ion Dictionary! Buh-bye!");
            return false;
        }
        if(ans == 2){
            System.out.println("Please enter the word you would like to search:");
            String word = input.next();
            int index = searchDictionary(word);
            if(index < 0) {
                System.out.println("The word '" + word + "' does not exist in the Ion Dictionary!");
                return true;
            }
            else{
                System.out.println("The word '" + word + "' occurred " + dictArrayList.get(index).getCount() + " times in the book!");
                return true;
            }
        }
        if(ans == 1){
            printDictionary();
            return true;
        }
        else{return false;}
    }

    /**
     * prints all the words in wordList one by one
     */
    public void printDictionary(){
        for(int i = 0; i<wordList.size()-1; i++){
            System.out.println(wordList.get(i));
        }
    }

    /**
     * Implements binary search function for 'word'
     * @param word
     * @return
     */
    public int searchDictionary(String word){
        return binarySearch(word, wordList.size()-1, 0);
    }

    /**
     * Searches for the index of word "word" in the dictArrayList using a binary search algorithm in which
     * the size of the list is cut in half after each iteration as it closes in on the word.
     * @param word
     * @param high
     * @param low
     * @return If word is not found, returns index -1, if word is found, returns its index in dictArrayList
     */
    private int binarySearch(String word, int high, int low){
        int middle = low + ((high - low)/2);
        if(high < low){
            return -1;
        }
        if(word.equals(getDictArrayList().get(middle).getWord())){
            return middle;
        }
        else if(getDictArrayList().get(middle).getWord().compareTo(word) < 0){
            return binarySearch(word, high,middle+1);
        }
        else{return binarySearch(word,middle-1,low);}

    }

}