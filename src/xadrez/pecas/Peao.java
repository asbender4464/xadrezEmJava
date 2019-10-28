package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez {

	private PartidaDeXadrez partidaDeXadrez; //Necess�rio para que o Pe�o acesse a Classe Partida de Xadrez, dado o 'En Passant'.
	
	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
	}

	//M�todo para implementar os movimentos poss�veis de um pe�o.
	@Override
	public boolean[][] movimentosPossiveis() {
		//Criando uma matriz auxiliar do tipo 'boolean' com a 'mesma dimens�o' do tabuleiro.
		//Por defini��o, todas as posi��es desta matriz come�am com o status 'false'.
		boolean [][] matriz = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		//PE�O BRANCO: movimentos 'para frente' e movimentos 'diagonais'.
		
		//Movimentos para frente.
		if (getCor() == Cor.BRANCAS) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorDeMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Movimentos diagonais. S� se houver pe�as dos advers�rio nas casas poss�veis de destino. Neste caso, haver� captura.
			
			//Diagonal � 'esquerda'.
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Diagonal � 'direita'.
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Movimento especial: 'En Passant', Pe�o Branco.
			if (posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && haUmaPecaDoOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadrez.getVulneravelAoEnPassant()) {
					matriz[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && haUmaPecaDoOponente(direita) && getTabuleiro().peca(direita) == partidaDeXadrez.getVulneravelAoEnPassant()) {
					matriz[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		}
		else {
			
			//PE�O PRETO: movimentos 'para frente' e movimentos 'diagonais'.
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorDeMovimentos() == 0) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Movimentos diagonais. S� se houver pe�as dos advers�rio nas casas poss�veis de destino. Neste caso, haver� captura.
			
			//Diagonal � 'esquerda'.
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			
			//Diagonal � 'direita'.
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().existePosicao(p) && haUmaPecaDoOponente(p)) {
				matriz[p.getLinha()][p.getColuna()] = true;
			}
			//Movimento especial: 'En Passant', Pe�o Preto.
			if (posicao.getLinha() == 4) {
			Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().existePosicao(esquerda) && haUmaPecaDoOponente(esquerda) && getTabuleiro().peca(esquerda) == partidaDeXadrez.getVulneravelAoEnPassant()) {
					matriz[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().existePosicao(direita) && haUmaPecaDoOponente(direita) && getTabuleiro().peca(direita) == partidaDeXadrez.getVulneravelAoEnPassant()) {
					matriz[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		return matriz;
	}
	@Override
	public String toString() {
		return "P";
	}
}
