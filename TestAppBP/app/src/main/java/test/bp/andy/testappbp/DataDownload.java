package test.bp.andy.testappbp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.google.android.gms.internal.zzt.TAG;

/**
 * Created by Andy on 5/21/2017.
 *
 * generic class used to download raw JSON data from Google's API database from the URL string built
 */

public class DataDownload {

    public String processURL(String urlString) throws IOException {
        // initialize all variables we will be using to null
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        // now for the http request code
        try {
            // parse the string to an actual URL object
            URL url = new URL(urlString);

            // create the http connection and type cast it to the object
            httpURLConnection = (HttpURLConnection) url.openConnection();

            // connect to the url
            httpURLConnection.connect();

            // read data from the url and store it
            inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            data = stringBuffer.toString();
            bufferedReader.close();
        }
        catch (Exception rekt) {
            Log.d(TAG, "processURL: ", rekt);
        }
        finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return data;
    }
}
