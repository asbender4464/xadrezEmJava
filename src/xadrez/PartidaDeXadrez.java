package xadrez;

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
	
	//M�todo que usa as 'coordenadas do Xadrez' (coluna e linha), e N�O as 'matriciais' (linha e coluna).
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	//M�todo Setup Inicial. � respons�vel por iniciar a partida de xadrez, colocando as pe�as no tabuleiro.
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
