package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}

	//Método para implementar os movimentos possíveis de um peão.
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz auxiliar do tipo 'boolean' com a 'mesma dimensão' do tabuleiro.
		//Por definição, todas as posições desta matriz começam com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		//PEÃO BRANCO: movimentos 'para frente' e movimentos 'diagonais'.
		
		//Movimentos para frente.
		if (getCor() == Cor.BRANCAS) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorDeMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Movimentos diagonais. Só se houver peças dos adversário nas casas possíveis de destino. Neste caso, haverá captura.
			
			//Diagonal à 'esquerda'.
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Diagonal à 'direita'.
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			
			//PEÃO PRETO: movimentos 'para frente' e movimentos 'diagonais'.
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorDeMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Movimentos diagonais. Só se houver peças dos adversário nas casas possíveis de destino. Neste caso, haverá captura.
			
			//Diagonal à 'esquerda'.
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Diagonal à 'direita'.
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
		}
		return matriz;
	}
	@Override
	public String toString() {
		return "P";
	}
}
