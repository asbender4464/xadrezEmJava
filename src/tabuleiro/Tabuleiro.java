package tabuleiro;

public class Tabuleiro {
	
	//Atributos
	private int linhas;
	private int colunas;
	//Definindo uma matriz
	private Peca[][] pecas;
	
	//Construtor padr�o
	public Tabuleiro(int linhas, int colunas) {
		//O 'if' abaixo � caracter�stico de 'programa��o defensiva'.
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("Erro na constru��o do tabuleiro: � preciso ter pelo menos 1 linha e 1 coluna.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	//GETs e SETs somente de linhas e colunas
	public int getLinhas() {
		return linhas;
	}

	//Programa��o defensiva: retirando (comentando) o 'setLinhas', para n�o permitir mudar a quantidade de linhas.
	//public void setLinhas(int linhas) {
	//	this.linhas = linhas;
	//}

	public int getColunas() {
		return colunas;
	}

	//Programa��o defensiva: retirando (comentando) o 'setColunas', para n�o permitir mudar a quantidade de colunas.
	//public void setColunas(int colunas) {
	//	this.colunas = colunas;
	//}
	
	//M�todo para retornar a pe�a, dada uma linha e uma coluna
	public Peca peca(int linha, int coluna) {
		//Programa��o defensiva
		if (!existePosicao(linha,coluna)) {
			throw new TabuleiroExcecao("Posi��o inexistente no tabuleiro.");
		}
		return pecas[linha][coluna];
	}
	
	//Sobrecarga do m�todo, retornando a pe�a pela posi��o
	public Peca peca (Posicao posicao) {
		//Programa��o defensiva para verificar se h� pe�a na posi��o.
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posi��o inexistente no tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	//M�todo 'Colocar Peca'.
	public void colocarPeca (Peca peca, Posicao posicao) {
		//Programa��o defensiva, para evitar sobreposi��o de pe�as.
		if (haUmaPeca(posicao)) {
			throw new TabuleiroExcecao("J� h� uma pe�a nessa posi��o " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		//� preciso informar que esta pe�a n�o est� mais na posi��o 'nula'.
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		//Programa��o defensiva para verificar se h� pe�a na posi��o.
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posi��o inexistente no tabuleiro.");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca auxiliar = peca(posicao);
		auxiliar.posicao = null; //A pe�a foi 'retirada do tabuleiro', sendo representada pelo 'nulo'.
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return auxiliar;
	}
	
	//M�todo auxiliar ao 'Existe Posi��o'
	public boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	//M�todo 'Existe Posi��o', valendo-se do m�todo auxiliar definido acima.
	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getLinha(),posicao.getColuna());
	}
	//M�todo 'H� uma pe�a'. Vale-se do m�todo 'Peca', em sobrecarga, definido acima.
	public boolean haUmaPeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posi��o inexistente no tabuleiro.");
		}
		return peca(posicao) != null;
	}
}
