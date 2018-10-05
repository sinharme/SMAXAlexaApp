package com.mf.smax.alexa.request;


import com.mf.smax.alexa.tools.SSLTool;
import org.json.simple.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class SMAXRestRequest {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String CONTENT_TYPE = "application/json";

   //public static final String HOST = "smax2018.05.itsma-demo.net"; // e.g. "mslon001pngx.saas.hp.com"
    // public static final String TENANTID = "712737951"; // e.g. "602818407"

    public static  String HOST = "mars.itsma-x.io"; // e.g. "mslon001pngx.saas.hp.com"
    public  static  String TENANTID = "119438017"; // e.g. "602818407"

    private static  String USERID = "jennifer.falconmf";
    private static  String PASSWORD = "Password_123";


    public static void main(String[] args) {
        SMAXRestRequest connRequest = new SMAXRestRequest();
        System.out.println("Testing 1 - Send Http auth request");
        String cookie = null;
        SSLTool.disableCertificateValidation();
        try {
            cookie = "LWSSO_COOKIE_KEY=" + connRequest.getAuthKey() + "; TENANTID=" + TENANTID;
            } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(cookie);
        System.out.println("\nTesting 2 - Send Http GET request");
        try {
            System.out.println( connRequest.getRecordDescription(cookie,"Incident"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*public SMAXRestRequest(){
        SSLTool.disableCertificateValidation();
        this.HOST=System.getenv("HOST");
        this.TENANTID=System.getenv("TENANTID");
        this.USERID=System.getenv("USERID");
        this.PASSWORD=System.getenv("PASSWORD");


    }*/
    public String getRecordDescription(String cookie, String recordType) throws Exception {

        String url = "https://" + HOST + "/rest/" + TENANTID + "/ems/"+recordType+"?filter=(Priority+%3D+%27CriticalPriority%27%20and%20Active+%3D+%27true%27)&layout=Id,DisplayLabel";
        SSLTool.disableCertificateValidation();
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", CONTENT_TYPE);
        con.setRequestProperty("Cookie", cookie);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());


        Integer total=0;
        String say= " Following are some of the critical "+recordType+"s :" + System.lineSeparator();
        Map<String, String> newmap = JSONParserUtil.getRecordListDescription(response.toString());
        for (Map.Entry<String, String> entry : newmap.entrySet()){
            say= say+ "  "+" Record ID "+ entry.getKey()+"  Title: "+ entry.getValue()+"," + System.lineSeparator();
           // total += Integer.parseInt((entry.getValue()));
        }

            /*System.out.println("Priorty = " + entry.getKey() +
                    ", Count = " + entry.getValue());*/


        return say;
    }


    // HTTP GET request
    public String sendGetPriorityAndRecordCount(String cookie, String recordType) throws Exception {

        String url = "https://" + HOST + "/rest/" + TENANTID + "/ems/"+recordType+"/aggregations?filter=Active+%3D+'true'&group=Priority&layout=Priority,Count(Id)";
        SSLTool.disableCertificateValidation();
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", CONTENT_TYPE);
        con.setRequestProperty("Cookie", cookie);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());


        Integer total=0;
        String say= " ";
        Map<String, String> newmap = JSONParserUtil.getPrirityAndCount(response.toString());
        for (Map.Entry<String, String> entry : newmap.entrySet()){
            say= say+ "  "+" There are "+ entry.getValue()+" " +recordType+"s of "+ entry.getKey()+", " + System.lineSeparator();
            total += Integer.parseInt((entry.getValue()));
        }
        String speechText = say +". Hence in total there are "+ total +"  total number of "+recordType+"s" ;
            /*System.out.println("Priorty = " + entry.getKey() +
                    ", Count = " + entry.getValue());*/


        return speechText;
    }


    public String getAuthKey() throws Exception {
        String url = "https://" + HOST + "/auth/authentication-endpoint/authenticate/login?TENANTID=" + TENANTID; //USERID + "&password=" + PASSWORD;
        SSLTool.disableCertificateValidation();
        URL object = new URL(url);
        HttpURLConnection con = null;
        con = (HttpURLConnection) object.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");
        JSONObject body = new JSONObject();
        body.put("Login", USERID);
        body.put("Password", PASSWORD);

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(body.toString());

        OutputStream os = con.getOutputStream();
        os.write(body.toString().getBytes());

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (con.getInputStream())));

        String output = "";
        String key = "";
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            key += output;
            System.out.println(output);
        }

        return key;

    }
}

