package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text feels_like;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    void initialize() {
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=4685966217fa544fd486274b66369bea&units=metric");

                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                    feels_like.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                    temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                    temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                }
            }
           });
    }

    private static String getUrlContent(String urlAddress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(urlConn.getInputStream())));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город был не найден!");
        }
        return content.toString();
    }
}

