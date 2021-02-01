package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Test;

public class TesteLeilao {

  @Test
  public void deveReceberUmLance() {
    Leilao leilao = new Leilao("Macbook Pro");
    assertEquals(0, leilao.getLances().size());

    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 2000));
    assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
  }

  @Test
  public void deveReceberVariosLances() {
    Leilao leilao = new Leilao("Macbook Pro");
    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 2000));
    leilao.propoe(new Lance(new Usuario("Pedro Henrique"), 3000));

    assertEquals(2, leilao.getLances().size());
    assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
    assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
  }

  @Test
  public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
    Leilao leilao = new Leilao("Macbook Pro");
    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 2000));
    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 3000));

    assertEquals(1, leilao.getLances().size());
    assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
  }

  @Test
  public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
    Leilao leilao = new Leilao("Macbook Pro");
    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 2000));
    leilao.propoe(new Lance(new Usuario("Pedro Henrique"), 3000));

    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 4000));
    leilao.propoe(new Lance(new Usuario("Pedro Henrique"), 5000));

    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 6000));
    leilao.propoe(new Lance(new Usuario("Pedro Henrique"), 7000));

    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 8000));
    leilao.propoe(new Lance(new Usuario("Pedro Henrique"), 9000));

    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 10000));
    leilao.propoe(new Lance(new Usuario("Pedro Henrique"), 11000));

    leilao.propoe(new Lance(new Usuario("Gabriel Lucas"), 10000));

    assertEquals(10, leilao.getLances().size());
    assertEquals(11000, leilao.getLances().get(leilao.getLances().size()-1).getValor(), 0.00001);

  }

}
