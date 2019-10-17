package aplicacao;

import xadrez.PecaDeXadrez;

public class IU {
	
	//Criando o M�todo 'imprimaTabuleiro'.
	public static void imprimaTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " ");
			for (int j=0; j<pecas.length ;j++) {
				imprimaPeca(pecas[i][j]); //Chamada do m�todo auxiliar, criado abaixo.
			}
			System.out.println(); //Para gerar uma quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}
	
	//M�todo auxiliar para imprimir 'uma' pe�a
	private static void imprimaPeca(PecaDeXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		}
		else {
			System.out.print(peca);
		}
		System.out.print(" "); //Espa�o em branco para que as pe�as n�o fiquem grudadas
	}
}
