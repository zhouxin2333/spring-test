package cool.zhouxin.different.entity.separate.database.api.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author zhouxin
 * @since 2020/6/4 10:11
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "cool.zhouxin.different.entity.separate.database.api.repository.db1",
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef= "db1TransactionManager"
)
public class DB1Config {

    @Bean
    @Primary
    @ConfigurationProperties("cool.zhouxin.datasource.db1")
    public DataSourceProperties db1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("cool.zhouxin.datasource.db1.configuration")
    public DataSource db1DataSource() {
        return db1DataSourceProperties().initializeDataSourceBuilder()
                                        .type(HikariDataSource.class)
                                        .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean db1EntityManagerFactory(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = builder
                .dataSource(db1DataSource())
                .packages("cool.zhouxin.different.entity.separate.database.api.entity.db1")
                .build();
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        entityManagerFactoryBean.setJpaPropertyMap(properties);
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean
    public PlatformTransactionManager db1TransactionManager(
            final @Qualifier("db1EntityManagerFactory") LocalContainerEntityManagerFactoryBean memberEntityManagerFactory) {
        return new JpaTransactionManager(memberEntityManagerFactory.getObject());
    }
}
