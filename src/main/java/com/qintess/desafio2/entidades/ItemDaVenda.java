package com.qintess.desafio2.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.Table;

import com.qintess.desafio2.Main;
import com.qintess.desafio2.entidades.pk.ItemDaVendaPk;

@Entity
@Table(name = "tb_ItemDaVenda")
public class ItemDaVenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemDaVendaPk id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("venda")
	private Venda venda;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("livro")
	private Livro livro;
	private Integer quantidade;
	private Double subTotal;

	public ItemDaVenda() {
		super();
	}

	public ItemDaVenda(Venda venda, Livro livro, Integer quantidade) {
		super();
		this.livro = livro;
		this.venda = venda;
		id = new ItemDaVendaPk(venda.getId(), livro.getId());
		this.quantidade = quantidade;
		this.subTotal = livro.getPreco() * quantidade;
	}

	public ItemDaVendaPk getId() {
		return id;
	}

	public void setId(ItemDaVendaPk id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
		this.subTotal = quantidade * this.livro.getPreco();
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Livro getLivro() {
		return livro;
	}

	public static void calculaTotalEstoque(ItemDaVenda entity, String acao) {
		Livro livro = Main.em.find(Livro.class, entity.getId().getLivro());
		Venda venda = Main.em.find(Venda.class, entity.getId().getVenda());
		Integer estoque = livro.getEstoque();
		Integer tirado = entity.getQuantidade();
		Integer estoqueNovo = null;
		Double subtotal = entity.getSubTotal();
		Double total = venda.getTotal() == null ? 0 : venda.getTotal();
		Double totalNovo = null;
		switch (acao) {
		case "insert":
			estoqueNovo = estoque - tirado;
			totalNovo = total + subtotal;
			break;
		case "update":
			ItemDaVenda idv = Main.em.find(ItemDaVenda.class, entity.id);
			
			totalNovo = (subtotal - idv.subTotal) + total;
			break;
		case "delete":
			estoqueNovo = estoque + tirado;
			totalNovo = total - subtotal;
			break;
		}
		venda.setTotal(totalNovo);
		livro.setEstoque(estoqueNovo);
		Main.em.merge(livro);
		Main.em.merge(venda);
	}

	public static void inserir(ItemDaVenda entity) {

		Main.em.getTransaction().begin();
		Main.em.persist(entity);
		calculaTotalEstoque(entity, "insert");
		Main.em.getTransaction().commit();
	}

	public static ItemDaVenda finById(Integer idVenda, Integer idLivro) {
		return Main.em.find(ItemDaVenda.class, new ItemDaVendaPk(idVenda, idLivro));
	}

	@SuppressWarnings("unchecked")
	public static List<ItemDaVenda> select() {
		Query query = Main.em.createQuery("select t from ItemDaVenda t");
		List<ItemDaVenda> set = query.getResultList();
		return set;
	}

	public static void update(ItemDaVenda entity) {
		Main.em.getTransaction().begin();
		Main.em.merge(entity);
		calculaTotalEstoque(entity, "update");
		Main.em.getTransaction().commit();
	}

	public static void delete(ItemDaVenda entity) {
		Main.em.getTransaction().begin();
		Main.em.remove(entity);
		calculaTotalEstoque(entity, "delete");
		Main.em.getTransaction().commit();
	}

	@Override
	public String toString() {
		return "ItemDaVenda [id=" + id + ", venda=" + venda + ", livro=" + livro + ", quantidade=" + quantidade
				+ ", subTotal=" + subTotal + "]";
	}

	public static ItemDaVenda finById(ItemDaVendaPk id) {
		return Main.em.find(ItemDaVenda.class, id);
	}

}
