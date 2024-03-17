package br.com.desafio.totvs.desafiobackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Classe para representação de telefone do cliente
 * para operações de banco de dados e regras de negócio
 */
@Entity
@Data
@EqualsAndHashCode(exclude = "cliente")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TELEFONE_CLIENTE", uniqueConstraints = {
        @UniqueConstraint(columnNames = "TELEFONE", name = "UK_TELEFONE_CLIENTE")})
public class TelefoneCliente implements IEntity<Long>{

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
