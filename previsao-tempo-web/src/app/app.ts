import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  cidade: string = '';
  clima: any = null;
  erro: string = '';

  // Inicia com uma imagem de sol / céu claro
  imageUrl: string = 'https://images.unsplash.com/photo-1601297183305-6df142704ea2?q=80&w=1920&auto=format&fit=crop';

  buscarTempo() {
    if (!this.cidade || this.cidade.trim() === '') {
      return;
    }

    this.erro = '';

    // Simular integração backend
    this.simularIntegracaoBackend(this.cidade);
  }

  private simularIntegracaoBackend(nomeDaCidade: string) {
    // Simula tempo de rede
    setTimeout(() => {
      if (nomeDaCidade.toLowerCase() === 'erro') {
        this.erro = 'Cidade não encontrada.';
        this.clima = null;
        return;
      }

      // Sorteia uma condição climática
      const condicoes = ['Sol', 'Chuva', 'Vento', 'Raios', 'Trovoes'];
      const condicaoAtual = condicoes[Math.floor(Math.random() * condicoes.length)];

      this.clima = {
        cidade: nomeDaCidade.toUpperCase(),
        temperatura: Math.floor(Math.random() * 15) + 15,
        descricao: condicaoAtual,
        vento: Math.floor(Math.random() * 50),
        umidade: Math.floor(Math.random() * 60) + 40
      };

      this.mudarImagemDeFundo(condicaoAtual);
    }, 600);
  }

  private mudarImagemDeFundo(condicao: string) {
    switch(condicao) {
      case 'Chuva':
        this.imageUrl = 'https://images.unsplash.com/photo-1515694346937-94d85e41e6f0?q=80&w=1920&auto=format&fit=crop';
        break;
        case 'Trovoes':
        this.imageUrl = 'https://images.ecycle.com.br/wp-content/uploads/2021/05/18142206/raios-e-trovoes-hd.jpg.webp';
        break;
      case 'Sol':
        this.imageUrl = 'https://images.unsplash.com/photo-1601297183305-6df142704ea2?q=80&w=1920&auto=format&fit=crop';
        break;
      case 'Vento':
        this.imageUrl = 'https://images.unsplash.com/photo-1505672678657-cc7037095e60?q=80&w=1920&auto=format&fit=crop';
        break;
      case 'Raios':
        this.imageUrl = 'https://images.unsplash.com/photo-1605727216801-e27ce1d0ce49?q=80&w=1920&auto=format&fit=crop';
        break;
      default:
        this.imageUrl = 'https://images.unsplash.com/photo-1601297183305-6df142704ea2?q=80&w=1920&auto=format&fit=crop';
        break;
    }
  }
}
