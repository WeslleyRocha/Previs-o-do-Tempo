package org.example.previsaotempo.api;

import org.example.previsaotempo.exception.ApiClientException;
import org.example.previsaotempo.model.WeatherData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherClientTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    private WeatherClient weatherClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherClient = new WeatherClient(mockHttpClient);
    }

    @Test
    void getWeatherByCoordinates_Success() throws Exception {
        // Arrange
        // JSON simplificado que mapeie com a classe WeatherData
        String jsonResponse = "{ \"latitude\": -23.55, \"longitude\": -46.63, \"current_weather\": { \"temperature\": 25.5, \"windspeed\": 10.2 } }";
        
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockHttpResponse);

        // Act
        WeatherData weatherData = weatherClient.getWeatherByCoordinates(-23.5505, -46.6333);

        // Assert
        assertNotNull(weatherData);
    }

    @Test
    void getWeatherByCoordinates_ApiError_400() throws Exception {
        // Arrange
        // Simulando coordenadas erradas causando bad request na API
        when(mockHttpResponse.statusCode()).thenReturn(400);
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockHttpResponse);

        // Act & Assert
        Exception exception = assertThrows(ApiClientException.class, () -> {
            weatherClient.getWeatherByCoordinates(900.0, -200.0);
        });

        assertTrue(exception.getMessage().contains("Ocorreu um erro inesperado ao buscar os dados do tempo."));
    }
}
