package br.com.uninter.baozistore.service;

import br.com.uninter.baozistore.exception.RecursoNaoEncontradoException;
import br.com.uninter.baozistore.exception.RegraNegocioException;
import br.com.uninter.baozistore.model.Produto;
import br.com.uninter.baozistore.repository.PedidoRepository;
import br.com.uninter.baozistore.repository.ProdutoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, PedidoRepository pedidoRepository) {
        this.produtoRepository = produtoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public Produto criar(Produto produto) {
        produto.setId(null);
        return produtoRepository.save(produto);
    }

    @Transactional(readOnly = true)
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto", id));
    }

    @Transactional
    public Produto atualizar(Long id, Produto dados) {
        Produto produto = buscarPorId(id);
        produto.setNome(dados.getNome());
        produto.setPreco(dados.getPreco());
        produto.setEstoque(dados.getEstoque());
        return produtoRepository.save(produto);
    }

    @Transactional
    public void apagar(Long id) {
        Produto produto = buscarPorId(id);
        if (pedidoRepository.existsByProdutoId(id)) {
            throw new RegraNegocioException(HttpStatus.CONFLICT,
                    "Produto possui pedidos cadastrados. Apague esses pedidos antes de apagar o produto");
        }
        produtoRepository.delete(produto);
    }
}
