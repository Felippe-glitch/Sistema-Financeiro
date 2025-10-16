package com.example.appfinance.Controllers;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.appfinance.Models.Empresa;
import com.example.appfinance.Models.Empresa.CreateEmpresa;
import com.example.appfinance.Models.Empresa.UpdateEmpresa;
import com.example.appfinance.Services.EmpresaService;
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

    private EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService){
        this.empresaService = empresaService;
    }

    @PostMapping
    @Validated(CreateEmpresa.class)
    public ResponseEntity<Void> postEmpresa(@RequestBody Empresa empresa) {
        this.empresaService.saveEmpresa(empresa);
        URI URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getIdEmpresa()).toUri();
        
        return ResponseEntity.created(URI).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaService.getEmpresa(id);
        return ResponseEntity.ok().body(empresa);
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
