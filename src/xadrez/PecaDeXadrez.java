package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	//Atributo
	private Cor cor;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	//GET e SET
	public Cor getCor() {
		return cor;
	}

	//SET comentado para que não se tenha uma cor modificada.
	//public void setCor(Cor cor) {
	//	this.cor = cor;
	//}
	
	//Método(operação) para verificar se há uma peça do oponente em determinada casa do tabuleiro.
	//O método é 'protected', pois se deseja que o mesmo seja acessível somente para classes do pacote
	//bem como para as subclasses do pacote 'xadrez.pecas'.
	protected boolean haUmaPecaDoOponente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao); //É preciso fazer um "downcasting' aqui.
		return p != null && p.getCor() != cor;
	}
}
