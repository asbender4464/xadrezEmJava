package aplicacao;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		while(true) { //Neste momento não há programação de cheque-mate e o programa ficará em 'loop', pelo parâmetro 'true'.
		//IU = Interface do usuário, uma Classe.
			IU.imprimaTabuleiro(partidaDeXadrez.getPecas());
			System.out.println();
			System.out.print("Origem: ");
			PosicaoXadrez origem = IU.lerPosicaoXadrez(sc);
			
			System.out.println();
			System.out.print("Destino: ");
			PosicaoXadrez destino = IU.lerPosicaoXadrez(sc);
			
			PecaDeXadrez pecaCapturada = partidaDeXadrez.executarMovimentoXadrez(origem, destino);
		}
	}
}
