package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	//Esta Classe é o 'coração' do sistema de xadrez.
	//Aqui serão colocadas as regras do jogo.
	
	private int mudarJogador;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
	//Construtor padrão
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		mudarJogador = 1;
		jogadorAtual = Cor.BRANCAS;
		//Chamando o Setup Inicial, para começar a partida, colocando as peças no tabuleiro.
		setupInicial();
	}
	
	//GETs
	public int getMudarJogador() {
		return mudarJogador;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}

	//Método
	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] matriz = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j); //Há um 'downcast'aqui.
			}
		}
		return matriz;
	}
	
	//Método para identificar movimentos possíveis a partir de uma posição de origem.
	public boolean[][] movimentosPossiveis (PosicaoXadrez origemPosicao) {
		Posicao posicao = origemPosicao.paraPosicao();
		validarOrigemPosicao(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	//Método para executar movimento de Xadrez.
	public PecaDeXadrez executarMovimentoXadrez(PosicaoXadrez origemPosicao, PosicaoXadrez destinoPosicao) {
		//Convertendo posições recebidas para posição da matriz.
		Posicao origem = origemPosicao.paraPosicao();
		Posicao destino = destinoPosicao.paraPosicao();
		validarOrigemPosicao(origem); //Operação para verificar se há peça na origem definida.
		validarDestinoPosicao(origem, destino); //Operação para verificar se a posição de destino é válida.
		Peca pecaCapturada = fazerMovimento(origem, destino);
		trocarDeJogador(); //Chama o método, definido abaixo, para trocar de 'player'.
		return (PecaDeXadrez)pecaCapturada; //Downcasting para 'PecaDeXadrez', porque a peca capturada era do tipo Peca.
	}
	
	//Método/operação 'fazerMovimento', usada acima.
	private Peca fazerMovimento(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem); //Colocando a peça a ser movida na variável 'p'.
		Peca pecaCapturada = tabuleiro.removerPeca(destino); //Remover a possível peça que esteja na posição de destino.
		tabuleiro.colocarPeca(p, destino); //Colocando a peça 'p' em seu destino.
		return pecaCapturada;
	}
	
	//Método/operação para 'validar' a origem da posição, usada no método acima, bem como verificar se há movimentos possíveis
	//para uma peça.
	private void validarOrigemPosicao(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new XadrezExcecao("Não há peça na posição de origem.");
		}
		//Verificar se o jogador 'atual' é o mesmo da 'peça escolhida' para ser movimentada.
		if (jogadorAtual != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça escolhida não é sua.");
		}
		//Verificando se há movimentos possíveis para uma determinada peça.
		if (!tabuleiro.peca(posicao).haAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Não há movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarDestinoPosicao(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new XadrezExcecao("A peça escolhida não pode ser movida para a posição de destino.");
		}
	}
	
	//Método para 'trocar de jogador'.
	private void trocarDeJogador() {
		mudarJogador++;
		jogadorAtual = (jogadorAtual == Cor.BRANCAS) ? Cor.PRETAS : Cor.BRANCAS; //Expressão condicional 'ternária'.
	}
	
	//Método que usa as 'coordenadas do Xadrez' (coluna e linha), e NÃO as 'matriciais' (linha e coluna).
	private void colocarNovaPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}
	
	//Método Setup Inicial. É responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro.
	private void setupInicial() {
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCAS));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCAS));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETAS));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETAS));
	}
}
