package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class ZHttpRequest implements Runnable {

    private final String USER_AGENT = "Mozilla/5.0";
    static String request;

    String url;

    public ZHttpRequest (String command0) {
        url = command0;
    }



    // HTTP GET request
    public void run()  {
        // Format of String : url = http://192.168.1.101/REMOTE=ON
        try {
            URL urlObject = new URL(url);

            HttpURLConnection httpConnection = (HttpURLConnection) urlObject.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = httpConnection.getResponseCode();
            System.out.println("Sending Request : " + url);
            System.out.println("Response Code   : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //IT MUST SEND RESPOND TO ANDROID APP

            //Print the Response
            System.out.println(response.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
