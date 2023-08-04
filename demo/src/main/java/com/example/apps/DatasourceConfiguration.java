package com.example.apps;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.apps.repo.portal", 
		entityManagerFactoryRef = "entityManagerFactory", 
		transactionManagerRef = "transactionManager")
@EnableTransactionManagement
@EnableEncryptableProperties
public class DatasourceConfiguration {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.demodb")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			final EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("com.example.apps.domain.portal")
				.persistenceUnit("persistenceUnit").build();
	}

	@Bean
	public JpaTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") final EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
}
