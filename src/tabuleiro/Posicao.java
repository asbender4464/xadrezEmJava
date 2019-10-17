package tabuleiro;

public class Posicao {

	//Atributos
	private int linha;
	private int coluna;
	
	//Construtor padr�o
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	//GETs e SETs
	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	
	//Imprimir posi��es na tela.
	@Override
	public String toString() {
		return linha + ", " + coluna;
	}
}
