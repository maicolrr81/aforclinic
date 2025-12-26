package com.xenialsoft.api.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * MyBatis 설정을 구성하는 클래스입니다.
 *
 * <p>
 * 이 클래스는 MyBatis의 {@link SqlSessionFactory}를 설정하고, 매퍼 스캔 및 기타 설정을 수행합니다.
 * </p>
 *
 * @author xenialsoft
 * @since  2025.02.07
 */
@Configuration
@MapperScan(basePackages = "com.xenialsoft.api")
public class MyBatisConfig {

    /**
     * MyBatis의 {@link SqlSessionFactory} 빈을 생성합니다.
     *
     * <p>
     * 이 메서드는 데이터 소스를 설정하고, 매퍼 XML 파일 및 타입 별칭 패키지를 지정합니다.
     * </p>
     *
     * @param  datasource 데이터베이스 연결을 위한 {@link DataSource}
     * @return            설정이 완료된 {@link SqlSessionFactory} 객체
     * @throws Exception  SQL 세션 팩토리 생성 중 발생할 수 있는 예외
     */
    @Bean
    SqlSessionFactory sqlSessionFactory(DataSource datasource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(datasource);
        // 매퍼 파일 위치 설정
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/**/*Mapper.xml");
        factory.setMapperLocations(resources);
        // 타입 별칭 설정
        factory.setTypeAliasesPackage("com.xenialsoft.api.**.*.model");
        return factory.getObject();
    }

    /**
     * MyBatis의 추가 설정을 커스터마이징합니다.
     *
     * <p>
     * 이 메서드는 필요 시 타입 핸들러 등의 설정을 추가하는 데 사용됩니다.
     * </p>
     *
     * @return {@link ConfigurationCustomizer} 객체
     */
    @Bean
    ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 타입 핸들러가 필요한 경우 아래에 추가합니다.
        };
    }

}
