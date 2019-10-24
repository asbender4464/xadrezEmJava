package xadrez;

import tabuleiro.Posicao;

public class PosicaoXadrez {

	//Atributos
	private char coluna;
	private int linha;
	
	//Construtor com argumentos
	public PosicaoXadrez(char coluna, int linha) {
		//Programa��o defensiva: o intevalo de coluna tem que estar entre 'a' e 'h', e linhas entre '1' e '8'.
		//Nota: caracteres tamb�m aceitam compara��es de '>' e '<'. Veja abaixo.
		if (coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExcecao("Erro ao 'instanciar' a Posi��o no Xadrez. Valores v�lidos s�o de 'a1' atpe 'h8'.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}

	//GETs e SETs
	public char getColuna() {
		return coluna;
	}
	
	//Programa��o defensiva: linhas e colunas n�o poder�o ser livremente alteradas. Campo Comentado.
	//public void setColuna(char coluna) {
	//	this.coluna = coluna;
	//}

	public int getLinha() {
		return linha;
	}

	//Programa��o defensiva: linhas e colunas n�o poder�o ser livremente alteradas. Campo Comentado.
	//public void setLinha(int linha) {
	//	this.linha = linha;
	//}
	
	//M�todo "Para Posi��o"
	//Nota 1: lembre-se que as matrizes come�am na posi��o '0'.
	//Nota 2: � poss�vel fazer opera��es matem�ticas com caracteres 'unicode'. Por exemplo: ('a' - 'a' = 0( ou ('b' - 'a' = 1).
	//Isso permite definir uma 'regra geral', codificada abaixo.
	protected Posicao paraPosicao () {
		return new Posicao(8 - linha, coluna - 'a');
	}
	
	//M�todo "Da Posi��o", que � a opera��o inversa do m�todo acima.
	//� preciso fazer um 'casting' para 'char', pois a convers�o n�o � autom�tica.
	protected static PosicaoXadrez daPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a'+ posicao.getColuna()), 8 - posicao.getLinha());
	}
	//Fazendo o 'toString' da posi��o.
	//Nota 1: a nota��o do xadrez � 'coluna e linha', e n�o a usual, matricial, 'linha e coluna'.
	//Nota 2: � preciso usar o string "" no 'return', para que o complilador entenda que � uma concatena��o. Se n�o usar, haver� erro.
	@Override
	public String toString () {
		return "" + coluna + linha;
	}
}
