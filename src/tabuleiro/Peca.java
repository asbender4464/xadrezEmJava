package tabuleiro;

public abstract class Peca {
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
	
	//M�todo(opera��o) para definir movimentos poss�veis
	public abstract boolean[][] movimentosPossiveis();
	
	//M�todo(opera��o) para definir um movimento poss�vel. Este � um m�todo 'concreto' que est� usando um m�todo 'abstrato' (acima).
	//Esta t�cnica tamb�m � conhecida como 'hook method'.
	public boolean movimentoPossivel(Posicao posicao) {
		return movimentosPossiveis()[posicao.getLinha()][posicao.getColuna()];
	}
	
	//M�todo(opera��o) para ver se h� pelo menos um movimento poss�vel para determinada pe�a.
	//Esta � outra opera��o 'padr�o' que depende de 'm�todo abstrato' (movimentosPossiveis).
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
