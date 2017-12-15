package wordpuzzle.ussdmodule.sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import wordpuzzle.ussdmodule.models.initiat.UssdInitiat;
import wordpuzzle.util.Utility;

public class HttpSender {

	public void requestSender(UssdInitiat ussdInitiat) throws IOException {
		
//		URL obj = new URL(Utility.USSD_SENDING_URL);
//        URLConnection con = obj.openConnection();
//        HttpURLConnection http = (HttpURLConnection)con;
//        http.setRequestMethod("POST"); // PUT is another valid option
//        http.setDoOutput(true);

        Gson gson = new GsonBuilder().create();
        
        System.out.println(gson.toJson(ussdInitiat));

//        byte[] out = gson.toJson(ussdInitiat).getBytes(StandardCharsets.UTF_8);
//        int length = out.length;

//        http.setFixedLengthStreamingMode(length);
//        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//        http.connect();
        System.out.println("\nSending 'POST' request to URL : " + Utility.USSD_SENDING_URL);
        
//        OutputStream os = http.getOutputStream();
//        os.write(out);
//
//        System.out.println(http.getResponseCode());
//
//        InputStream inputStream = http.getInputStream();
//        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//        BufferedReader reader = new BufferedReader(inputStreamReader);
//
//        String output = null;
//
//        while ((output = reader.readLine()) != null) {
//
//            System.out.println(output);
//        }
	}
}
