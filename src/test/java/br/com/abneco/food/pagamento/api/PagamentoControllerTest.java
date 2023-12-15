package br.com.abneco.food.pagamento.api;

import br.com.abneco.food.pagamento.dto.PagamentoDto;
import br.com.abneco.food.pagamento.entities.Pagamento;
import br.com.abneco.food.pagamento.enums.Status;
import br.com.abneco.food.pagamento.persistence.PagamentoRepository;
import br.com.abneco.food.pagamento.services.PagamentoService;
import br.com.abneco.food.pagamento.utils.PagamentoTestMass;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PagamentoService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_ROUTE = "/abneco/food/pagamento";


    @Test
    void testFetchById() throws Exception {
        PagamentoDto dto = PagamentoTestMass.buildPagamentoDto(Status.CRIADO);
        Pagamento pagamento = service.registerPayment(dto).toEntity();

        mockMvc.perform(get(BASE_ROUTE + "/" + pagamento.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(pagamento.getId().toString()))
                .andExpect(jsonPath("codigo").value(dto.getCodigo()))
                .andExpect(jsonPath("expiracao").value(dto.getExpiracao()))
                .andExpect(jsonPath("formaDePagamentoId").value(dto.getFormaDePagamentoId()))
                .andExpect(jsonPath("nome").value(dto.getNome()))
                .andExpect(jsonPath("numero").value(dto.getNumero()))
                .andExpect(jsonPath("pedidoId").value(dto.getPedidoId()))
                .andExpect(jsonPath("status").value(dto.getStatus().toString()));

        repository.deleteById(pagamento.getId());
    }

    @Test
    void testFetchPaymentsPageable() throws Exception {
        PagamentoDto dto = PagamentoTestMass.buildPagamentoDto(Status.CRIADO);
        Pagamento pagamento = service.registerPayment(dto).toEntity();

        mockMvc.perform(get(BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PageRequest.of(0, 10))))
                .andExpect(status().isOk());

        repository.deleteById(pagamento.getId());
    }

    @Test
    void shouldRegisterPayment() throws Exception {
        mockMvc.perform(post(BASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PagamentoTestMass.buildPagamentoDto(Status.CRIADO))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(Status.CRIADO.toString()));

        Pagamento pagamento = repository.findByCodigo(PagamentoTestMass.buildPagamentoDto(Status.CRIADO).getCodigo())
                .orElseThrow(EntityNotFoundException::new);
        repository.deleteById(pagamento.getId());
    }

    @Test
    void shouldUpdatePayment() throws Exception {
        PagamentoDto dto = PagamentoTestMass.buildPagamentoDto(Status.CRIADO);
        Pagamento pagamento = service.registerPayment(dto).toEntity();

        mockMvc.perform(put(BASE_ROUTE + "/" + pagamento.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PagamentoTestMass.buildPagamentoDtoToUpdate(Status.CONFIRMADO))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Status.CONFIRMADO.toString()));

        repository.deleteById(pagamento.getId());
    }

    @Test
    void shouldDeletePayment() throws Exception {
        PagamentoDto dto = PagamentoTestMass.buildPagamentoDto(Status.CRIADO);
        Pagamento pagamento = service.registerPayment(dto).toEntity();

        mockMvc.perform(delete(BASE_ROUTE + "/" + pagamento.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
