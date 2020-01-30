package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;

public class LeilaoTest {
	
	private Usuario steveJobs;
	private Usuario billGates;

	@Before
	public void setUp() {
		steveJobs = new Usuario("Steve Jobs");
		billGates = new Usuario("Bill Gates");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAceitarLancesDeValorIgualAZero() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Macbook Pro")
				.lance(billGates, 0)
				.constroi();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveAceitarLancesComValorNegativo() {
		Leilao leilao = new CriadorDeLeilao()
				.para("Macbook Pro")
				.lance(billGates, -100)
				.constroi();
	}
	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Macbook Pro");
		
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
		
		assertEquals(1, leilao.getLances().size());
		assertThat(leilao.getLances(), hasItem(new Lance(steveJobs, 2000)));
	}
	
	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro")
				.lance(steveJobs, 2000)
				.lance(billGates, 3000)
				.constroi();
		
		assertEquals(2, leilao.getLances().size());
		assertThat(leilao.getLances(), hasItems(
				new Lance(steveJobs, 2000),
				new Lance(billGates, 3000)
		));
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro")
				.lance(steveJobs, 2000)
				.lance(steveJobs, 3000)
				.constroi();
		
		leilao.dobraLance(steveJobs);
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarMaisDe5LancesDeUmMesmoUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro")
				.lance(steveJobs, 2000.0)
				.lance(billGates, 3000.0)
				.lance(steveJobs, 4000.0)
				.lance(billGates, 5000.0)
				.lance(steveJobs, 6000.0)
				.lance(billGates, 7000.0)
				.lance(steveJobs, 8000.0)
				.lance(billGates, 9000.0)
				.lance(steveJobs, 10000.0)
				.lance(billGates, 11000.0)
				.lance(steveJobs, 12000.0)
				.constroi();
				
		leilao.dobraLance(steveJobs);
		
		assertEquals(10, leilao.getLances().size());
		assertEquals(11000, leilao.getLances()
				.get(leilao.getLances().size()-1).getValor(), 0.00001);
	}
	
	@Test
	public void deveDobrarUltimoLanceDeUmUsuario() {
		Leilao leilao = new CriadorDeLeilao().para("Camera GoPro")
				.lance(steveJobs, 2000.0)
				.lance(billGates, 3000.0)
				.lance(steveJobs, 4000.0)
				.lance(billGates, 5000.0)
				.constroi();
		
		leilao.dobraLance(steveJobs);
		
		assertEquals(5, leilao.getLances().size());
		assertEquals(8000, leilao.getLances()
				.get(leilao.getLances().size()-1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveDobrarSemLanceAnterior() {
		Leilao leilao = new Leilao("Camera GoPro");
				
		leilao.dobraLance(steveJobs);
		
		assertEquals(0, leilao.getLances().size());
	}
}
