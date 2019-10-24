package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	//Atributos
	private Cor cor;
	private int contadorDeMovimentos;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	//GETs
	public Cor getCor() {
		return cor;
	}

	public int getContadorDeMovimentos() {
		return contadorDeMovimentos;
	}
	
	//M�todo para incrementar o 'contador de movimentos'.
	public void incrementarContadorDeMovimentos () {
		contadorDeMovimentos++;
	}

	//M�todo para decrementar o 'contador de movimentos'.
	public void decrementarContadorDeMovimentos () {
		contadorDeMovimentos--;
	}
	
	//M�todo para obter a posi��o de uma pe�a de xadrez, no formato do 'xadrez', ou seja, 'letra e n�mero'
	//De acordo com o diagrama UML, a aplica��o n�o deve permitir acesso direto � 'matriz', que � definida
	//na Classe Peca.
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	//SET comentado para que n�o se tenha uma cor modificada.
	//public void setCor(Cor cor) {
	//	this.cor = cor;
	//}
	
	//M�todo(opera��o) para verificar se h� uma pe�a do oponente em determinada casa do tabuleiro.
	//O m�todo � 'protected', pois se deseja que o mesmo seja acess�vel somente para classes do pacote
	//bem como para as subclasses do pacote 'xadrez.pecas'.
	protected boolean haUmaPecaDoOponente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao); //� preciso fazer um "downcasting' aqui.
		return p != null && p.getCor() != cor;
	}
}
