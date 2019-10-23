package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.PartidaDeXadrez;
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

	//M�todo para limpar a tela.
	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	//M�todo para ler uma posi��o do usu�rio.
	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		//Programa��o defensiva: bloco 'try-catch'
		try {
			String s = sc.nextLine();
			char coluna =s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch (RuntimeException e) {
			throw new InputMismatchException("Erro na leitura da Posi��o de Xadrez. Valores v�lidos s�o de 'a1' at� 'h8'.");
		}
	}
	
	//M�todo 'imprima partida'.
	public static void imprimaPartida(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> capturadas) {
		imprimaTabuleiro(partidaDeXadrez.getPecas());
		System.out.println();
		imprimaPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Rodada: " + partidaDeXadrez.getMudarJogador());
		System.out.println("Aguardando o movimento do jogador " + partidaDeXadrez.getJogadorAtual());
	}
	
	//Criando o M�todo 'imprimaTabuleiro'.
	public static void imprimaTabuleiro(PecaDeXadrez[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimaPeca(pecas[i][j], false); // Chamada do m�todo auxiliar, criado abaixo.
			}
			System.out.println(); // Para gerar uma quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}

	// Criando o M�todo 'imprimaTabuleiro', com as posi��es poss�veis coloridas (marcadas).
	public static void imprimaTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				imprimaPeca(pecas[i][j], movimentosPossiveis[i][j]); // Chamada do m�todo auxiliar, criado abaixo.
			}
			System.out.println(); // Para gerar uma quebra de linha.
		}
		System.out.println("  a b c d e f g h");
	}
	
	// M�todo auxiliar para imprimir 'uma' pe�a
	private static void imprimaPeca(PecaDeXadrez peca, boolean fundo) {
    	if (fundo) {
    		System.out.print(ANSI_BLUE_BACKGROUND);
    	}
		if (peca == null) {
            System.out.print("-" + ANSI_RESET);
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
	
	//M�todo paraimprimir na tela a lista de 'pe�as capturadas'.
	private static void imprimaPecasCapturadas(List<PecaDeXadrez> capturadas) {
		//Usando uma 'express�o lambda' para listar todas as pe�as 'brancas' da lista de 'pe�as capturadas'.
		List<PecaDeXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCAS).collect(Collectors.toList());
		//Fazendo a mesma coisa para as pe�as 'pretas'.
		List<PecaDeXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cor.PRETAS).collect(Collectors.toList());
		//Abaixo, a l�gica para imprimir as listas na tela.
		System.out.println("PE�AS CAPTURADAS:");
		System.out.print("BRANCAS: ");
		System.out.print(ANSI_WHITE); //Iniciar impress�o em 'branco'.
		System.out.println(Arrays.toString(brancas.toArray())); //Recurso do Java para imprimir listas.
		System.out.print(ANSI_RESET); //Fazer 'reset' nas cores.
		System.out.print("PRETAS: ");
		System.out.print(ANSI_YELLOW); //Iniciar impress�o em 'amarelo', para contrastar com o fundo preto.
		System.out.println(Arrays.toString(pretas.toArray())); //Recurso do Java para imprimir listas.
		System.out.print(ANSI_RESET); //Fazer 'reset' nas cores.
	}
}
