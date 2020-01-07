package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteDoAvaliador {
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
//		Scenario
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));
		
//		Action
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
//		Validation
		double maiorEsperado = 400;
		double menorEsperado = 200;
		
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("João");
		Leilao leilao =  new Leilao("Playstation");
		
		leilao.propoe(new Lance(joao, 1000.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		double maiorEsperado = 1000;
		double menorEsperado = 1000;
		
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(maria, 400.0));
		leilao.propoe(new Lance(joao, 500.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(3, maiores.size());
		assertEquals(500.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(400.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(2).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsMaioresLancesDentreMenosDeTres() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Cadeira de Praia");
		
		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(2, maiores.size());
		assertEquals(200.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(100.0, maiores.get(1).getValor(), 0.00001);
	}
	
	@Test
	public void deveEncontrarListaVaziaEmNenhumLance() {
		Leilao leilao = new Leilao("Guardanapo bordado");
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertEquals(0, maiores.size());
	}
	
	@Test
	public void deveCalcularMedia() {
//		Scenario
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));
		
//		Action
		Avaliador leiloeiro = new Avaliador();
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
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
//		Validation
		double mediaEsperada = 0;
		
		assertEquals(mediaEsperada, leiloeiro.getMedia(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComOrdemRandomica() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation");
		
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 450.0));
		leilao.propoe(new Lance(joao, 120.0));
		leilao.propoe(new Lance(maria, 700.0));
		leilao.propoe(new Lance(joao, 630.0));
		leilao.propoe(new Lance(maria, 230.0));
		
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(120.0, leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEntenderLeilaoComOrdemDecrescente() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Volkswagem Fox");
		
		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 100.0));
		
		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(100.0, leiloeiro.getMenorLance(), 0.00001);
	}
}
