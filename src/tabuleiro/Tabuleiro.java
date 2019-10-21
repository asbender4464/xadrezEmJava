package tabuleiro;

public class Tabuleiro {
	
	//Atributos
	private int linhas;
	private int colunas;
	//Definindo uma matriz
	private Peca[][] pecas;
	
	//Construtor padrão
	public Tabuleiro(int linhas, int colunas) {
		//O 'if' abaixo é característico de 'programação defensiva'.
		if (linhas < 1 || colunas < 1) {
			throw new TabuleiroExcecao("Erro na construção do tabuleiro: é preciso ter pelo menos 1 linha e 1 coluna.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	//GETs e SETs somente de linhas e colunas
	public int getLinhas() {
		return linhas;
	}

	//Programação defensiva: retirando (comentando) o 'setLinhas', para não permitir mudar a quantidade de linhas.
	//public void setLinhas(int linhas) {
	//	this.linhas = linhas;
	//}

	public int getColunas() {
		return colunas;
	}

	//Programação defensiva: retirando (comentando) o 'setColunas', para não permitir mudar a quantidade de colunas.
	//public void setColunas(int colunas) {
	//	this.colunas = colunas;
	//}
	
	//Método para retornar a peça, dada uma linha e uma coluna
	public Peca peca(int linha, int coluna) {
		//Programação defensiva
		if (!existePosicao(linha,coluna)) {
			throw new TabuleiroExcecao("Posição inexistente no tabuleiro.");
		}
		return pecas[linha][coluna];
	}
	
	//Sobrecarga do método, retornando a peça pela posição
	public Peca peca (Posicao posicao) {
		//Programação defensiva para verificar se há peça na posição.
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posição inexistente no tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	//Método 'Colocar Peca'.
	public void colocarPeca (Peca peca, Posicao posicao) {
		//Programação defensiva, para evitar sobreposição de peças.
		if (haUmaPeca(posicao)) {
			throw new TabuleiroExcecao("Já há uma peça nessa posição " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		//É preciso informar que esta peça não está mais na posição 'nula'.
		peca.posicao = posicao;
	}
	
	public Peca removerPeca(Posicao posicao) {
		//Programação defensiva para verificar se há peça na posição.
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posição inexistente no tabuleiro.");
		}
		if (peca(posicao) == null) {
			return null;
		}
		Peca auxiliar = peca(posicao);
		auxiliar.posicao = null; //A peça foi 'retirada do tabuleiro', sendo representada pelo 'nulo'.
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return auxiliar;
	}
	
	//Método auxiliar ao 'Existe Posição'
	public boolean existePosicao(int linha, int coluna) {
		return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
	}
	//Método 'Existe Posição', valendo-se do método auxiliar definido acima.
	public boolean existePosicao(Posicao posicao) {
		return existePosicao(posicao.getLinha(),posicao.getColuna());
	}
	//Método 'Há uma peça'. Vale-se do método 'Peca', em sobrecarga, definido acima.
	public boolean haUmaPeca(Posicao posicao) {
		if (!existePosicao(posicao)) {
			throw new TabuleiroExcecao("Posição inexistente no tabuleiro.");
		}
		return peca(posicao) != null;
	}
}
