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

import appfinance.Models.Conta;
import appfinance.Models.Conta.CreateConta;
import appfinance.Models.Conta.UpdateConta;
import appfinance.Services.ContaService;

@RestController
@RequestMapping("/conta")
@Validated
public class ContaController {
    @Autowired
    private ContaService contaService;

    @PostMapping
    @Validated(CreateConta.class)
    public ResponseEntity<Void> postConta(@RequestBody Conta conta) {

        this.contaService.createConta(conta);

        URI URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(conta.getIdConta())
                .toUri();

        System.out.println("---> LOG: Conta cadastrada com sucesso!");
        System.out.println("- Agência: " + conta.getAgencia());
        System.out.println("- Conta: " + conta.getConta());
        System.out.println("- Dígito verificador: " + conta.getDvConta());
        System.out.println("- Saldo: " + conta.getSaldo());
        System.out.println("- Tipo de Conta: " + conta.getTipoConta());
        System.out.println("- Status da Conta: " + conta.getStatusConta());
        System.out.println("- Banco (ID): " + conta.getFkBanco().getIdBanco());
        System.out.println("- Banco (Nome): " + conta.getFkBanco().getNomeBanco());

        return ResponseEntity.created(URI).build();
    }

    @GetMapping
    public ResponseEntity<Page<Conta>> getAllContas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(contaService.getContas(page, pageSize));
    }

    @PutMapping("/{id}")
    @Validated(UpdateConta.class)
    public ResponseEntity<Void> putConta(@PathVariable Long id, @RequestBody Conta newConta) {
        newConta.setIdConta(id);
        this.contaService.updateConta(newConta);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id) {
        contaService.deleteConta(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> getContaById(@PathVariable Long id) {
        Conta conta = contaService.getContaById(id);
        return ResponseEntity.ok(conta);
    }

}
