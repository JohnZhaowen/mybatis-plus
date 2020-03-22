package mp.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import mp.plugins.KeyWordsPlugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "mp.mapper", sqlSessionFactoryRef  = "mysqlSqlSessionFactory")
public class DataSourceMysqlConfig {
	
    private final String sqlmap = "classpath*:mybatis/mapper/*.xml";

    @Qualifier("KeyWordsPlugin")
    @Autowired
    private KeyWordsPlugin keyWordsPlugin;

    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource dateSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource datasource)
            throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setPlugins(keyWordsPlugin);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(sqlmap));
//        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(mybatisConfig));
        return bean.getObject();
    }
    
    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
    	DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }
}
