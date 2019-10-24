package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {

	//Atributos
	private char coluna;
	private int linha;
	
	//Construtor com argumentos
	public PosicaoXadrez(char coluna, int linha) {
		//Programação defensiva: o intevalo de coluna tem que estar entre 'a' e 'h', e linhas entre '1' e '8'.
		//Nota: caracteres também aceitam comparações de '>' e '<'. Veja abaixo.
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro ao 'instanciar' a Posição no Xadrez. Valores válidos são de 'a1' atpe 'h8'.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	//GETs e SETs
	public char getColuna() {
		return coluna;
	}
	
	//Programação defensiva: linhas e colunas não poderão ser livremente alteradas. Campo Comentado.
	//public void setColuna(char coluna) {
	//	this.coluna = coluna;
	//}

	public int getLinha() {
		return linha;
	}

	//Programação defensiva: linhas e colunas não poderão ser livremente alteradas. Campo Comentado.
	//public void setLinha(int linha) {
	//	this.linha = linha;
	//}
	
	//Método "Para Posição"
	//Nota 1: lembre-se que as matrizes começam na posição '0'.
	//Nota 2: É possível fazer operações matemáticas com caracteres 'unicode'. Por exemplo: ('a' - 'a' = 0( ou ('b' - 'a' = 1).
	//Isso permite definir uma 'regra geral', codificada abaixo.
	protected Posicao paraPosicao () {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	//Método "Da Posição", que é a operação inversa do método acima.
	//É preciso fazer um 'casting' para 'char', pois a conversão não é automática.
	protected static PosicaoXadrez daPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a'+ posicao.getColuna()), 8 - posicao.getLinha());
	}
	//Fazendo o 'toString' da posição.
	//Nota 1: a notação do xadrez é 'coluna e linha', e não a usual, matricial, 'linha e coluna'.
	//Nota 2: é preciso usar o string "" no 'return', para que o complilador entenda que é uma concatenação. Se não usar, haverá erro.
	@Override
	public String toString () {
		return "" + coluna + linha;
	}
}
