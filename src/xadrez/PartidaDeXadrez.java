package xadrez;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	//Esta Classe � o 'cora��o' do sistema de xadrez.
	//Aqui ser�o colocadas as regras do jogo.
	
	private Tabuleiro tabuleiro;

	//Construtor padr�o
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		//Chamando o Setup Inicial, para come�ar a partida, colocando as pe�as no tabuleiro.
		setupInicial();
	}
	
	//M�todo
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //H� um 'downcast'aqui.
			}
		}
		return matriz;
	}
	//M�todo Setup Inicial. � respons�vel por iniciar a partida de xadrez, colocando as pe�as no tabuleiro.
	private void setupInicial() {
		tabuleiro.colocarPeca(new Torre(tabuleiro,Cor.BRANCAS), new Posicao(2,1));
		tabuleiro.colocarPeca(new Rei(tabuleiro,Cor.PRETAS), new Posicao(0,4));
		tabuleiro.colocarPeca(new Rei(tabuleiro,Cor.BRANCAS), new Posicao(7,4));
	}
}
