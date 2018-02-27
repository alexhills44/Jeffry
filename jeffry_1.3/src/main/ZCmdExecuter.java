package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ZCmdExecuter implements Runnable{

    String command;

    public ZCmdExecuter (String command0) {
        command = command0;
    }


    public void run ()  {
        //Opens cmd.exe and passes in the commands
        //Command Format cd C:\\Program Files\\Mozilla Firefox\\ && firefox.exe -new-window "www.yahoo.com"
        //For more commands visit www.nirsoft.net or google NirCmd.exe
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",command);
        builder.redirectErrorStream(true);
        try {
            Process p = builder.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
