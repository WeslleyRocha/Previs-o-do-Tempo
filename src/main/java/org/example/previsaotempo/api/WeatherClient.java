package org.example.previsaotempo.api;

import com.google.gson.Gson;
import org.example.previsaotempo.exception.ApiClientException;
import org.example.previsaotempo.model.WeatherData;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class WeatherClient {

    private static final String WEATHER_API_URL = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current_weather=true";
    private final HttpClient httpClient;
    private final Gson gson;
    private final String apiKey; // Exemplo de como armazenar uma chave de API

    public WeatherClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        // PRÁTICA DE SEGURANÇA: Carregar a chave de API de variáveis de ambiente ou de um arquivo de configuração seguro.
        // NUNCA deixe a chave de API diretamente no código-fonte.
        this.apiKey = System.getenv("OPEN_METEO_API_KEY"); // Exemplo, mesmo que a API não exija
    }

    public WeatherClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.gson = new Gson();
        this.apiKey = System.getenv("OPEN_METEO_API_KEY");
    }

    public WeatherData getWeatherByCoordinates(double latitude, double longitude) throws ApiClientException {
        try {
            // A formatação de números de ponto flutuante é segura e não precisa de encoding
            String url = String.format(WEATHER_API_URL, String.valueOf(latitude).replace(",", "."), String.valueOf(longitude).replace(",", "."));

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET();

            // PRÁTICA DE SEGURANÇA: Adicionar a chave de API ao cabeçalho da requisição, se necessário.
            // O método mais comum é usar o cabeçalho "Authorization" ou um específico da API.
            if (apiKey != null && !apiKey.isEmpty()) {
                requestBuilder.header("Authorization", "Bearer " + apiKey);
            }

            HttpRequest request = requestBuilder.build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), WeatherData.class);
            } else {
                // PRÁTICA DE SEGURANÇA: Não expor detalhes internos da API
                throw new ApiClientException("Falha ao se comunicar com o serviço de previsão do tempo.");
            }
        } catch (Exception e) {
            // PRÁTICA DE SEGURANÇA: Encapsular a exceção original para não vazar detalhes
            throw new ApiClientException("Ocorreu um erro inesperado ao buscar os dados do tempo.", e);
        }
    }
}
