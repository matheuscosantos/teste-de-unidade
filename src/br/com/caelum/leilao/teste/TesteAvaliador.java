package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.service.Avaliador;
import java.text.DecimalFormat;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TesteAvaliador {

  private Avaliador leiloeiro = new Avaliador();
  private Usuario joao;
  private Usuario maria;
  private Usuario jose;

  @Before // cria esse método antes do meu teste
  public void criaAvaliador() {
    this.leiloeiro = new Avaliador();
    this.joao = new Usuario("João");
    this.jose = new Usuario("José");
    this.maria = new Usuario("Maria");
  }

  @Test(expected = RuntimeException.class) // o teste só passa se a exceção for lançada
  public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
    Leilao leilao = new CriadorDeLeilao().para("Playstation 3").constroi();
    leiloeiro.avalia(leilao);

  }

  @Test
  public void deveEntenderLancesEmOrdemCrescente() {
    // cenário


    Leilao leilao = new Leilao("Playstation 3");
    leilao.propoe(new Lance(joao, 250.0));
    leilao.propoe(new Lance(jose, 300.0));
    leilao.propoe(new Lance(maria, 400.0));

    // ação
    leiloeiro.avalia(leilao);

    // retorno do método/validação
    MatcherAssert.assertThat(leiloeiro.getMaiorDeTodos(), equalTo(400.0));
    MatcherAssert.assertThat(leiloeiro.getMenorDeTodos(), equalTo(250.0));
  }

  @Test
  public void deveEntenderLeilaoComApenasUmLance() {

    Leilao leilao = new Leilao("Playstation 3");
    leilao.propoe(new Lance(joao, 1000.00));

    leiloeiro.avalia(leilao);

    assertEquals(1000.0, leiloeiro.getMaiorDeTodos(), 0.00001);
    assertEquals(1000.0, leiloeiro.getMenorDeTodos(), 0.00001);
  }

  @Test
  public void deveEncontrarOsTresMaioresLances() {
    Leilao leilao = new CriadorDeLeilao().para("Playstation 3")
        .lance(joao, 100.0)
        .lance(maria, 200.0)
        .lance(joao, 300.0)
        .lance(maria, 400.0)
        .constroi();


    leiloeiro.avalia(leilao);

    List<Lance> maiores = leiloeiro.getTresMaiores();
    assertEquals(3, maiores.size());
    MatcherAssert.assertThat(maiores, hasItems(
        new Lance(maria, 400),
        new Lance(joao, 300),
        new Lance(maria, 200)
    ));

  }

  @Test
  public void deveRetornarMediaDosLances() {

    Leilao leilao = new Leilao("Playstation 3");
    leilao.propoe(new Lance(joao, 500.0));
    leilao.propoe(new Lance(jose, 300.0));
    leilao.propoe(new Lance(maria, 400.0));

    double mediaEsperada = 400.0;
    DecimalFormat df = new DecimalFormat("###.##");
    assertEquals(mediaEsperada,
        Double.parseDouble(df.format(new Avaliador().mediaDosLances(leilao))),
        0.00001);
  }

}
