package br.com.abneco.food.pagamento.dto;

import br.com.abneco.food.pagamento.entities.Pagamento;
import br.com.abneco.food.pagamento.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class PagamentoDto {
    private Long id;
    private BigDecimal valor;
    private String nome;
    private String numero;
    private String expiracao;
    private String codigo;
    private Status status;
    private Long formaDePagamentoId;
    private Long pedidoId;

    public Pagamento toEntity() {
        return Pagamento.builder()
                .id(this.id)
                .codigo(this.codigo)
                .expiracao(this.expiracao)
                .formaDePagamentoId(this.formaDePagamentoId)
                .nome(this.nome)
                .numero(this.numero)
                .pedidoId(this.pedidoId)
                .status(this.status).build();
    }

}
