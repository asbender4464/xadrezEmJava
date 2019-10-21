package xadrez;

import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	//Esta Classe é o 'coração' do sistema de xadrez.
	//Aqui serão colocadas as regras do jogo.
	
	private Tabuleiro tabuleiro;

	//Construtor padrão
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		//Chamando o Setup Inicial, para começar a partida, colocando as peças no tabuleiro.
		setupInicial();
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
	
	//Método que usa as 'coordenadas do Xadrez' (coluna e linha), e NÃO as 'matriciais' (linha e coluna).
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	//Método Setup Inicial. É responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro.
	private void setupInicial() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCAS));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETAS));
	}
}
