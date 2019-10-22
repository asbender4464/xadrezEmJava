package tabuleiro;

public abstract class Peca {
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
	
	//Método(operação) para definir movimentos possíveis
	public abstract boolean[][] movimentosPossiveis();
	
	//Método(operação) para definir um movimento possível. Este é um método 'concreto' que está usando um método 'abstrato' (acima).
	//Esta técnica também é conhecida como 'hook method'.
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	//Método(operação) para ver se há pelo menos um movimento possível para determinada peça.
	//Esta é outra operação 'padrão' que depende de 'método abstrato' (movimentosPossiveis).
	public boolean haAlgumMovimentoPossivel() {
		boolean[][] matriz = movimentosPossiveis(); //Uso da matriz 'auxiliar' 'MATRIZ'.
		for (int i=0; i<matriz.length; i++) {
			for (int j=0; j<matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
