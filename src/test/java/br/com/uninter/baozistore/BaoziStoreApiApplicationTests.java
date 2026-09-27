package br.com.uninter.baozistore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testa o fluxo completo da API: cliente -> produto -> pedido.
 * Usa um banco H2 em memoria (src/test/resources/application.properties).
 */
@SpringBootTest
@AutoConfigureMockMvc
class BaoziStoreApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void fluxoCompletoDeClienteProdutoEPedido() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Teste4994659\",\"clienteDesde\":\"2026-01-10\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clienteDesde").value("2026-01-10"));

        mvc.perform(post("/api/produtos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Baozi de Porco\",\"preco\":7.50,\"estoque\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));

        mvc.perform(post("/api/pedidos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":1,\"produtoId\":1,\"quantidade\":4}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cliente.nome").value("Teste4994659"))
                .andExpect(jsonPath("$.produto.nome").value("Baozi de Porco"))
                .andExpect(jsonPath("$.valorTotal").value(30.00));

        mvc.perform(get("/api/pedidos")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        mvc.perform(get("/api/pedidos/1")).andExpect(status().isOk());

        // cliente com pedido nao pode ser apagado
        mvc.perform(delete("/api/clientes/1")).andExpect(status().isConflict());

        mvc.perform(delete("/api/pedidos/1")).andExpect(status().isNoContent());
        mvc.perform(get("/api/pedidos/1")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensagem").value("Pedido com id 1 nao encontrado"));
    }

    @Test
    void recusaDadosInvalidos() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detalhes[0]").value("nome: O nome do cliente e obrigatorio"));

        mvc.perform(post("/api/pedidos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clienteId\":999,\"produtoId\":999,\"quantidade\":0}"))
                .andExpect(status().isBadRequest());
    }
}
