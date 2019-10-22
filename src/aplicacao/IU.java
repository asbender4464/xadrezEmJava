package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.Cor;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class IU {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	//Cores do TEXTO.
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	//Cores do FUNDO
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	//Método para limpar a tela.
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	//Método para ler uma posição do usuário.
	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		//Programação defensiva: bloco 'try-catch'
		try {
			String s = sc.nextLine();
			char coluna =s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro na leitura da Posição de Xadrez. Valores válidos são de 'a1' até 'h8'.");
		}
	}
	
	// Criando o Método 'imprimaTabuleiro'.
	public static void imprimaTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimaPeca(pecas[i][j]); // Chamada do método auxiliar, criado abaixo.
			}
			System.out.println(); // Para gerar uma quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}

	// Método auxiliar para imprimir 'uma' peça
	private static void imprimaPeca(PecaDeXadrez peca) {
    	if (peca == null) {
            System.out.print("-");
        }
        else {
            if (peca.getCor() == Cor.BRANCAS) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
    	System.out.print(" ");
	}
}
