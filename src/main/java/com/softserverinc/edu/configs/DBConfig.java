package com.softserverinc.edu.configs;

import com.softserverinc.edu.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for database, Hibernate, transactions
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.softserverinc.edu.repositories")
public class DBConfig extends WebMvcConfigurerAdapter {

    /**
     * Getting properties data from bean
     */
    @Autowired
    private Environment environment;

    /**
     * Configures data source for database that will be used by other frameworks
     *
     * @return a driver instance that is connected with a database
     */
    @Bean
    public DataSource dataSource() {
        // Simple implementation of the standard JDBC DataSource interface
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClass"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }

    /**
     * Add Support of transactions. It is need @EnableTransactionManagement annotation
     *
     * @param entityManagerFactory use to interact with the entity manager factory for the persistence unit
     * @return jpaTransactionManager
     */
    @Bean
    @Qualifier("transactionManager")
    public JpaTransactionManager jpaTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    /**
     * Specify JPA engine-specific vendor
     *
     * @return JPA vendor Hibernate
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setShowSql(true);
        return jpaVendorAdapter;
    }

    /**
     * Hibernate Manager configuration.
     * <p>invoke {@link #jpaVendorAdapter()}</p>
     *
     * @return entityManagerFactory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        // Scan package for existing entities
        entityManagerFactory.setPackagesToScan("com.softserverinc.edu.entities");
        entityManagerFactory.setJpaProperties(hibernateSearchProperties());
        return entityManagerFactory;
    }

    /**
     * User service bean
     *
     * @return UserService implementations
     */
    @Bean
    public UserService userService() {
        return new UserService();
    }

    /**
     * Hibernate search properties
     * @return hibernateProperties
     */
    private Properties hibernateSearchProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.search.default.directory_provider",
                environment.getProperty("hibernate.search.default.directory_provider"));
        hibernateProperties.setProperty("hibernate.search.default.indexBase",
                environment.getProperty("hibernate.search.default.indexBase"));
        return hibernateProperties;
    }
}
