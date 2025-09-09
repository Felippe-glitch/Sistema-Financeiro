package com.example.appfinance.Services;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import com.example.appfinance.Models.Movimentacao;
import com.example.appfinance.Models.ENUM.TipoDuplicata;
import com.example.appfinance.Repository.MovimentacaoRepository;

public class MovimentacaoService {
    private MovimentacaoRepository movimentacaoRepository;
    private BancoService bancoService;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository, BancoService bancoService) {
        this.movimentacaoRepository = movimentacaoRepository;
        this.bancoService = bancoService;
    }

    // CRUD BÁSICO PARA MOVIMENTAÇÃO

    @Transactional
    public Movimentacao createMovimentacao(Movimentacao mov) {
        try {
            if (mov.getDuplicata() != null) {
                boolean exists = movimentacaoRepository.existsByDuplicata(mov.getDuplicata());
                if (exists) {
                    throw new RuntimeException(
                            "Já existe uma movimentação relacioada à duplicata" + mov.getDuplicata().getIdDuplicata());
                }
            }

            Movimentacao newMov = movimentacaoRepository.save(mov);

            BigDecimal valor = mov.getDuplicata().getValorDuplicata();

            if (mov.getDuplicata().getTipoDuplicata().equals(TipoDuplicata.RECEBER)) {
                bancoService.atualizarSaldo(mov.getBanco().getIdBanco(), valor, true);
            } else {
                bancoService.atualizarSaldo(mov.getBanco().getIdBanco(), valor, false);
            }

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

        if (mov.getDuplicata() != null && !mov.getDuplicata().equals(movExistente.getDuplicata())) {
            boolean exists = movimentacaoRepository.existsByDuplicata(mov.getDuplicata());
            if (exists) {
                throw new RuntimeException("Já existe uma movimentação para a duplicata ID: "
                        + mov.getDuplicata().getIdDuplicata());
            }
        }

        BigDecimal valorAntigo = movExistente.getDuplicata().getValorDuplicata();
        boolean isEntradaAntigo = movExistente.getDuplicata().getTipoDuplicata().equals(TipoDuplicata.RECEBER);
        bancoService.atualizarSaldo(movExistente.getBanco().getIdBanco(), valorAntigo, !isEntradaAntigo);

        movExistente.setBanco(mov.getBanco());
        movExistente.setDuplicata(mov.getDuplicata());
        movExistente.setUsuario(mov.getUsuario());
        movExistente.setFormaPagamento(mov.getFormaPagamento());
        movExistente.setDataRegistroMovimentacao(mov.getDataRegistroMovimentacao());

        Movimentacao movAtualizada = movimentacaoRepository.save(movExistente);

        BigDecimal valorNovo = mov.getDuplicata().getValorDuplicata();
        boolean isEntradaNovo = mov.getDuplicata().getTipoDuplicata().equals(TipoDuplicata.RECEBER);
        bancoService.atualizarSaldo(mov.getBanco().getIdBanco(), valorNovo, isEntradaNovo);

        return movAtualizada;
    }

    @Transactional
    public Movimentacao deleteMovimentacao(Long id) {
        try {
            Movimentacao movExistente = movimentacaoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Movimentação não encontrada com ID: " + id));

            BigDecimal valor = movExistente.getDuplicata().getValorDuplicata();
            boolean isEntrada = movExistente.getDuplicata().getTipoDuplicata().equals(TipoDuplicata.RECEBER);

            bancoService.atualizarSaldo(movExistente.getBanco().getIdBanco(), valor, !isEntrada);

            movimentacaoRepository.delete(movExistente);

            return movExistente;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar movimentação: " + e.getMessage(), e);
        }
    }

}
