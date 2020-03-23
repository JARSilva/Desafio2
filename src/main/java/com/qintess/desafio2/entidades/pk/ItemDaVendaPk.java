package com.qintess.desafio2.entidades.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.qintess.desafio2.entidades.Livro;
import com.qintess.desafio2.entidades.Venda;

@Embeddable
public class ItemDaVendaPk implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "venda_id")
	private Integer venda;
	
	@Column(name = "livro_id")
	private Integer livro;
	
	public ItemDaVendaPk() {
		super();
	}
	public ItemDaVendaPk(Integer venda, Integer livro) {
		super();
		this.venda = venda;
		this.livro = livro;
	}
	public Integer getVenda() {
		return venda;
	}
	public void setVenda(Integer venda) {
		this.venda = venda;
	}
	public Integer getLivro() {
		return livro;
	}
	public void setLivro(Integer livro) {
		this.livro = livro;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((livro == null) ? 0 : livro.hashCode());
		result = prime * result + ((venda == null) ? 0 : venda.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemDaVendaPk other = (ItemDaVendaPk) obj;
		if (livro == null) {
			if (other.livro != null)
				return false;
		} else if (!livro.equals(other.livro))
			return false;
		if (venda == null) {
			if (other.venda != null)
				return false;
		} else if (!venda.equals(other.venda))
			return false;
		return true;
	}
	
	
}
