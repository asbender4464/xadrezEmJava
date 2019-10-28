package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez {

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	@Override
	public String toString() {
		return "B";
		//Esta letra 'B', de Bispo, substituirá o '-' no tabuleiro.
	}
	
	//Método para implementar os movimentos possíveis do Bispo.
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz do tipo 'boolean' com a 'mesma dimensão' do tabuleiro.
		//Por definição, todas as posições desta matriz começam com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Marcando como 'true' as posições 'à NOROESTE' da posição do Bispo.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1); //Deslocando uma linha para cima e à esquerda, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à NORDESTE' da posição do Bispo.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1); //Deslocando uma linha para cima e à direita, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à SUDOESTE' da posição do Bispo.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1); //Deslocando uma linha para baixo e à esquerda, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à SUDESTE' da posição do Bispo.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1); //Deslocando uma linha para baixo e à direita, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		return matriz;
	}
}
