package br.com.caelum.leilao.service;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {

  private double maiorDeTodos = Double.NEGATIVE_INFINITY; // constante que guarda o menor número dentro do Double

  private double menorDeTodos = Double.POSITIVE_INFINITY;
  private List<Lance> maiores;

  public void avalia(Leilao leilao) {
    maiores = new ArrayList<>();

    if (leilao.getLances().size() == 0) {
      throw new RuntimeException("Não é possível avaliar um leilão sem lances!");
    }

    for (Lance lance: leilao.getLances()) {
      if (lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
      if (lance.getValor() < menorDeTodos) menorDeTodos = lance.getValor();
      maiores.add(lance);
    }

    Collections.sort(maiores, new Comparator<Lance>() {
      @Override
      public int compare(Lance o1, Lance o2) {
        if (o1.getValor() < o2.getValor()) return 1;
        if (o1.getValor() > o2.getValor()) return -1;
        return 0;
      }
    });
    maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
  }

  public List<Lance> getTresMaiores() {
    return this.maiores;
  }

  public double mediaDosLances(Leilao leilao) {
    List<Double> lances = new ArrayList<>();
    double aux = 0;
    for (Lance lance: leilao.getLances()) {
      aux = aux + lance.getValor();
      lances.add(lance.getValor());
    }
    return aux/lances.size();
  }

  public double getMaiorDeTodos() {
    return maiorDeTodos;
  }

  public double getMenorDeTodos() {
    return menorDeTodos;
  }

}
