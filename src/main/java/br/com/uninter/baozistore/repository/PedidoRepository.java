package br.com.uninter.baozistore.repository;

import br.com.uninter.baozistore.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    boolean existsByClienteId(Long clienteId);

    boolean existsByProdutoId(Long produtoId);
}
