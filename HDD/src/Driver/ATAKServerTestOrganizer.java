package Driver;

import Helper.Debugger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Iterator;
import org.json.simple.parser.ParseException;

/**
 * Created by root on 9/4/17.
 */
public class ATAKServerTestOrganizer {

    public String testJsonFile;
    public Dictionary<String,Boolean> tests;
    public Dictionary<String, Integer> tests2; // for future usage
    public Dictionary<String, Object> tests3; // for future usage, replace object by proper data structure.
    public JSONArray requiredTests;
    public JSONArray optionalTests;
    public ATAKServerTestOrganizer(String testJsonFile){
        this.testJsonFile = testJsonFile;
    }

    public void Organize(){
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(this.testJsonFile));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject);
            requiredTests = (JSONArray) jsonObject.get("requiredValidators");
            optionalTests = (JSONArray) jsonObject.get("optionalValidators");

            Debugger.log(requiredTests);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        ATAKServerTestOrganizer testOrganizer = new ATAKServerTestOrganizer("/home/ubuntu/research/HDD/src/Driver/tests.json");
        testOrganizer.Organize();
    }

}
