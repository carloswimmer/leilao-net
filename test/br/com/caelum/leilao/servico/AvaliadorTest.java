package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTest {
	
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
	
	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Playstation 3 Novo")
				.constroi();
		
		leiloeiro.avalia(leilao);
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
		
		assertThat(leiloeiro.getMaiorLance(), equalTo(maiorEsperado));
		assertThat(leiloeiro.getMenorLance(), equalTo(menorEsperado));
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao =  new CriadorDeLeilao().para("Playstation")
				.lance(joao, 1000.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		assertThat(leiloeiro.getMaiorLance(), equalTo(leiloeiro.getMenorLance()));
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
		
		assertThat(maiores.size(), equalTo(3));
		assertThat(maiores, hasItems(
				new Lance(joao, 500),
				new Lance(maria, 400),
				new Lance(joao, 300)
		));
	}
	
	@Test
	public void deveEncontrarOsMaioresLancesDentreMenosDeTres() {
		Leilao leilao = new CriadorDeLeilao().para("Cadeira de Praia")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertThat(maiores.size(), equalTo(2));
		assertThat(maiores, hasItems(
				new Lance(joao, 100),
				new Lance(maria, 200)
		));
	}
	
	@Test(expected = RuntimeException.class)
	public void deveEncontrarListaVaziaEmNenhumLance() {
		Leilao leilao = new Leilao("Guardanapo bordado");
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertThat(maiores.size(), equalTo(0));
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
		
		assertThat(leiloeiro.getMedia(), equalTo(mediaEsperada));
	}

	@Test(expected = RuntimeException.class)
	public void deveCalcularMediaSemLances() {
//		Scenario
		Leilao leilao = new Leilao("Playstation 1");
		
//		Action
		leiloeiro.avalia(leilao);
		
//		Validation
		double mediaEsperada = 0;
		
		assertThat(leiloeiro.getMedia(), equalTo(mediaEsperada));
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
		
		assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
		assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
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
		
		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
		assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
	}
}
