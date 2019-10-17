package tabuleiro;

public class Peca {
	//Em UML o "#" antes do nome da Classe significa que ela é "protegida".
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	//Construtor padrão somente com 'tabuleiro'
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	//GET só do Tabuleiro.
	//"Protected" pq. somente Classes dentro do mesmo pacote e subclasses destas é que poderão
	//acessar o tabuleiro de uma peça.
	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	//SET foi comentado para não permitir que o Tabuleiro seja alterado
	//public void setTabuleiro(Tabuleiro tabuleiro) {
	//	this.tabuleiro = tabuleiro;
	//}
	
}
