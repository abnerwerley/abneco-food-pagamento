package br.com.abneco.food.pagamento.services;

import br.com.abneco.food.pagamento.dto.PagamentoDto;
import br.com.abneco.food.pagamento.entities.Pagamento;
import br.com.abneco.food.pagamento.enums.Status;
import br.com.abneco.food.pagamento.persistence.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    public Page<PagamentoDto> fetchPayments(Pageable pageable) {
        return repository.findAll(pageable).map(Pagamento::toResponse);
    }

    public PagamentoDto fetchById(Long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return pagamento.toResponse();
    }

    public PagamentoDto registerPayment(PagamentoDto pagamentoDto) {
        Pagamento pagamento = pagamentoDto.toEntity();
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);
        return pagamento.toResponse();
    }

    public PagamentoDto updatePayment(Long id, PagamentoDto pagamentoDto) {
        Optional<Pagamento> optionalPagamento = repository.findById(id);
        if (optionalPagamento.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Pagamento pagamento = pagamentoDto.toEntity();
        pagamento.setId(id);
        repository.save(pagamento);
        return pagamento.toResponse();
    }

    public void deletePayment(Long id) {
        Pagamento pagamento = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.deleteById(pagamento.getId());
    }
}
