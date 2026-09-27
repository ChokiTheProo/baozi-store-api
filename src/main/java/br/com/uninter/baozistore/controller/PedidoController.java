package br.com.uninter.baozistore.controller;

import br.com.uninter.baozistore.dto.PedidoRequest;
import br.com.uninter.baozistore.model.Pedido;
import br.com.uninter.baozistore.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /** POST /api/pedidos - registra um pedido. Corpo: { "clienteId", "produtoId", "quantidade" }. */
    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoRequest pedido) {
        Pedido salvo = pedidoService.criar(pedido);
        URI local = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(local).body(salvo);
    }

    /** GET /api/pedidos - lista todos os pedidos. */
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    /** GET /api/pedidos/{id} - consulta um pedido pelo id. */
    @GetMapping("/{id}")
    public Pedido buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    /** PUT /api/pedidos/{id} - atualiza um pedido. */
    @PutMapping("/{id}")
    public Pedido atualizar(@PathVariable Long id, @Valid @RequestBody PedidoRequest pedido) {
        return pedidoService.atualizar(id, pedido);
    }

    /** DELETE /api/pedidos/{id} - apaga um pedido. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        pedidoService.apagar(id);
        return ResponseEntity.noContent().build();
    }
}
