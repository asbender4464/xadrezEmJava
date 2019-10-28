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

	/*
	 * Projeto Jogo de Xadrez em Java - Vers�o FINAL - 28/Outubro/2019.
	 * Desenvolvido no Curso JavaCompleto 2019 - Programa��o Orientada a Objetivos
	 * Prof. Dr. Nelio Alves
	 * 
	 * Nota do Aluno Andr� Silva Bender: optei por desenvolver o programa em L�ngua Portuguesa (nomes de atributos,
	 * m�todos, vari�veis, etc.) e registrar todos os coment�rios feitos pelo Professor ao longo da constru��o do projeto.
	 * Espero, deste modo, ajudar outros colegas em seus estudos. 
	 */
	
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		List<PecaDeXadrez> capturadas = new ArrayList<>();
		
		while(!partidaDeXadrez.getXequeMate()) { //O programa rodar� at� que haja um Xeque-Mate.
			try {
				//Chamando o m�todo de limpeza de tabuleiro antes de imprimi-lo.
				IU.clearScreen();
				
				//IU = Interface do usu�rio, uma Classe.
				IU.imprimaPartida(partidaDeXadrez, capturadas);
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
				
				//Condi��o para inserir uma 'pe�a capturada' na lista de 'pe�as capturadas'.
				if (pecaCapturada != null) {
					capturadas.add(pecaCapturada);
				}
				
				if (partidaDeXadrez.getPromovido() != null) {
					System.out.println("Escolha uma pe�a para a promo��o de seu Pe�o (B, C, T ou Q): ");
					String tipo = sc.nextLine();
					partidaDeXadrez.substituirPecaPromovida(tipo);
				}
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
		//Havendo o 'xeque-mate', o la�o 'while' termina, limpa-se a tela e imprime-se o tabuleiro em sua forma final.
		IU.clearScreen();
		IU.imprimaPartida(partidaDeXadrez, capturadas);
	}
}
