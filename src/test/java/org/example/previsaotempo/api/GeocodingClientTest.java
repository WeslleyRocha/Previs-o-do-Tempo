package org.example.previsaotempo.api;

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

class GeocodingClientTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;

    private GeocodingClient geocodingClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        geocodingClient = new GeocodingClient(mockHttpClient);
    }

    @Test
    void getCoordinatesForCity_Success() throws Exception {
        // Arrange
        String jsonResponse = "{ \"results\": [ { \"name\": \"Londres\", \"latitude\": 51.50853, \"longitude\": -0.12574 } ] }";
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockHttpResponse);

        // Act
        GeocodingClient.Coordinates coords = geocodingClient.getCoordinatesForCity("Londres");

        // Assert
        assertNotNull(coords);
        assertEquals("Londres", coords.getResolvedName());
        assertEquals(51.50853, coords.getLatitude());
        assertEquals(-0.12574, coords.getLongitude());
    }

    @Test
    void getCoordinatesForCity_CityNotFound() throws Exception {
        // Arrange
        // A API retorna 200 mas sem a chave "results" se a cidade não existir
        String jsonResponse = "{ \"generationtime_ms\": 0.123 }";
        when(mockHttpResponse.statusCode()).thenReturn(200);
        when(mockHttpResponse.body()).thenReturn(jsonResponse);
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockHttpResponse);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            geocodingClient.getCoordinatesForCity("Xyz123CidadeFantasma");
        });

        assertTrue(exception.getMessage().contains("Cidade não encontrada"));
    }

    @Test
    void getCoordinatesForCity_ApiError_500() throws Exception {
        // Arrange
        when(mockHttpResponse.statusCode()).thenReturn(500);
        when(mockHttpClient.send(any(HttpRequest.class), ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()))
                .thenReturn(mockHttpResponse);

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            geocodingClient.getCoordinatesForCity("São Paulo");
        });

        assertTrue(exception.getMessage().contains("Erro ao consultar a API de Geocoding. HTTP Status: 500"));
    }
}
