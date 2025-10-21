package appfinance.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import appfinance.Models.Receber;
import appfinance.Repository.ReceberRepository;
import jakarta.transaction.Transactional;

@Service
public class ReceberService {

    @Autowired
    private ReceberRepository receberRepository;

    @Transactional
    public Receber createReceber(Receber receber) {
        receber.setIdReceber(null);
        try {
            Receber duplicata = receberRepository.save(receber);
            return duplicata;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar conta: " + e.getMessage());
        }
    }

    @Transactional
    public Receber updateReceber(Receber receber) {
        Receber newDuplicata = receberRepository.findById(receber.getIdReceber())
                .orElseThrow(() -> new RuntimeException("Duplicata não encontrada: " + receber.getIdReceber()));

        newDuplicata.setDataEmissao(receber.getDataEmissao());
        newDuplicata.setDataVencimento(receber.getDataVencimento());
        newDuplicata.setValorReceber(receber.getValorReceber());
        newDuplicata.setDescricaoReceber(receber.getDescricaoReceber());
        newDuplicata.setEmpresa(receber.getEmpresa());
        newDuplicata.setUsuario(receber.getUsuario());
        newDuplicata.setConta(receber.getConta());
        newDuplicata.setDataRec(null);

        newDuplicata = receberRepository.save(newDuplicata);

        return newDuplicata;
    }

    @Transactional
    public Receber deleteReceber(Long id) {
        try {
            Receber duplicata = receberRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Duplicata não encontrada: " + id));
            receberRepository.deleteById(id);
            return duplicata;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar duplicata: " + e.getMessage());
        }
    }

    @Transactional
    public Page<Receber> getContasReceber(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return receberRepository.findAll(pageable);
    }
}
