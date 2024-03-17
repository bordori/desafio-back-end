package br.com.desafio.totvs.desafiobackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Classe para representação de cliente
 * para operações de banco de dados e regras de negócio
 */
@Entity
@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENTE", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NOME", name = "UK_CLIENTE_NOME")})
public class Cliente implements IEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "BAIRRO")
    private String bairro;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelefoneCliente> telefones;

    public Cliente(Long id) {
        this.id = id;
    }
}
