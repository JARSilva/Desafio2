package com.qintess.desafio2.entidades;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_livro")
public class Livro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String titulo;
	private Double preco;
	private Integer estoque;
	
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "livro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
	private Set<Autor> autores = new HashSet<Autor>();
	
	@ManyToOne
	@JoinColumn(name = "genero_id")
	private Genero genero;
	
	@OneToMany(mappedBy = "livro")
	private Set<ItemDaVenda> pedidos = new HashSet<ItemDaVenda>();
	
	public Livro() {
		super();
	}
	public Livro(Integer id, String titulo, Double preco, Integer estoque, Genero genero) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.preco = preco;
		this.estoque = estoque;
		this.genero = genero;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	public Set<Autor> getAutores() {
		return autores;
	}
	public Set<ItemDaVenda> getPedidos() {
		return pedidos;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", preco=" + preco + ", estoque=" + estoque + "]";
	}
	
	
	
}
