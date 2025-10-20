package com.example.appfinance.Controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
