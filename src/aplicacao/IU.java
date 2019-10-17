package aplicacao;

import xadrez.PecaDeXadrez;

public class IU {
	
	//Criando o Método 'imprimaTabuleiro'.
	public static void imprimaTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " ");
			for (int j=0; j<pecas.length ;j++) {
				imprimaPeca(pecas[i][j]); //Chamada do método auxiliar, criado abaixo.
			}
			System.out.println(); //Para gerar uma quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}
	
	//Método auxiliar para imprimir 'uma' peça
	private static void imprimaPeca(PecaDeXadrez peca) {
		if (peca == null) {
			System.out.print("-");
		}
		else {
			System.out.print(peca);
		}
		System.out.print(" "); //Espaço em branco para que as peças não fiquem grudadas
	}
}
