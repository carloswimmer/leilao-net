package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTeste {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void criaCenario() {
		leiloeiro = new Avaliador();
		joao = new Usuario("João");
		jose = new Usuario("José");
		maria = new Usuario("Maria");
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
//		Scenario
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 200.0)
				.lance(jose, 300.0)
				.lance(maria, 400.0)
				.constroi();
		
//		Action
		leiloeiro.avalia(leilao);
		
//		Validation
		double maiorEsperado = 400;
		double menorEsperado = 200;
		
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao =  new CriadorDeLeilao().para("Playstation")
				.lance(joao, 1000.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		double maiorEsperado = 1000;
		double menorEsperado = 1000;
		
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.lance(joao, 500.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(3, maiores.size());
		assertEquals(500.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(400.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(2).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsMaioresLancesDentreMenosDeTres() {
		Leilao leilao = new CriadorDeLeilao().para("Cadeira de Praia")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(2, maiores.size());
		assertEquals(200.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(100.0, maiores.get(1).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarListaVaziaEmNenhumLance() {
		Leilao leilao = new Leilao("Guardanapo bordado");
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(0, maiores.size());
	}
	
	@Test
	public void deveCalcularMedia() {
//		Scenario
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 200.0)
				.lance(jose, 300.0)
				.lance(maria, 400.0)
				.constroi();
		
//		Action
		leiloeiro.avalia(leilao);
		
//		Validation
		double mediaEsperada = 300;
		
		assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.00001);
	}

	@Test
	public void deveCalcularMediaSemLances() {
//		Scenario
		Leilao leilao = new Leilao("Playstation 1");
		
//		Action
		leiloeiro.avalia(leilao);
		
//		Validation
		double mediaEsperada = 0;
		
		assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComOrdemRandomica() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation")
				.lance(joao, 200.0)
				.lance(maria, 450.0)
				.lance(joao, 120.0)
				.lance(maria, 700.0)
				.lance(joao, 630.0)
				.lance(maria, 230.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(120.0, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComOrdemDecrescente() {
		Leilao leilao = new CriadorDeLeilao().para("Volkswagem Fox")
				.lance(joao, 400.0)
				.lance(maria, 300.0)
				.lance(joao, 200.0)
				.lance(maria, 100.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(100.0, leiloeiro.getMenorLance(), 0.00001);
	}
}
