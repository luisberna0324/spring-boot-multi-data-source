package com.soltelec.consola.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

import static com.soltelec.consola.DemoApplication.MODEL_PACKAGE;

/**
 * @author Jay Ehsaniara, Dec 30 2019
 */
@Configuration
@ConfigurationProperties("spring.datasource-read")
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryRead",
        transactionManagerRef = "transactionManagerRead",
        basePackages = {"com.soltelec.consola.repository.readRepository"}
)
public class DataSourceConfigRead extends HikariConfigRead {

    public DataSourceConfigRead(HikariReadProperties hikariReadProperties) {
        super(hikariReadProperties);
    }

    @Bean
    public HikariDataSource dataSourceRead() {
        return new HikariDataSource(this);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRead(
            final HikariDataSource dataSourceRead) {

        return new LocalContainerEntityManagerFactoryBean() {{
            setDataSource(dataSourceRead);
            setPersistenceProviderClass(HibernatePersistenceProvider.class);
            setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            setPackagesToScan(MODEL_PACKAGE);
            setJpaProperties(JPA_READ_PROPERTIES);
        }};
    }

    @Bean(name = "readingEntityManagerFactory")
    public EntityManagerFactory readingEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("persistence.postgres");
        em.setDataSource(dataSourceRead());
        em.setPackagesToScan("com.soltelec.consola.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.afterPropertiesSet();
        return em.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManagerRead(EntityManagerFactory entityManagerFactoryRead) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(readingEntityManagerFactory());
        return transactionManager;
    }
}
