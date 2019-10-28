package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez {
	
	/*
	 * Abaixo iniciará a preparação para a Jogada ROQUE, grande (ou Roque do lado da Rainha) e pequeno (ou Roque do lado do Rei).
	 * Para tanto, a peça REI, diferentemente das outras, deverá ter um acesso à Classe X, conforme o diagrama UML.
	 * Condições para se fazer um ROQUE: (1) O Rei NÃO pode estar em xeque; e (2) As casas entre o Rei e a Torre precisam estar
	 * desocupadas; e (3) Ambas as peças NÃO podem ter sido movidas anteriormente.
	 */
	
	private PartidaDeXadrez partidaDeXadrez;
	
	public Rei(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}
	@Override
	public String toString() {
		return "K";
		//Esta letra 'K', de 'King', substituirá o '-' no tabuleiro.
	}
	
	//Método auxiliar 'pode mover'. Ele verificará se o Rei poderá se mover para certa posição.
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p == null || p.getCor() != getCor(); //Testa se a casa está 'vazia' ou se há 'peça do adversário' (cor diferente).
	}
	
	//Método auxiliar para testar a 'condição' de Roque.
	private boolean testePossibilidadeRoque(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContadorDeMovimentos() == 0;
	}
	
	
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz do tipo 'boolean' com a 'mesma dimensão' do tabuleiro.
		//Por definição, todas as posições desta matriz começam com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0); //Criação de posição 'auxiliar'.
		
		//Testando as 8 posições possíveis para o movimento de um Rei.
		
		//Marcando como 'true' as posições 'ACIMA' da posição da Rei.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'ABAIXO' da posição da Rei.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À ESQUERDA' da posição da Rei.
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À DIREITA' da posição da Rei.
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À NOROESTE' da posição da Rei.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À NORDESTE' da posição da Rei.
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À SUDOESTE' da posição da Rei.
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() -1);
		if (getTabuleiro().existePosicao(p) && podeMover(p)) {
			matriz[p.getLinha()][p.getColuna()] = true;
		}
		
		//Marcando como 'true' as posições 'À SUDESTE' da posição da Rei.
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
