package Taku.app.core.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Value("${spring.datasource.url}")
    public String jdbcUrl;

    @Value("${spring.datasource.username}")
    public String dbUsername;

    @Value("${spring.datasource.password}")
    public String dbPassword;

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean getSessionFactory() {
        System.out.println("Configuring sessionfactory bean");
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("hibernate.cfg.xml"));

        factoryBean.setDataSource(getDataSource());
        return factoryBean;
    }


    @Bean(name="transactionManager")
    @Inject
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        System.out.println("Configuring transaction manager");
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean(name="dataSource")
    public DataSource getDataSource() {
        System.out.println("Getting data source");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }
}
