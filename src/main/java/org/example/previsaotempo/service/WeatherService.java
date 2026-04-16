package org.example.previsaotempo.service;

import org.example.previsaotempo.api.GeocodingClient;
import org.example.previsaotempo.api.WeatherClient;
import org.example.previsaotempo.model.WeatherData;

public class WeatherService {
    private final GeocodingClient geocodingClient;
    private final WeatherClient weatherClient;

    public WeatherService() {
        this.geocodingClient = new GeocodingClient();
        this.weatherClient = new WeatherClient();
    }

    public void printWeatherForCity(String cityName) {
        try {
            System.out.println("Buscando informações para a cidade: " + cityName + "...");
            
            // 1. Busca as coordenadas da cidade (Open-Meteo exige latitude e longitude)
            GeocodingClient.Coordinates coordinates = geocodingClient.getCoordinatesForCity(cityName);
            
            // 2. Com as coordenadas, busca a previsão do tempo
            WeatherData weatherData = weatherClient.getWeatherByCoordinates(coordinates.getLatitude(), coordinates.getLongitude());
            
            // 3. Exibe os resultados
            WeatherData.CurrentWeather current = weatherData.getCurrentWeather();
            if (current != null) {
                System.out.println("\n=== Previsão do Tempo ===");
                System.out.println("Local: " + coordinates.getResolvedName());
                System.out.println("Temperatura: " + current.getTemperature() + " °C");
                System.out.println("Velocidade do vento: " + current.getWindspeed() + " km/h");
                
                // Formata a data e hora para um formato mais amigável
                String time = current.getTime().replace("T", " às ");
                System.out.println("Data e Hora do registro: " + time + "h");
                System.out.println("=========================\n");
            } else {
                System.out.println("Não foi possível obter a condição atual para esta cidade.");
            }
            
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao buscar os dados: " + e.getMessage());
        }
    }
}
