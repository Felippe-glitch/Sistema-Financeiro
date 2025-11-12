package appfinance.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import appfinance.Models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    List<Empresa> findByTipoEmpresa(int tipoEmpresa);

    boolean existsByCpfCnpj(String cpfCnpj);

    @Procedure(name = "getEmpresa")
    Empresa getEmpresa(@Param("id_empresa") Long id_empresa);

    @Query(value = "EXEC extratoCliente :id_empresa, :data_inicio, :data_fim", nativeQuery = true)
    List<Object[]> extratoCliente(@Param("id_empresa") Long idEmpresa, @Param("data_inicio") LocalDate dataEmissao, @Param("data_fim") LocalDate dataVencimento);

    @Query(value = "EXEC extratoFornecedor :id_empresa, :data_inicio, :data_fim", nativeQuery = true)
    List<Object[]> extratoFornecedor(@Param("id_empresa") Long idEmpresa, @Param("data_inicio") LocalDate dataEmissao, @Param("data_fim") LocalDate dataVencimento);

    @Query(value = "EXEC extratoAmbos :id_empresa, :data_inicio, :data_fim", nativeQuery = true)
    List<Object[]> extratoAmbos(@Param("id_empresa") Long idEmpresa, @Param("data_inicio") LocalDate dataEmissao, @Param("data_fim") LocalDate dataVencimento);
}
