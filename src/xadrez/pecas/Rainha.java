package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

/*
 * NOTA: os movimentos de uma Rainha são a 'soma' dos movimentos de uma 'Torre' com os de um 'Bispo'.
 * Como perceberás abaixo, os códigos destas classes foram integralmente aproveitados aqui!
 */

public class Rainha extends PecaDeXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	@Override
	public String toString() {
		return "Q";
		//Esta letra 'Q', de Queen, substituirá o '-' no tabuleiro.
	}
	
	//Método para implementar os movimentos possíveis da Rainha.
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz do tipo 'boolean' com a 'mesma dimensão' do tabuleiro.
		//Por definição, todas as posições desta matriz começam com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Marcando como 'true' as posições 'ACIMA' da posição da Rainha.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À ESQUERDA' da posição da Rainha.
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À DIREITA' da posição da Rainha.
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'ABAIXO' da posição da Rainha.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à NOROESTE' da posição da Rainha.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() - 1); //Deslocando uma linha para cima e à esquerda, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à NORDESTE' da posição da Rainha.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1); //Deslocando uma linha para cima e à direita, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à SUDOESTE' da posição da Rainha.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1); //Deslocando uma linha para baixo e à esquerda, no tabuleiro.
		}
		//Testando se há peça de oponente e, se sim, marcar a respectiva posição como 'true' também.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'à SUDESTE' da posição da Rainha.
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
