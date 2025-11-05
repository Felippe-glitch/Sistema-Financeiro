package appfinance.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appfinance.Models.Banco;
import appfinance.Repository.BancoRepository;

@Service
public class BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    public BancoService(BancoRepository bancoRepository) {
        this.bancoRepository = bancoRepository;
    }

    // CRUD PARA BANCO
    @Transactional
    public Banco createBanco(Banco banco) {
        banco.setIdBanco(null);
        banco = this.bancoRepository.save(banco);
        return banco;
    }

    @Transactional
    public Banco updateBanco(Banco banco) {
        Banco newBanco = bancoRepository.findById(banco.getIdBanco())
                .orElseThrow(() -> new RuntimeException("Banco não encontrado" + banco.getIdBanco()));

        newBanco.setNomeBanco(banco.getNomeBanco());
        newBanco = bancoRepository.save(newBanco);
        return newBanco;
    }

    @Transactional
    public void deleteBanco(Long id) {
        try {
            bancoRepository.deleteById(id);
            System.out.println("Banco deletado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar banco: " + e.getMessage());
        }
    }

    @Transactional
    public Page<Banco> getBancos(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);

        return bancoRepository.findAll(pageable);
    }

    @Transactional
    public Banco getBanco(Long id){
        return bancoRepository.getBanco(id);
    }
}
