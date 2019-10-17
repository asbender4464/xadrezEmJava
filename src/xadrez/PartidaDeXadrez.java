package xadrez;

import tabuleiro.Tabuleiro;

public class PartidaDeXadrez {
	//Esta Classe é o 'coração' do sistema de xadrez.
	//Aqui serão colocadas as regras do jogo.
	
	private Tabuleiro tabuleiro;

	//Construtor padrão
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
	}
	
	//Método
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //Há um 'downcast'aqui.
			}
		}
		return matriz;
	}
}
