package aplicacao;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExcecao;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while(true) { //Neste momento n�o h� programa��o de cheque-mate e o programa ficar� em 'loop', pelo par�metro 'true'.
			try {
				//Chamando o m�todo de limpeza de tabuleiro antes de imprimi-lo.
				IU.clearScreen();
				
				//IU = Interface do usu�rio, uma Classe.
				IU.imprimaPartida(partidaDeXadrez);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
				
				boolean[][] movimentosPossiveis = partidaDeXadrez.movimentosPossiveis(origem);
				IU.clearScreen();
				IU.imprimaTabuleiro(partidaDeXadrez.getPecas(), movimentosPossiveis);
				//Acima, 'sobrecarga' do m�todo 'imprimaTabuleiro', colorindo o tabuleiro para mostrar as
				//posi��es poss�veis para onde uma pe�a poder� se mover.
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
				
				PecaDeXadrez pecaCapturada = partidaDeXadrez.executarMovimentoXadrez(origem, destino);
			}
			catch(XadrezExcecao e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //Esta linha faz o programa esperar at� o usu�rio teclar ENTER.
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //Esta linha faz o programa esperar at� o usu�rio teclar ENTER.
			}
		}
	}
}
