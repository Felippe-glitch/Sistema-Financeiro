package appfinance.Controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import appfinance.Models.Empresa;
import appfinance.Models.Empresa.CreateEmpresa;
import appfinance.Models.Empresa.UpdateEmpresa;
import appfinance.Services.EmpresaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/empresa")
@Validated
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping
    @Validated(CreateEmpresa.class)
    public ResponseEntity<Void> postEmpresa(@RequestBody Empresa empresa) {
        this.empresaService.saveEmpresa(empresa);
        URI URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getIdEmpresa()).toUri();
        
        return ResponseEntity.created(URI).build();
    }

    @GetMapping
    public ResponseEntity<Page<Empresa>> getAllEmpresas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(empresaService.getEmpresas(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable Long id){
        return ResponseEntity.ok(empresaService.getEmpresa(id));
    }

    @GetMapping("/extrato/cliente")
    public ResponseEntity<List<Object[]>> getExtratoCliente(@RequestParam Long id, @RequestParam LocalDate emissao, @RequestParam LocalDate vencimento) {
        return ResponseEntity.ok(empresaService.getExtratoCliente(id, emissao, vencimento));
    }

    @GetMapping("/extrato/fornecedor")
    public ResponseEntity<List<Object[]>> getExtratoFornecedor(@RequestParam Long id, @RequestParam LocalDate emissao, @RequestParam LocalDate vencimento) {
        return ResponseEntity.ok(empresaService.getExtratoFornecedor(id, emissao, vencimento));
    }

    @GetMapping("/extrato/ambos")
    public ResponseEntity<List<Object[]>> getExtratoAmbos(@RequestParam Long id, @RequestParam LocalDate emissao, @RequestParam LocalDate vencimento) {
        return ResponseEntity.ok(empresaService.getExtratoAmbos(id, emissao, vencimento));
    }

    @PutMapping("/{id}")
    @Validated(UpdateEmpresa.class)
    public ResponseEntity<Void> putEmpresa(@PathVariable Long id, @RequestBody Empresa newEmpresa) {
        newEmpresa.setIdEmpresa(id);
        this.empresaService.updateEmpresa(newEmpresa); 
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id){
        empresaService.deleteEmpresa(id);

        return ResponseEntity.noContent().build();
    }
    
    
}
