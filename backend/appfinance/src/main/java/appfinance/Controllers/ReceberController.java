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

import appfinance.Models.Receber;
import appfinance.Models.Receber.CreateReceber;
import appfinance.Models.Receber.UpdateReceber;
import appfinance.Services.ReceberService;

@RestController
@RequestMapping("/receber")
@Validated
public class ReceberController {
    @Autowired
    private ReceberService receberService;

    @PostMapping
    @Validated(CreateReceber.class)
    public ResponseEntity<Void> postReceber(@RequestBody Receber receber) {

        this.receberService.createReceber(receber);

        URI URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(receber.getIdReceber())
                .toUri();

        System.out.println("---> LOG: Recebimento cadastrado com sucesso!");
        System.out.println("- ID: " + receber.getIdReceber());
        System.out.println("- Valor a receber: " + receber.getValorReceber());
        System.out.println("- Data de vencimento: " + receber.getDataVencimento());
        System.out.println("- Data de emissão: " + receber.getDataEmissao());
        System.out.println("- Data de recebimento: " + receber.getDataRec());
        System.out.println("- Descrição: " + receber.getDescricaoReceber());
        System.out.println("- Usuário: " + receber.getUsuario());

        System.out.println("- Empresa (ID): " + receber.getEmpresa().getIdEmpresa());

        System.out.println("- Conta (ID): " + receber.getConta().getIdConta());

        return ResponseEntity.created(URI).build();
    }

    @GetMapping
    public ResponseEntity<Page<Receber>> getAllReceber(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(receberService.getContasReceber(page, pageSize));
    }

    @PutMapping("/{id}")
    @Validated(UpdateReceber.class)
    public ResponseEntity<Void> putReceber(@PathVariable Long id, @RequestBody Receber newReceber) {
        newReceber.setIdReceber(id);
        this.receberService.updateReceber(newReceber);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceber(@PathVariable Long id) {
        receberService.deleteReceber(id);

        return ResponseEntity.noContent().build();
    }
}
