package jp.co.kdm.testutil;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.io.Resources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.io.Resources;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyBatisTestUtil {

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                Properties props = new Properties();
                try (InputStream in = Resources.getResourceAsStream("application-test.properties")) {
                    props.load(in);
                }

                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(props.getProperty("spring.datasource.url"));
                config.setUsername(props.getProperty("spring.datasource.username"));
                config.setPassword(props.getProperty("spring.datasource.password"));
                config.setDriverClassName(props.getProperty("spring.datasource.driver-class-name"));
                config.setMaximumPoolSize(Integer.parseInt(props.getProperty("spring.datasource.hikari.maximum-pool-size", "5")));

                DataSource dataSource = new HikariDataSource(config);
                JdbcTransactionFactory transactionFactory = new JdbcTransactionFactory();
                Environment environment = new Environment("test", transactionFactory, dataSource);
                Configuration configuration = new Configuration(environment);
                configuration.setMapUnderscoreToCamelCase(true);

                String configLocation = props.getProperty("mybatis.config-location").replace("classpath:", "");
                try (InputStream configStream = Resources.getResourceAsStream(configLocation)) {
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(configStream);
                    sqlSessionFactory.getConfiguration().setEnvironment(environment);
                }

            }
            catch (IOException e) {
                throw new RuntimeException("MyBatisのテスト用初期化に失敗しました", e);
            }
        }
        return sqlSessionFactory;
    }
} 
