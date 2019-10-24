package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturadas = new ArrayList<>();
		
		while(!partidaDeXadrez.getXequeMate()) { //O programa rodará até que haja um Xeque-Mate.
			try {
				//Chamando o método de limpeza de tabuleiro antes de imprimi-lo.
				IU.clearScreen();
				
				//IU = Interface do usuário, uma Classe.
				IU.imprimaPartida(partidaDeXadrez, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
				IU.clearScreen();
				IU.imprimaTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);
				//Acima, 'sobrecarga' do método 'imprimaTabuleiro', colorindo o tabuleiro para mostrar as
				//posições possíveis para onde uma peça poderá se mover.
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
				
				PecaDeXadrez pecaCapturada = partidaDeXadrez.executarMovimentoXadrez(origem, destino);
				
				//Condição para inserir uma 'peça capturada' na lista de 'peças capturadas'.
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
			}
			catch(XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //Esta linha faz o programa esperar até o usuário teclar ENTER.
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //Esta linha faz o programa esperar até o usuário teclar ENTER.
			}
		}
		//Havendo o 'xeque-mate', o laço 'while' termina, limpa-se a tela e imprime-se o tabuleiro em sua forma final.
		IU.clearScreen();
		IU.imprimaPartida(partidaDeXadrez, capturadas);
	}
}
