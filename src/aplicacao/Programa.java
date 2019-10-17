package aplicacao;

import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {

		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		//IU = Interface do usuário, uma Classe.
		IU.imprimaTabuleiro(partidaDeXadrez.getPecas());
	}
}
