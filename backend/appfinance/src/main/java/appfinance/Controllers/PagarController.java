package appfinance.Controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import appfinance.Models.Pagar;
import appfinance.Models.Pagar.CreatePagar;
import appfinance.Models.Pagar.UpdatePagar;
import appfinance.Services.PagarService;

@RestController
@RequestMapping("/pagar")
@Validated
public class PagarController {

    @Autowired
    private PagarService pagarService;

    @PostMapping
    @Validated(CreatePagar.class)
    public ResponseEntity<Void> postPagar(@RequestBody Pagar pagar) {

        this.pagarService.createPagar(pagar);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pagar.getIdContaPagar())
                .toUri();

        System.out.println("---> LOG: Pagamento cadastrado com sucesso!");
        System.out.println("- Valor a pagar: " + pagar.getValorPagar());
        System.out.println("- Data de vencimento: " + pagar.getDataVencimento());
        System.out.println("- Data de emissão: " + pagar.getDataEmissao());
        System.out.println("- Data de pagamento: " + pagar.getDataPag());
        System.out.println("- Descrição: " + pagar.getDescricaoPagar());
        System.out.println("- Usuário: " + pagar.getUsuario());
        System.out.println("- Empresa (ID): " + pagar.getEmpresa().getIdEmpresa());
        System.out.println("- Conta (ID): " + pagar.getConta().getIdConta());

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Pagar>> getAllPagar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(pagarService.getContasPagar(page, pageSize));
    }

    @PutMapping("/{id}")
    @Validated(UpdatePagar.class)
    public ResponseEntity<Void> putPagar(@PathVariable Long id, @RequestBody Pagar newPagar) {
        newPagar.setIdContaPagar(id);
        this.pagarService.updatePagar(newPagar);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagar(@PathVariable Long id) {
        pagarService.deletePagar(id);
        return ResponseEntity.noContent().build();
    }
}
