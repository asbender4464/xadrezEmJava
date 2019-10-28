package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private int mudarJogador;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	private boolean xequeMate;
	private PecaDeXadrez vulneravelAoEnPassant;
	private PecaDeXadrez promovido;
	
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		mudarJogador = 1;
		jogadorAtual = Cor.BRANCAS;
		setupInicial();
	}
	
	public int getMudarJogador() {
		return mudarJogador;
	}
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	public boolean getXeque() {
		return xeque;
	}
	public boolean getXequeMate() {
		return xequeMate;
	}
		
	public PecaDeXadrez getVulneravelAoEnPassant() {
		return vulneravelAoEnPassant;
	}
	
	public PecaDeXadrez getPromovido() {
		return promovido;
	}
	
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
	public boolean[][] movimentosPossiveis (PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.paraPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaDeXadrez executarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		Posicao origem = origemPosicao.paraPosicao();
		Posicao destino = destinoPosicao.paraPosicao();
		validarOrigemPosicao(origem);
		validarDestinoPosicao(origem, destino);
		Peca pecaCapturada = fazerMovimento(origem, destino);
		if (testeXeque(jogadorAtual)) {
			desfazerMovimento(origem, destino, pecaCapturada);
			throw new XadrezExcecao("Tu não podes te colocar em xeque!");
		}
		PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);
		promovido = null;
		if (pecaMovida instanceof Peao) {
			if (pecaMovida.getCor() == Cor.BRANCAS && destino.getLinha() == 0 || pecaMovida.getCor() == Cor.PRETAS && destino.getLinha() == 7){
				promovido = (PecaDeXadrez)tabuleiro.peca(destino);
				promovido = substituirPecaPromovida("Q");
			}
		}
		xeque = (testeXeque(oponente(jogadorAtual))) ? true : false;
		if (testeXequeMate(oponente(jogadorAtual))) {
			xequeMate = true;
		}
		else {
		trocarDeJogador();
		}
		if (pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			vulneravelAoEnPassant = pecaMovida;
		}
		else {
			vulneravelAoEnPassant = null;
		}
		return (PecaDeXadrez)pecaCapturada;
	}

	public PecaDeXadrez substituirPecaPromovida(String tipo) {
		if (promovido == null) {
			throw new IllegalStateException("Não há peça para se fazer a promoção.");
		}
		if (!tipo.equals("B") &&!tipo.equals("C") &&!tipo.equals("T") &&!tipo.equals("Q")) {
			throw new InvalidParameterException("Tipo inválido para a promoção. Escolha entre 'B', 'C', 'T' ou 'Q'.");
		}
		Posicao pos = promovido.getPosicaoXadrez().paraPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasNoTabuleiro.remove(p);
		PecaDeXadrez novaPeca = novaPeca(tipo, promovido.getCor());
		tabuleiro.colocarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		return novaPeca;
	}
	
	private PecaDeXadrez novaPeca(String tipo, Cor cor) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cor);
		if (tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
	}
	
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem);
		p.incrementarContadorDeMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPeca(p, destino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre =(PecaDeXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.incrementarContadorDeMovimentos();
		}
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre =(PecaDeXadrez)tabuleiro.removerPeca(origemT);
			tabuleiro.colocarPeca(torre, destinoT);
			torre.incrementarContadorDeMovimentos();
		}
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao posicaoDoPeao;
				if (p.getCor() == Cor.BRANCAS) {
					posicaoDoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				}
				else {
					posicaoDoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removerPeca(posicaoDoPeao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}	
		return pecaCapturada;
	}
	
	private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(destino);
		p.decrementarContadorDeMovimentos();
		tabuleiro.colocarPeca(p, origem);
		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecaDeXadrez torre =(PecaDeXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decrementarContadorDeMovimentos();
		}
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecaDeXadrez torre =(PecaDeXadrez)tabuleiro.removerPeca(destinoT);
			tabuleiro.colocarPeca(torre, origemT);
			torre.decrementarContadorDeMovimentos();
		}
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == vulneravelAoEnPassant) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(destino);
				Posicao posicaoDoPeao;
				if (p.getCor() == Cor.BRANCAS) {
					posicaoDoPeao = new Posicao(3, destino.getColuna());
				}
				else {
					posicaoDoPeao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.colocarPeca(peao, posicaoDoPeao);
			}
		}
	}
	
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezExcecao("Não há peça na posição de origem.");
		}
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça escolhida não é sua.");
		}
		if (!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Não há movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode ser movida para a posição de destino.");
		}
	}
	
	private void trocarDeJogador() {
		mudarJogador++;
		jogadorAtual = (jogadorAtual == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS;
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe o Rei da cor " + cor + " no tabuleiro.");
	}
	
	private boolean testeXeque(Cor cor) {
		Posicao posicaoDoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasDoOponente = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasDoOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoDoRei.getLinha()][posicaoDoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeXequeMate(Cor cor) {
		if (!testeXeque(cor)) {
			return false;
		}
		List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			boolean[][] matriz = p.movimentosPossiveis();
			for (int i=0; i<tabuleiro.getLinhas(); i++) {
				for (int j=0; j<tabuleiro.getColunas(); j++) {
					if (matriz[i][j]) {
						Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMovimento(origem, destino);
						boolean testeXeque = testeXeque(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testeXeque) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}
	
	private void setupInicial() {
		colocarNovaPeca('a', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 1, new Rei(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('h', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('a', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('b', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('c', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('d', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('e', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('f', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('g', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		colocarNovaPeca('h', 2, new Peao(tabuleiro, Cor.BRANCAS, this));
		
		colocarNovaPeca('a', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('c', 8, new Bispo(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 8, new Rainha(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 8, new Rei(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('f', 8, new Bispo(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('h', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('a', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('b', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('c', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('d', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('e', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('f', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('g', 7, new Peao(tabuleiro, Cor.PRETAS, this));
		colocarNovaPeca('h', 7, new Peao(tabuleiro, Cor.PRETAS, this));
	}
}
