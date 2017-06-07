package Helper;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;



/**
 * Created by christia
 * copied and modiefied from some online resource.
 */
public class PythonRunner {
    private String _pythonPath;
    private String _scriptPath;
    private String[] _args;

    public PythonRunner(String pythonPath, String scriptPath, String[] args) {
        _pythonPath = pythonPath;
        _scriptPath = scriptPath;
        _args = args;
    }
    public boolean executeAndReturnResult(){
        boolean result = true;
        JSONArray resultArray = (JSONArray)GenerateDummyOutput();
        if(resultArray == null)
            return false;
        if(resultArray.isEmpty())
            return false;
        for (Object json: resultArray ) {
            JSONObject jsonObject = (JSONObject)json;
            result &= jsonObject.get("currentState").toString().equals("PASSED");


        }
        return result;

    }
    public String execute() {
        String output = Globals.EmptyString;

        String[] cmd = new String[_args.length + 2];
        cmd[0] = _pythonPath; // check version of installed python: python -V
        cmd[1] = _scriptPath;

        for(int i = 0;i<_args.length;i++){
            cmd[i+2] = _args[i];
        }

        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        try {
            while ((line = bfr.readLine()) != null) {
                output += line + "\n";

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Debugger.log(output);
        return output;
    }





    public static void main(String[] args){
        PythonRunner prRunner = new PythonRunner("python", "/home/ubuntu/learning/python/First.py",new String[]{"-w", "-r", "client-test-location"});
        //String output = prRunner.execute();
        // temporary
        // hard coded output to verify tool
        // TODO: change later.

        Debugger.log(prRunner.executeAndReturnResult());
        //Debugger.log(GenerateDummyOutput());
    }

    private static Object GenerateDummyOutput(){
        String temp = Globals.EmptyString;

        temp = "{\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"errorMessages\": [],\n" +
                "      \"validatorIdentifier\": \"client-location-produce\",\n" +
                "      \"currentState\": \"PASSED\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"errorMessages\": [],\n" +
                "      \"validatorIdentifier\": \"client-image-produce\",\n" +
                "      \"currentState\": \"PASSED\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"errorMessages\": [],\n" +
                "      \"validatorIdentifier\": \"client-image-share\",\n" +
                "      \"currentState\": \"PASSED\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"errorMessages\": [],\n" +
                "      \"validatorIdentifier\": \"client-location-share\",\n" +
                "      \"currentState\": \"PASSED\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"testDurationMS\": 4540\n" +
                "}";

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject =  (JSONObject) parser.parse(temp);;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //parser.parse(temp);

        //Debugger.log(jsonObject.get("results"));
        return jsonObject.get("results");
    }



}