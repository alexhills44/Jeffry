package main;


import java.io.File;
import java.util.Scanner;

public class InputsStringsHandler {

    private static int COMMAND = 2;
    private static int COMMAND_TYPE = 1;
    private static int WORD_TO_MATCH = 0;

    private String[] matchesFoundTypeOfCommand;
    private String[] matchesFoundCommand;
    private String[] matchesFoundWords;
    private String matchesFoundTypeOfCommandSingle;
    private String matchesFoundCommandSingle;
    private int maxMatches;
    private int matchesCounter;
    private String[] inputs;
    private String name;

    boolean exitLoop = false;


    public InputsStringsHandler (String inputs0 , String userName0) {
        matchesFoundWords=null;
        matchesCounter=0;
        searchTxtCommandFileForOneInput(inputs0);
    }


    public InputsStringsHandler (String[] inputs0 , String userName0) {
        inputs = inputs0;
        name = userName0;
    }
    //Finds the Right Command according to inputs[]
    // 1
    public void phraseArrayAnalyzer () {
        matchesFoundWords=null;
        matchesCounter=0;
        int i=0;
        exitLoop=false;
        while((!exitLoop) && (i<6)) {
            if (inputs[i]!=null) {
                //This method detects the 'and' in the phrase
                //and Calls searchTxtCommandFileForOneInput();
                phraseSplitter(inputs[i]);
                i++;
            }
        }
        //from the array that contains the matches it pick the one with the longest length
        maxMatches = matchesFoundWords[0].length();
        for(int j=0;j<matchesCounter;j++) {
            if(maxMatches <matchesFoundWords[i].length()) {
                maxMatches = matchesFoundWords[i].length();

                matchesFoundCommandSingle = matchesFoundCommand[i];
                matchesFoundTypeOfCommandSingle = matchesFoundTypeOfCommand[i];
            }
        }
        executer(matchesFoundTypeOfCommandSingle,matchesFoundCommandSingle);
    }

    // If Phrase contains 'and' then splits the phrase to two phrases
    // 2
    public void phraseSplitter(String input) {
        if(input.contains("and")) {
            String[] splitInput = input.split("and");
            for (int i=splitInput.length-1;i>=0;i--) {
                searchTxtCommandFileForOneInput(splitInput[i]);
            }
        }else {
            searchTxtCommandFileForOneInput(input);
        }
    }

    //Run this method for every input!
    // 3
    public void searchTxtCommandFileForOneInput(String input) {
        String[] splittedInput = input.split(" "); //splits the input to seperate words so it can compare them
        String show=null; // string that stores the first part of the txt file
        String typeOfCommand = null; // string that stores the second part of the txt file
        String command = null; // string that stores the third part of the txt file
        int accuracy=0; //stores the accuracy
        int max=0;  // stores the max accuracy

        try {
            //declare the scanner class and pass in the txt path
            Scanner filescan = new Scanner(new File("C:\\Users\\jeffry\\Desktop\\jeffry_1.3\\src\\TextFiles\\commands.txt"));
            String line; // string that stores the current line

            while (filescan.hasNextLine()) { // while the txt file has next line
                line = filescan.nextLine(); //store to the "line" string the line
                String[] splittedLine = line.split("---"); // split it to parts (when it sees '---')
                String [] splittedLineToWords = splittedLine[WORD_TO_MATCH].split(" "); //split the splited line from txt to words
                accuracy = 0; //set the accuracy for each line at 0

                for (int j=0;j< splittedLineToWords.length;j++) { //for each word in the first part split it to seperate words
                    for (int i=0;i<splittedInput.length;i++) { // for each word in the splited input

                        if (splittedInput[i].equalsIgnoreCase(splittedLineToWords[j])) { //if they have the same word ignoring case
                            accuracy++; //increase accuracy
                        }
                        //System.out.println(accuracy);
                        if (accuracy > max) { //add it to max if its bigger than max

                            max=accuracy;
                            show = splittedLine[WORD_TO_MATCH]; //store it
                            typeOfCommand = splittedLine[COMMAND_TYPE]; //store it
                            command = splittedLine[COMMAND]; //store it

                            if (max == show.split(" ").length) {

                                System.out.println("Command Found : "+command);
                                System.out.println("Type of Command : "+typeOfCommand);
                                System.out.println("Command Found : "+show);
                                matchesFoundWords[matchesCounter] = show;
                                matchesFoundCommand[matchesCounter] = command;
                                matchesFoundTypeOfCommand[matchesCounter] = typeOfCommand;

                                matchesCounter++;
                            }
                        }
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Executes the commands
    public void executer (String typeOfCommand, String command) {
        // Call the right class to handle the command
        if (typeOfCommand.equals("Request")) {
            new ZHttpRequest(command).run();
        }else if (typeOfCommand.equals("cmd")) {
            new ZCmdExecuter(command).run();
        }
    }
}







