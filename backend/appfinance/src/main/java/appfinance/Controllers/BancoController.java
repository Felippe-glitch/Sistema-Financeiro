package appfinance.Controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import appfinance.Models.Banco;
import appfinance.Models.Banco.CreateBanco;
import appfinance.Models.Banco.UpdateBanco;
import appfinance.Services.BancoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/banco")
@Validated
public class BancoController {

    @Autowired
    private BancoService bancoService;

    @PostMapping("")
    @Validated(CreateBanco.class)
    public ResponseEntity<Void> postBanco(@RequestBody Banco banco) {

        this.bancoService.createBanco(banco);

        URI URI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(banco.getIdBanco()).toUri();
        
        System.out.println("---> LOG: Banco cadastrado com sucesos! ");
        System.out.println("- Recebido: " + banco.getNomeBanco());
        return ResponseEntity.created(URI).build();
    }

    @GetMapping
    public ResponseEntity<Page<Banco>> getAllBancos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(bancoService.getBancos(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBanco(@PathVariable Long id) {
        return ResponseEntity.ok(bancoService.getBanco(id));
    }
    

    @PutMapping("/{id}")
    @Validated(UpdateBanco.class)
    public ResponseEntity<Void> putBanco(@PathVariable Long id, @RequestBody Banco newBanco) {
        newBanco.setIdBanco(id);
        this.bancoService.updateBanco(newBanco); 
        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable Long id){
        bancoService.deleteBanco(id);

        return ResponseEntity.noContent().build();
    }
    
    
}
