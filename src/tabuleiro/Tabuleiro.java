package tabuleiro;

public class Tabuleiro {
	
	//Atributos
	private int linhas;
	private int colunas;
	//Definindo uma matriz
	private Peca[][] pecas;
	
	//Construtor padr�o
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	//GETs e SETs somente de linhas e colunas
	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}
}
