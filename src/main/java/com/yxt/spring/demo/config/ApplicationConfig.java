package com.yxt.spring.demo.config;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * MallConfig
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.yxt.spring.demo.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@PropertySource("classpath:db.properties")
public class ApplicationConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    /*@Value("${zhibo.mysql.initialSize}")
    private String initialSize;

    @Value("${zhibo.mysql.minIdle}")
    private String minIdle;

    @Value("${zhibo.mysql.maxActive}")
    private String maxActive;

    @Value("${zhibo.mysql.maxWait}")
    private String maxWait;

    @Value("${zhibo.mysql.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;

    @Value("${zhibo.mysql.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${zhibo.mysql.poolPreparedStatements}")
    private String poolPreparedStatements;

    @Value("${zhibo.mysql.maxPoolPreparedStatementPerConnectionSize}")
    private String maxPoolPreparedStatementPerConnectionSize;*/

    @Bean
    public static PropertySourcesPlaceholderConfigurer zhiboPropertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
       /* dataSource.setInitialSize(Integer.parseInt(initialSize));
        dataSource.setMinIdle(Integer.parseInt(minIdle));
        dataSource.setMaxActive(Integer.parseInt(maxActive));
        dataSource.setMaxWait(Long.parseLong(maxWait));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        dataSource.setPoolPreparedStatements(Boolean.valueOf(poolPreparedStatements));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(maxPoolPreparedStatementPerConnectionSize));*/
        dataSource.setValidationQuery("SELECT 'x' from dual");
        Collection<Object> connectionInitSqls = new ArrayList<Object>();
        /*connectionInitSqls.add("SET NAMES utf8mb4");
        connectionInitSqls.add("SET CHARACTER SET utf8mb4");
        connectionInitSqls.add("SET character_set_connection=utf8mb4");*/
        dataSource.setConnectionInitSqls(connectionInitSqls);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        try {
            dataSource.setFilters("stat");
        } catch (SQLException e) {
            LOGGER.error("dataSource.setFilters() error:", e);
        }
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.yxt.spring.demo.entity");
        factory.setDataSource(dataSource());
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.ejb.entitymanager_factory_name", "entityManagerFactory");
//        properties.put("hibernate.hbm2ddl.auto", "none");
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public TransactionAwareDataSourceProxy transactionAwareDataSourceProxy() {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

}
