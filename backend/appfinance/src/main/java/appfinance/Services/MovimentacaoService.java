package appfinance.Services;

import java.time.LocalDateTime;
// import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import appfinance.DTO.ExtratoDiarioDTO;
import appfinance.DTO.ExtratoPeriodoDTO;
import appfinance.Models.Movimentacao;
import appfinance.Models.Pagar;
import appfinance.Models.Receber;
import appfinance.Repository.MovimentacaoRepository;
import appfinance.Repository.PagarRepository;
import appfinance.Repository.ReceberRepository;
import appfinance.Repository.Custom.ExtratoPeriodoRepositoryCustom;
import appfinance.Repository.Custom.ExtratoRepositoryCustom;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    // @Autowired
    // private ContaService contaService;
    @Autowired
    private ExtratoRepositoryCustom extratoRepositoryCustom;

    @Autowired
    private ReceberRepository receberRepository;

    @Autowired
    private ExtratoPeriodoRepositoryCustom extratoPeriodoRepositoryCustom;

    @Autowired
    private PagarRepository pagarRepository;


    // CRUD BÁSICO PARA MOVIMENTAÇÃO

    @Transactional
    public Movimentacao createMovimentacao(Movimentacao mov) {
        try {

            Movimentacao newMov = movimentacaoRepository.save(mov);


            // --> CRIAR PROCEDIMENTO E CHAMAR NO SERVICE
            // if (mov.getTipoDuplicata().equals(TipoDuplicata.RECEBER)) {
            //     contaService.atualizarSaldo(mov.getConta().getIdConta(), mov.getValor(), true);
            // } else {
            //     bancoService.atualizarSaldo(mov.getBanco().getIdBanco(), valor, false);
            // }

            return newMov;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar movimentação: " + e.getMessage());
        }
    }

    @Transactional
    public Movimentacao updateMovimentacao(Movimentacao mov) {
        Movimentacao movExistente = movimentacaoRepository.findById(mov.getIdMovimentacao())
                .orElseThrow(
                        () -> new RuntimeException("Movimentação não encontrada com ID: " + mov.getIdMovimentacao()));

        // if (mov.getDuplicata() != null && !mov.getDuplicata().equals(movExistente.getDuplicata())) {
        //     boolean exists = movimentacaoRepository.existsByDuplicata(mov.getDuplicata());
        //     if (exists) {
        //         throw new RuntimeException("Já existe uma movimentação para a duplicata ID: "
        //                 + mov.getDuplicata().getIdDuplicata());
        //     }
        // }

        // BigDecimal valorAntigo = movExistente.getValor();
        // boolean isEntradaAntigo = Integer.valueOf(1).equals(movExistente.getTipoDuplicata());
        // contaService.atualizarSaldo(movExistente.getBanco().getIdBanco(), valorAntigo, !isEntradaAntigo);

        movExistente.setConta(mov.getConta());
        movExistente.setTipoDuplicata(mov.getTipoDuplicata());
        movExistente.setUsuario(mov.getUsuario());
        movExistente.setFormaPagamento(mov.getFormaPagamento());
        movExistente.setValor(mov.getValor());
        movExistente.setDataRegistroMovimentacao(mov.getDataRegistroMovimentacao());

        Movimentacao movAtualizada = movimentacaoRepository.save(movExistente);

        // BigDecimal valorNovo = mov.getDuplicata().getValorDuplicata();
        // boolean isEntradaNovo = mov.getDuplicata().getTipoDuplicata().equals(TipoDuplicata.RECEBER);
        // bancoService.atualizarSaldo(mov.getBanco().getIdBanco(), valorNovo, isEntradaNovo);

        return movAtualizada;
    }

    @Transactional
    public Movimentacao deleteMovimentacao(Long id) {
        try {
            Movimentacao movExistente = movimentacaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Movimentação não encontrada com ID: " + id));

            // BigDecimal valor = movExistente.getValor();
            // boolean isEntradaAntigo = Integer.valueOf(2).equals(movExistente.getTipoDuplicata());

            // contaService.atualizarSaldo(movExistente.getConta().getIdConta(), valor, !isEntrada);

            movimentacaoRepository.delete(movExistente);

            return movExistente;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar movimentação: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Page<Movimentacao> getMovs(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return movimentacaoRepository.findAll(pageable);
    }

    @Transactional
    public List<ExtratoDiarioDTO> getExtratoDiario(){
        return extratoRepositoryCustom.extratoDiario();
    }

    @Transactional
    public Movimentacao getMovimentacao(Long id){
        return movimentacaoRepository.getMovimentacao(id);
    }

    @Transactional
    public List<ExtratoPeriodoDTO> getExtratoPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim){
        
        return extratoPeriodoRepositoryCustom.extratoPeriodo(dataInicio, dataFim);
    }

    @Transactional
    public List<Receber> getAuditoriaReceber(String usuario){
        return receberRepository.auditoriaDuplicatas(usuario, 1);
    }

    
    @Transactional
    public List<Pagar> getAuditoriaPagar(String usuario){
        return pagarRepository.auditoriaDuplicatas(usuario, 0);
    }
}
