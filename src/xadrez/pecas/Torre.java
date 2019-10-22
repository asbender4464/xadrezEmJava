package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez {

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	@Override
	public String toString() {
		return "T";
		//Esta letra 'T', de Torre, substituir� o '-' no tabuleiro.
	}
	
	//M�todo para implementar os movimentos poss�veis da TORRE.
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz do tipo 'boolean' com a 'mesma dimens�o' do tabuleiro.
		//Por defini��o, todas as posi��es desta matriz come�am com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Marcando como 'true' as posi��es 'ACIMA' da posi��o da Torre.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se h� pe�a de oponente e, se sim, marcar a respectiva posi��o como 'true' tamb�m.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� ESQUERDA' da posi��o da Torre.
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se h� pe�a de oponente e, se sim, marcar a respectiva posi��o como 'true' tamb�m.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� DIREITA' da posi��o da Torre.
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se h� pe�a de oponente e, se sim, marcar a respectiva posi��o como 'true' tamb�m.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es 'ABAIXO' da posi��o da Torre.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1); //Deslocando uma linha para cima, no tabuleiro.
		}
		//Testando se h� pe�a de oponente e, se sim, marcar a respectiva posi��o como 'true' tamb�m.
		if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		return matriz;
	}
}
