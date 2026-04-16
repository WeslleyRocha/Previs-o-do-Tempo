package org.example.previsaotempo.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Open-Meteo Weather API uses coordinates, not city names.
 * We must first use the Geocoding API to resolve a city name to latitude and longitude.
 */
public class GeocodingClient {

    private static final String GEOCODING_API_URL = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=1&language=pt";
    private final HttpClient httpClient;
    private final Gson gson;

    public GeocodingClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    // Construtor adicionado para Injeção de Dependência (facilita os testes com Mockito)
    public GeocodingClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.gson = new Gson();
    }

    public Coordinates getCoordinatesForCity(String cityName) throws Exception {
        String url = String.format(GEOCODING_API_URL, cityName.replace(" ", "+"));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            if (jsonObject.has("results")) {
                JsonArray results = jsonObject.getAsJsonArray("results");
                if (results.size() > 0) {
                    JsonObject firstResult = results.get(0).getAsJsonObject();
                    double latitude = firstResult.get("latitude").getAsDouble();
                    double longitude = firstResult.get("longitude").getAsDouble();
                    String name = firstResult.get("name").getAsString();
                    return new Coordinates(latitude, longitude, name);
                }
            }
            throw new Exception("Cidade não encontrada: " + cityName);
        } else {
            throw new Exception("Erro ao consultar a API de Geocoding. HTTP Status: " + response.statusCode());
        }
    }

    public static class Coordinates {
        private final double latitude;
        private final double longitude;
        private final String resolvedName;

        public Coordinates(double latitude, double longitude, String resolvedName) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.resolvedName = resolvedName;
        }

        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public String getResolvedName() { return resolvedName; }
    }
}
