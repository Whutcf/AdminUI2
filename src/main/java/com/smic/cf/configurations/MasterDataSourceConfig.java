package com.smic.cf.configurations;

import java.sql.SQLException;

import javax.sql.DataSource;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 * 
 * @ClassName MasterDataSourceConfig
 * @Description 默认的数据源配置类
 * @author cai feng
 * @date 2019年6月15日 sqlSessionFactionryRef表示定义一个唯一SQLSessionFactory实例
 */
@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

	public static final String PACKAGE = "com.smic.cf.mapper.master";

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	@Value("${spring.datasource.initialSize}")
	private int initialSize;
	@Value("${spring.datasource.minIdle}")
	private int minIdle;
	@Value("${spring.datasource.maxActive}")
	private int maxActive;
	@Value("${spring.datasource.maxWait}")
	private int maxWait;
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;
	@Value("${spring.datasource.filters}")
	private String filters;
	@Value("{spring.datasource.connectionProperties}")
	private String connectionProperties;

	@Bean(name = "masterDataSource")
	@Primary // 标志这个 Bean 如果在多个同类 Bean候选时，该 Bean优先被考虑。多数据源配置的时候注意，必须要有一个主数据源，用@Primary标志该Bean
	public DataSource masterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		// 具体配置
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataSource.setConnectionProperties(connectionProperties);
		return dataSource;
	}

	@Bean(name = "masterTransactionManager")
	@Primary
	public DataSourceTransactionManager masterTransactionManager() {
		return new DataSourceTransactionManager(masterDataSource());
	}

	
	@Bean(name = "masterSqlSessionFactory")
	@Primary
	public MybatisSqlSessionFactoryBean masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
			throws Exception {
		final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean(); 
		sessionFactory.setDataSource(masterDataSource);
		//配置是使MybatisPlus的别名生效
		sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setTypeAliasesPackage("com.smic.cf.domain");
		return sessionFactory;
	}
	/*
	 * 使用MybatisPlus替换
	 * public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean(); 
		sessionFactory.setDataSource(masterDataSource);
		//配置是使MybatisPlus的别名生效
		sessionFactory.setVfs(SpringBootVFS.class);
		sessionFactory.setTypeAliasesPackage("com.smic.cf.domain");
		return sessionFactory.getObject();
	}*/

}
