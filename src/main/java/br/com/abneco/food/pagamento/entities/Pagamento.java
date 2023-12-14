package br.com.abneco.food.pagamento.entities;

import br.com.abneco.food.pagamento.dto.PagamentoDto;
import br.com.abneco.food.pagamento.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "pagamentos")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 3)
    private String codigo;

    @NotBlank
    @Size(max = 7)
    private String expiracao;

    @NotNull
    private Long formaDePagamentoId;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Size(max = 19)
    private String numero;

    @NotNull
    private Long pedidoId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    public PagamentoDto toResponse() {
        return PagamentoDto.builder()
                .id(this.id)
                .codigo(this.codigo)
                .expiracao(this.expiracao)
                .formaDePagamentoId(this.formaDePagamentoId)
                .nome(this.nome)
                .numero(this.numero)
                .pedidoId(this.pedidoId)
                .status(this.status)
                .build();
    }
}
