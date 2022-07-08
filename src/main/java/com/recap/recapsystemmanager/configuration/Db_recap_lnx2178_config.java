package com.recap.recapsystemmanager.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.recap.recapsystemmanager.repositories.repo_2", entityManagerFactoryRef = "entityManager_lnx2178", transactionManagerRef = "transactionManager_lnx2178")
public class Db_recap_lnx2178_config {

//	@Value("${spring.jpa.hibernate.ddl-auto}")
//	String ddl_auto;
//
//	@Value("${spring.jpa.properties.hibernate.dialect}")
//	String dialect;
//
//	@Value("${spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation}")
//	String non_contextual_creation;
//
//	@Bean
//	@ConfigurationProperties("spring.datasource.lnx2178")
//	public DataSourceProperties DbProperties_lnx2178() {
//		return new DataSourceProperties();
//	}
//
//	@Bean
//	public DataSource dataSource_lnx2178() {
//		return DbProperties_lnx2178().initializeDataSourceBuilder().type(HikariDataSource.class).build();
//	}
//
//	@Bean(name = "entityManager_lnx2178")
//	@ConfigurationProperties("spring.jpa")
//	public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder) {
//		Map<String, String> primaryJpaProperties = new HashMap<>();
//		primaryJpaProperties.put("hibernate.dialect", dialect);
//		primaryJpaProperties.put("hibernate.hbm2ddl.auto", ddl_auto);
//		primaryJpaProperties.put("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation",
//				non_contextual_creation);
//		return builder.dataSource(dataSource_lnx2178()).packages("com.recap.recapsystemmanager.model")
//				.properties(primaryJpaProperties).build();
//	}
//
//	@Bean(name = "transactionManager_lnx2178")
//	public PlatformTransactionManager transactionManager(
//			final @Qualifier("entityManager_lnx2178") LocalContainerEntityManagerFactoryBean bean) {
//		return new JpaTransactionManager(bean.getObject());
//	}
}
