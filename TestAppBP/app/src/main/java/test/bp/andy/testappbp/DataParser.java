package test.bp.andy.testappbp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andy on 5/21/2017.
 *
 *
 */

public class DataParser {

    // method that creates a JSON data object and returns it as a hashmap list
    public List<HashMap<String,String>> parse(String jsonString) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject((String) jsonString);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException rekt) {
            rekt.printStackTrace();
        }
        return addLocations(jsonArray);
    }

    // method that returns a list of locations from the JSON object
    private List<HashMap<String, String>> addLocations(JSONArray jsonArray) {
        // count the length of the array
        int locationCount = jsonArray.length();

        // deal with the adt
        List<HashMap<String, String>> locationList = new ArrayList<>();
        HashMap<String, String> locationMap = null;

        // save all the locations returned into the hashmap
        for (int i = 0; i < locationCount; i++) {
            try {
                locationMap = addLocation((JSONObject) jsonArray.get(i));
                locationList.add(locationMap);
            }
            catch (JSONException rekt) {
                rekt.printStackTrace();
            }
        }
        return locationList;
    }

    private HashMap<String, String> addLocation(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        Log.d("getPlace", "Entered");

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}
