package main;

import java.io.File;
import java.util.Vector;

public class SearchFiles implements Runnable {

    static Vector<File> vector;

    static String drive,phrase;

    public SearchFiles(String pathToFolder, String input) {
        drive = pathToFolder;
        phrase = input;
    }
    @Override
    public void run() {

        File file=new File(drive);
        vector=new Vector<File>();
        System.out.println("\nStarting search....\n-------------------------\n");
        search(file,phrase);
        searchExtended();

        print();

    }



    public static void searchExtended () {
        String searchBestMatch="";
        int max=0;
        File[] filesFound =new File[vector.size()];
        vector.copyInto(filesFound);
        String[] phraseWord = phrase.split(" ");
        for (File f : filesFound) {
            int accuracy = 0;
            for (String a:phraseWord) {
                if ((f.toString().toLowerCase()).contains(a.toLowerCase())) {
                    accuracy++;
                }
            }
            if(accuracy > max) {
                searchBestMatch = f.toString();
            }
        }
        System.out.println("\nBEST");
        System.out.println("------------------------------------------");
        System.out.println(searchBestMatch);
        System.out.println("\nBEST");
        System.out.println("------------------------------------------");
    }



    public static void search(File file,String name) {

        System.out.println("Searching in "+file.getAbsolutePath());
        if(file.isDirectory())
        {
            if(file.getName().contains(name))
            {
                vector.addElement(file);
            }
            File[] files=file.listFiles();
            for(int i=0;i<files.length;i++)
            {
                try
                {
                    if(files[i].isDirectory())
                    {
                        // Go search for files if dir
                        search(files[i],name);
                    }
                    else
                    {
                        if(files[i].getName().toLowerCase().contains(name.toLowerCase()))
                        {
                            // Add the found file to vector
                            vector.addElement(files[i]);
                        }
                    }
                }catch(Exception e){}
            }
        }
    }

    public static void print() {

        // Create a file array of v size
        File[] f=new File[vector.size()];
        // Copy vector data into f
        vector.copyInto(f);
        // Print the results
        System.out.println("\nResults");
        System.out.println("------------------------------------------");
        // Loop till end of size
        for(File k:f)
        {
            // Print the file path
            System.out.println("Found at "+k.getAbsolutePath());
        }
    }
}