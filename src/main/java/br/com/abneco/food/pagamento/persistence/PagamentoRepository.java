package br.com.abneco.food.pagamento.persistence;

import br.com.abneco.food.pagamento.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Pagamento findByCodigo(String codigo);
}
