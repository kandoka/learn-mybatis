package com.kandoka.mybatis.spring;

import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/22 17:08
 */
public class MapperScanner extends ClassPathBeanDefinitionScanner {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.SPRING, MapperScanner.class);

    private SqlSessionFactory sqlSessionFactory;

    public MapperScanner(BeanDefinitionRegistry registry, SqlSessionFactory sqlSessionFactory) {
        super(registry, false);
        this.sqlSessionFactory = sqlSessionFactory;
        log.info("Created a MapperScanner");
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        boolean isCandidateComponent =  metadata.isInterface();
        log.info("Is class " + metadata.getClassName() + " candidate? :" + isCandidateComponent);
        return isCandidateComponent;
    }

    @Override
    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        return true;
    }

    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> holders = super.doScan(basePackages);
        for (BeanDefinitionHolder holder: holders) {
            convertToMapperFactoryBean(holder.getBeanDefinition());
        }
        return holders;
    }

    private void convertToMapperFactoryBean(BeanDefinition beanDefinition) {
        GenericBeanDefinition mapperFactoryBeanDefinition =
                (GenericBeanDefinition) beanDefinition;
        //get bean class name before it changes
        String mapperInterfaceName = beanDefinition.getBeanClassName();

        // change mapper interface class to MapperFactoryBean.class
        mapperFactoryBeanDefinition.setBeanClass(MapperFactoryBean.class);
        ConstructorArgumentValues cav =  mapperFactoryBeanDefinition.getConstructorArgumentValues();
        cav.addIndexedArgumentValue(0, mapperInterfaceName);
        cav.addIndexedArgumentValue(1, this.sqlSessionFactory);
    }
}
