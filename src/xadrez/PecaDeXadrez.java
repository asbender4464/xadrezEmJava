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
	
	//Método para incrementar o 'contador de movimentos'.
	public void incrementarContadorDeMovimentos () {
		contadorDeMovimentos++;
	}

	//Método para decrementar o 'contador de movimentos'.
	public void decrementarContadorDeMovimentos () {
		contadorDeMovimentos--;
	}
	
	//Método para obter a posição de uma peça de xadrez, no formato do 'xadrez', ou seja, 'letra e número'
	//De acordo com o diagrama UML, a aplicação não deve permitir acesso direto à 'matriz', que é definida
	//na Classe Peca.
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
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
