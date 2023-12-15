package br.com.abneco.food.pagamento.service;

import br.com.abneco.food.pagamento.dto.PagamentoDto;
import br.com.abneco.food.pagamento.entities.Pagamento;
import br.com.abneco.food.pagamento.enums.Status;
import br.com.abneco.food.pagamento.persistence.PagamentoRepository;
import br.com.abneco.food.pagamento.services.PagamentoService;
import br.com.abneco.food.pagamento.utils.PagamentoTestMass;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService service;

    @Mock
    private PagamentoRepository repository;

    public static final Long ID = 1L;


    @Test
    void testFetchPayments() {
        List<Pagamento> pagamentos = List.of(PagamentoTestMass.buildPagamento(Status.CRIADO), PagamentoTestMass.buildPagamento(Status.CONFIRMADO));
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(pagamentos));
        Page<PagamentoDto> resultado = service.fetchPayments(PageRequest.of(0, 10));

        assertEquals(pagamentos.size(), resultado.getContent().size());
        assertEquals(Status.CRIADO, resultado.getContent().get(0).getStatus());
        assertEquals(Status.CONFIRMADO, resultado.getContent().get(1).getStatus());
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void shouldFetchById() {
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(PagamentoTestMass.buildPagamento(Status.CRIADO)));
        PagamentoDto response = service.fetchById(ID);
        assertEquals(Status.CRIADO, response.getStatus());
        verify(repository).findById(ID);

        when(repository.findById(ID)).thenThrow(EntityNotFoundException.class);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.fetchById(ID));
        assertNotNull(exception);
    }

    @Test
    void shouldCreatePayment() {
        PagamentoDto response = service.registerPayment(PagamentoTestMass.buildPagamentoDto(Status.CRIADO));
        assertEquals(Status.CRIADO, response.getStatus());
        verify(repository, times(1)).save(any(Pagamento.class));
    }

    @Test
    void shouldUpdatePayment() {
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(PagamentoTestMass.buildPagamento(Status.CRIADO)));
        PagamentoDto response = service.updatePayment(ID, PagamentoTestMass.buildPagamentoDtoToUpdate(Status.CONFIRMADO));
        assertEquals(Status.CONFIRMADO, response.getStatus());
        verify(repository).findById(ID);
        verify(repository, times(1)).save(any(Pagamento.class));
    }

    @Test
    void shouldDeletePayment() {
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(PagamentoTestMass.buildPagamento(Status.CRIADO)));
        service.deletePayment(ID);
        verify(repository).findById(ID);
        verify(repository, times(1)).deleteById(any());
    }

}
