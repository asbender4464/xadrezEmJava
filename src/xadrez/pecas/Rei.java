package xadrez.pecas;

import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {

	public Rei(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
	}
	@Override
	public String toString() {
		return "K";
		//Esta letra 'K', de 'King', substituirá o '-' no tabuleiro.
	}
}
