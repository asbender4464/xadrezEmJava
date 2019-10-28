package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {
	
	/*
	 * Abaixo iniciar� a prepara��o para a Jogada ROQUE, grande (ou Roque do lado da Rainha) e pequeno (ou Roque do lado do Rei).
	 * Para tanto, a pe�a REI, diferentemente das outras, dever� ter um acesso � Classe X, conforme o diagrama UML.
	 * Condi��es para se fazer um ROQUE: (1) O Rei N�O pode estar em xeque; e (2) As casas entre o Rei e a Torre precisam estar
	 * desocupadas; e (3) Ambas as pe�as N�O podem ter sido movidas anteriormente.
	 */
	
	private PartidaDeXadrez partidaDeXadrez;
	
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}
	@Override
	public String toString() {
		return "K";
		//Esta letra 'K', de 'King', substituir� o '-' no tabuleiro.
	}
	
	//M�todo auxiliar 'pode mover'. Ele verificar� se o Rei poder� se mover para certa posi��o.
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor(); //Testa se a casa est� 'vazia' ou se h� 'pe�a do advers�rio' (cor diferente).
	}
	
	//M�todo auxiliar para testar a 'condi��o' de Roque.
	private boolean testePossibilidadeRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorDeMovimentos() == 0;
	}
	
	
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz do tipo 'boolean' com a 'mesma dimens�o' do tabuleiro.
		//Por defini��o, todas as posi��es desta matriz come�am com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0); //Cria��o de posi��o 'auxiliar'.
		
		//Testando as 8 posi��es poss�veis para o movimento de um Rei.
		
		//Marcando como 'true' as posi��es 'ACIMA' da posi��o da Rei.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es 'ABAIXO' da posi��o da Rei.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� ESQUERDA' da posi��o da Rei.
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� DIREITA' da posi��o da Rei.
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� NOROESTE' da posi��o da Rei.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� NORDESTE' da posi��o da Rei.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� SUDOESTE' da posi��o da Rei.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() -1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posi��es '� SUDESTE' da posi��o da Rei.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Verificando se o Rei tem possibilidade de fazer o Roque (grande e pequeno).
		if (getContadorDeMovimentos() == 0 && !partidaDeXadrez.getXeque()) {
			//Roque pequeno.
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testePossibilidadeRoque(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			//Roque grande.
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testePossibilidadeRoque(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2) == null && getTabuleiro().peca(p3) == null) {
					matriz[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}
		}
		
		
		return matriz;
	}
}
