package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez {

	public Cavalo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	@Override
	public String toString() {
		return "C";
		//Esta letra 'C', de 'Cavalo', substituirá o '-' no tabuleiro.
	}
	
	//Método auxiliar 'pode mover'. Ele verificará se o Cavalo poderá se mover para certa posição.
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor(); //Testa se a casa está 'vazia' ou se há 'peça do adversário' (cor diferente).
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz do tipo 'boolean' com a 'mesma dimensão' do tabuleiro.
		//Por definição, todas as posições desta matriz começam com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0); //Criação de posição 'auxiliar'.
		
		//Testando as 8 posições possíveis para o movimento de um Cavalo.
		
		//Posição 1: 2 à ESQUERDA e 1 ACIMA.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 2: 1 à ESQUERDA e 2 ACIMA.
		p.setValores(posicao.getLinha() - 2, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 3: 1 à DIREITA e 2 ACIMA.
		p.setValores(posicao.getLinha() - 2, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 4: 2 à DIREITA e 1 ACIMA.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 5: 1 ABAIXO e 2 à DIREITA.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 6: 2 ABAIXO e 1 à DIREITA.
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 7: 1 à ESQUERDA e 2 ABAIXO.
		p.setValores(posicao.getLinha() + 2, posicao.getColuna() -1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Posição 8: 2 à ESQUERDA e 1 ABAIXO.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 2);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		return matriz;
	}
}
