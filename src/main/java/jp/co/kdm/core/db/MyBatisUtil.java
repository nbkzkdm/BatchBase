package jp.co.kdm.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class MyBatisUtil {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            // プロパティ読み込み
            Properties props = new Properties();
            try (InputStream propInput = Resources.getResourceAsStream("application.properties")) {
                props.load(propInput);
            }

            // HikariCP 設定
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(props.getProperty("db.driver"));
            hikariConfig.setJdbcUrl(props.getProperty("db.url"));
            hikariConfig.setUsername(props.getProperty("db.username"));
            hikariConfig.setPassword(props.getProperty("db.password"));
            hikariConfig.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.hikari.maximumPoolSize", "10")));
            hikariConfig.setMinimumIdle(Integer.parseInt(props.getProperty("db.hikari.minimumIdle", "2")));
            hikariConfig.setIdleTimeout(Long.parseLong(props.getProperty("db.hikari.idleTimeout", "30000")));
            hikariConfig.setConnectionTimeout(Long.parseLong(props.getProperty("db.hikari.connectionTimeout", "30000")));
            hikariConfig.setMaxLifetime(Long.parseLong(props.getProperty("db.hikari.maxLifetime", "60000")));

            DataSource dataSource = new HikariDataSource(hikariConfig);

            // MyBatis 設定読み込み
            InputStream configStream = Resources.getResourceAsStream("mybatis-config.xml");

            // Configuration 読み込み
            Configuration configuration = new SqlSessionFactoryBuilder().build(configStream).getConfiguration();

            // データベース環境設定
            TransactionFactory transactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment("development", transactionFactory, dataSource);
            configuration.setEnvironment(environment);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        } catch (Exception e) {
            throw new RuntimeException("MyBatis/Hikari 初期化失敗", e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
