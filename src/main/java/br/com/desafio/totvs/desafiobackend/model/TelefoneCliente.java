package br.com.desafio.totvs.desafiobackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude = "cliente")
@Table(name = "TELEFONE_CLIENTE")
public class TelefoneCliente {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TELEFONE", nullable = false)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Cliente.class, optional = false)
    @JoinColumn(name = "ID_CLIENTE")
    @JsonBackReference
    private Cliente cliente;

}
