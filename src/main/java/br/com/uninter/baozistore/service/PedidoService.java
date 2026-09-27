package br.com.uninter.baozistore.service;

import br.com.uninter.baozistore.dto.PedidoRequest;
import br.com.uninter.baozistore.exception.RecursoNaoEncontradoException;
import br.com.uninter.baozistore.exception.RegraNegocioException;
import br.com.uninter.baozistore.model.Pedido;
import br.com.uninter.baozistore.model.Produto;
import br.com.uninter.baozistore.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository pedidoRepository, ClienteService clienteService,
                         ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }

    @Transactional
    public Pedido criar(PedidoRequest dados) {
        Pedido pedido = new Pedido();
        preencher(pedido, dados);
        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido", id));
    }

    @Transactional
    public Pedido atualizar(Long id, PedidoRequest dados) {
        Pedido pedido = buscarPorId(id);
        preencher(pedido, dados);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void apagar(Long id) {
        pedidoRepository.delete(buscarPorId(id));
    }

    /** Busca cliente e produto pelos ids, confere o estoque e calcula o valor total. */
    private void preencher(Pedido pedido, PedidoRequest dados) {
        Produto produto = produtoService.buscarPorId(dados.produtoId());
        if (!Boolean.TRUE.equals(produto.getEstoque())) {
            throw new RegraNegocioException(HttpStatus.BAD_REQUEST,
                    "O produto '" + produto.getNome() + "' esta sem estoque");
        }
        pedido.setCliente(clienteService.buscarPorId(dados.clienteId()));
        pedido.setProduto(produto);
        pedido.setQuantidade(dados.quantidade());
        pedido.calcularValorTotal();
    }
}
