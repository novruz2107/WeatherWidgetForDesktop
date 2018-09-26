package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    Label city, temp, desc, humidity, pressure;
    @FXML
    GridPane gridPane;

    String cityString = "Baku";

    public void initialize(){

        gridPane.setStyle("-fx-background-color: rgba(0, 100, 100, 0.3);");
        Timer t = new Timer();

        t.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        handle();
                    }
                },
                0,      // run first occurrence immediatetly
                2000); // run every two seconds

    }

    public void handle() {

        String result = "";

        URL url;
        HttpURLConnection urlConnection = null;


        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + cityString + "&units=metric&mode=json&APPID=0eb169c9d6c02335f14c9579df3493a2");
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
            int data = reader.read();

            while (data != -1) {
                char current = (char) data;
                result += current;
                data = reader.read();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            city.setText("No internet!");
            e.printStackTrace();
        }


        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            JSONObject main = jsonObject.getJSONObject("main");

            city.setText(jsonObject.getString("name").toUpperCase());
            desc.setText(details.getString("description").toUpperCase());
            humidity.setText("Humidity: " + String.valueOf(main.getDouble("humidity")) + " %");
            pressure.setText("Pressure: " + String.valueOf(main.getDouble("pressure")) + " hpa");
            temp.setText(String.valueOf(main.getDouble("temp")) + " °C");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void changeCity(){
        if(cityString.equals("Baku")){
            cityString = "Ganja";
            handle();
        }else{
            cityString = "Baku";
            handle();
        }
    }


}
