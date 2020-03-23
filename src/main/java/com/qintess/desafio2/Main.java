package com.qintess.desafio2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.qintess.desafio2.entidades.Cliente;
import com.qintess.desafio2.entidades.ItemDaVenda;
import com.qintess.desafio2.entidades.Livro;
import com.qintess.desafio2.entidades.Venda;

public class Main {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-jpa");
	public static EntityManager em = emf.createEntityManager();

	public static void main(String[] args) {
		ItemDaVenda entity = ItemDaVenda.finById(1, 5);
		ItemDaVenda.delete(entity);
		em.close();
		emf.close();

	}

	@SuppressWarnings("unchecked")
	public static void livrosMaisVendidos() {
		Query query = Main.em.createQuery("select l from Livro l");
		List<Livro> list = query.getResultList();
		Map<Livro, Integer> resultados = new HashMap<Livro, Integer>();
		for (Livro l : list) {
			Integer pedidos = 0;
			for (ItemDaVenda p : l.getPedidos()) {
				pedidos += p.getQuantidade();
			}
			resultados.put(l, pedidos);
		}
		Map<Livro, Integer> resultadosSort = sortByValues(resultados);
		int i = 1;
		for (Livro livro : resultadosSort.keySet()) {
			System.out.println(
					i++ + "- Livro " + livro.getTitulo() + ", requisitado " + resultados.get(livro) + " vezes");
		}
	}

	public static void vendaMaiorValor() {
		Query query = Main.em.createQuery("select v from Venda v order by v.total desc");
		query.setMaxResults(1);
		Venda venda = (Venda) query.getSingleResult();
		System.out.println(
				"A venda com maior valor é a de id " + venda.getId() + " com o valor de " + venda.getTotal() + " R$");
	}
	@SuppressWarnings("unchecked")
	public static void clienteMaisComprou() {
		Query query = Main.em.createQuery("select c from Cliente c");
		List<Cliente> clientes =  query.getResultList();
		int maior = 0;
		for(Cliente c : clientes) {
			int numPedidos = c.getVendas().size();
			if(numPedidos > maior) 
				maior = numPedidos;
		}
		for(Cliente c : clientes) {
			int numPedidos = c.getVendas().size();
			if(numPedidos == maior) 
				System.out.println("Cliente "+c.getNome()+" teve o maior número de compras com "+numPedidos+" pedidos");
		}
		
	}
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
				if (compare == 0)
					return 1;
				else
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}

}
