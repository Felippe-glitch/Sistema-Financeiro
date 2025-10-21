package appfinance.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import appfinance.DTO.ExtratoDiarioDTO;
import appfinance.Models.Movimentacao;
import appfinance.Models.Movimentacao.CreateMovimentacao;
import appfinance.Models.Movimentacao.UpdateMovimentacao;
import appfinance.Services.MovimentacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/movimentacao")
@Validated
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    // Criar movimentação
    @PostMapping
    @Validated(CreateMovimentacao.class)
    public ResponseEntity<Void> postMovimentacao(@RequestBody Movimentacao movimentacao) {
        movimentacaoService.createMovimentacao(movimentacao);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movimentacao.getIdMovimentacao())
                .toUri();

        System.out.println("---> LOG: Movimentação registrada com sucesso!");
        System.out.println("- ID: " + movimentacao.getIdMovimentacao());
        System.out.println("- Valor: " + movimentacao.getValor());
        System.out.println("- Tipo Duplicata: " + (movimentacao.getTipoDuplicata() == 0 ? "Pagar" : "Receber"));
        System.out.println("- Forma de Pagamento: " + movimentacao.getFormaPagamento());
        System.out.println("- Conta (ID): " + movimentacao.getConta().getIdConta());
        System.out.println("- Usuário: " + movimentacao.getUsuario());

        return ResponseEntity.created(uri).build();
    }

    // Listar movimentações com paginação
    @GetMapping
    @Validated
    public ResponseEntity<Page<Movimentacao>> getAllMovimentacoes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(movimentacaoService.getMovs(page, pageSize));
    }

    // Utiliza o procedure 'extratoDiario'
    @GetMapping("/extrato/diario")
    @Validated
    public ResponseEntity<List<ExtratoDiarioDTO>> getExtratoDiario() {
        return ResponseEntity.ok(movimentacaoService.getExtratoDiario());
    }
    

    @PutMapping("/{id}")
    @Validated(UpdateMovimentacao.class)
    public ResponseEntity<Void> putMovimentacao(@PathVariable Long id, @RequestBody Movimentacao novaMovimentacao) {
        novaMovimentacao.setIdMovimentacao(id);
        movimentacaoService.updateMovimentacao(novaMovimentacao);
        return ResponseEntity.noContent().build();
    }

    // Deletar movimentação
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable Long id) {
        movimentacaoService.deleteMovimentacao(id);
        return ResponseEntity.noContent().build();
    }


}
