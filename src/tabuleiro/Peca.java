package tabuleiro;

public class Peca {
	//Em UML o "#" antes do nome da Classe significa que ela � "protegida".
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	//Construtor padr�o somente com 'tabuleiro'
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	//GET s� do Tabuleiro.
	//"Protected" pq. somente Classes dentro do mesmo pacote e subclasses destas � que poder�o
	//acessar o tabuleiro de uma pe�a.
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	//SET foi comentado para n�o permitir que o Tabuleiro seja alterado
	//public void setTabuleiro(Tabuleiro tabuleiro) {
	//	this.tabuleiro = tabuleiro;
	//}
	
}
