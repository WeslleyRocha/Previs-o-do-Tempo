package org.example.previsaotempo.api;

import com.google.gson.Gson;
import org.example.previsaotempo.model.WeatherData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherClient {

    private static final String WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true";
    private final HttpClient httpClient;
    private final Gson gson;

    public WeatherClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // Construtor adicionado para Injeção de Dependência (facilita os testes com Mockito)
    public WeatherClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.gson = new Gson();
    }

    public WeatherData getWeatherByCoordinates(double latitude, double longitude) throws Exception {
        String url = String.format(WEATHER_API_URL, String.valueOf(latitude).replace(",", "."), String.valueOf(longitude).replace(",", "."));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return gson.fromJson(response.body(), WeatherData.class);
        } else {
            throw new Exception("Erro ao consultar a API Open-Meteo. HTTP Status: " + response.statusCode());
        }
    }
}
