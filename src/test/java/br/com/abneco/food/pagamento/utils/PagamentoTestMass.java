package br.com.abneco.food.pagamento.utils;

import br.com.abneco.food.pagamento.dto.PagamentoDto;
import br.com.abneco.food.pagamento.entities.Pagamento;
import br.com.abneco.food.pagamento.enums.Status;

public class PagamentoTestMass {

    public static Pagamento buildPagamento(Status status) {
        return Pagamento.builder()
                .id(1L)
                .codigo("1a2")
                .expiracao("010124")
                .formaDePagamentoId(1L)
                .nome("Fulano")
                .numero("11123451234")
                .pedidoId(1234234L)
                .status(status)
                .build();
    }

    public static PagamentoDto buildPagamentoDto(Status status) {
        return PagamentoDto.builder()
                .id(1L)
                .codigo("1a2")
                .expiracao("010124")
                .formaDePagamentoId(1L)
                .nome("Fulano")
                .numero("11123451234")
                .pedidoId(1234234L)
                .status(status)
                .build();
    }

    public static PagamentoDto buildPagamentoDtoToUpdate(Status status) {
        return PagamentoDto.builder()
                .id(1L)
                .codigo("1a2")
                .expiracao("010124")
                .formaDePagamentoId(1L)
                .nome("Fulano")
                .numero("11123451234")
                .pedidoId(1234234L)
                .status(status)
                .build();
    }
}
