package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

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
	
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Macbook Pro");
		
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new CriadorDeLeilao().para("Macbook Pro")
				.lance(steveJobs, 2000)
				.lance(billGates, 3000)
				.constroi();
		
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
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
