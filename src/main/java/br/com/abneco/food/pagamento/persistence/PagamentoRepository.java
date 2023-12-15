package br.com.abneco.food.pagamento.persistence;

import br.com.abneco.food.pagamento.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByCodigo(String codigo);
}
