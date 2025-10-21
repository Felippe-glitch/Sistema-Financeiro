package appfinance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppfinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppfinanceApplication.class, args);
	}

	@Bean
	public CommandLineRunner logTabelas(DataSource dataSource) {
		return args -> {
			try (Connection connection = dataSource.getConnection()) {
				System.out.println("TABELAS DO BANCO DE DADOS :");
				ResultSet tables = connection.getMetaData().getTables(null, null, "%", new String[] { "TABLE" });
				System.out.println("{");
				while (tables.next()) {
					String tableName = tables.getString("TABLE_NAME");
					System.out.println(" - " + tableName.toUpperCase());
				}
				System.out.println("}");
				System.out.println();
			} catch (SQLException e) {
				System.err.println("Erro ao obter tabelas do banco: " + e.getMessage());
			}
		};
	}
}
