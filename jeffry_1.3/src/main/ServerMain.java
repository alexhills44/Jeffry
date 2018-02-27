package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import Controllers.SecondController;


public class ServerMain extends Thread{
    //defines the PORT in which the server is going to be assigned to
    private static int PORT = 9001;
    private static String networkName= "Wireless Network Connection";
    private static String SUBNET_MASK = "255.255.255.0";
    private static String GATEWAY = "192.168.1.1";
    private static String IP_ADDRESS = "192.168.1.3";
    //define the array||hashset to keep track of the users
    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */

    @Override
    public void run() {
        System.out.println("System : Ip Address : "+IP_ADDRESS);
        System.out.println("System : Port Number : "+PORT);
        System.out.println("System : Gateway : "+GATEWAY);
        //set IP_ADDRESS
//        new ZCmdExecuterNonThread("netsh interface ipv4 set address name="+networkName+
//                " static "+IP_ADDRESS+" "+SUBNET_MASK +" "+GATEWAY).run();
        try {
            System.out.println("The chat server is running.");
            System.out.println(IP_ADDRESS+PORT);
            System.out.println(InetAddress.getLocalHost());
            ServerSocket listener = new ServerSocket(PORT);

            try {
                while (true) {
                    new Handler(listener.accept()).start();
                }
            } finally {
                listener.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        String[] inputs = new String[6];
        int inputValue; //just for the inputs array
        boolean arrayIsReady = false; // if array finished then its true
        //SecondController diplay = new SecondController();
        //When new Clients Connects it shouts out "NEW CLIENT" and assisgns the socket in which it listens too
        public Handler(Socket socket) {
            this.socket = socket;
            System.out.println("1.  New Client Guys!!");
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(),true);

                //Constantly requests name until the name entered isnt already in use
                while(true) {
                    System.out.println("2.  Requesting name...");
                    out.println("SUBMITNAME");
                    name = in.readLine();

                    if(name == null) {
                        return;
                    }
                    synchronized(names) {
                        if (!names.contains(name)) {
                            //if name isnt use add it to the names Hashset and exit loop
                            names.add(name);
                            break;
                        }
                    }
                }
                //Send to the Client that the name has been accepted
                out.println("NAMEACCEPTED");
                System.out.println("3.  Name Accepted!");
                writers.add(out);

                while (true) {
                    //Reads the line send from the client
                    String input = in.readLine();
                    //if its nothing then do nothing!
                    if (input == null) {
                        return;
                    }


                    //////////////////////////////////////////////////////////////////
                    //////////////////////// HANDLE STRINGS //////////////////////////
                    //////////////////////////////////////////////////////////////////

                    //
                    stringArrayAssembler(input,name);
                    //Logs all the input Passed in
                    textLogFileEditor(input,name);
//                    SecondController.displayText(name,input);

                    if(arrayIsReady) {
                        // call class to handle array and execute the commands
                        InputsStringsHandler inputsStringsHandler = new InputsStringsHandler (inputs ,name);
                        inputsStringsHandler.phraseArrayAnalyzer();
                        arrayIsReady=false;
                    }

                    //////////////////////////////////////////////////////////////////
                    //////////////////////// HANDLE STRINGS //////////////////////////
                    //////////////////////////////////////////////////////////////////

                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + input);
                        //pass the input in the analyzer class
                        //it has to take the integer value and
                        //know how many messages it should wait
                        //also take in the the name of whom requests it

                    }
                }
                //adding all the exception that this shit is going to throw
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }

        }
        // this methods add the possible inputs into an array ready for shipping
        public void stringArrayAssembler(String input,String name) {

            if (input.equals("&")) {
                inputs[inputValue] = input;
                inputValue=0;
                arrayIsReady = true;
            }if(!input.equals("&")) {
                inputs [inputValue] = input;
                inputValue++;

            }

        }
        //this method logs all the input data to the userName.txt file can be used to learn from
        public void textLogFileEditor (String input,String name) {
            BufferedWriter textWriter = null;
            DateFormat date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date today = Calendar.getInstance().getTime();
            String stringDateToday = date.format(today);
            try {
                String logFilePath = "C:\\Users\\jeffry\\Desktop\\jeffry_1.3\\src\\TextFiles\\alex.txt";
                textWriter = new BufferedWriter(new FileWriter(logFilePath,true));
                textWriter.write(stringDateToday + " # " + " # " + name + " # ");
                textWriter.write(input+"\n");
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("Incorrect Log File path!!!");
            }finally {
                try {
                    textWriter.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setNetworkName(String networkName0) {
        networkName=networkName0;
    }
    public static void setSubnetMask(String subnetMask) {
        SUBNET_MASK = subnetMask;
    }
    public static void setGATEWAY(String gateway) {
        GATEWAY = gateway;
    }
    public static void setPORT (int port) {
        PORT = port;
    }
    public static void setIpAddress(String ipAddress) {
        IP_ADDRESS = ipAddress;
    }

}
