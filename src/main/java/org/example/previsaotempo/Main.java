package org.example.previsaotempo;

import org.example.previsaotempo.service.WeatherService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherService weatherService = new WeatherService();

        System.out.println("Bem-vindo ao App de Previsão do Tempo!");
        
        while (true) {
            System.out.print("Digite o nome da cidade (ou 'sair' para encerrar): ");
            String cidade = scanner.nextLine().trim();

            if (cidade.equalsIgnoreCase("sair")) {
                System.out.println("Saindo do aplicativo. Até mais!");
                break;
            }

            if (!cidade.isEmpty()) {
                weatherService.printWeatherForCity(cidade);
            } else {
                System.out.println("Por favor, digite um nome de cidade válido.");
            }
        }

        scanner.close();
    }
}
