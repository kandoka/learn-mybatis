package com.kandoka.mybatis.builder.xml;

import com.kandoka.mybatis.builder.BaseBuilder;
import com.kandoka.mybatis.datasource.DataSourceFactory;
import com.kandoka.mybatis.io.Resources;
import com.kandoka.mybatis.mapping.BoundSql;
import com.kandoka.mybatis.mapping.Environment;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.SqlCommandType;
import com.kandoka.mybatis.session.Configuration;
import com.kandoka.mybatis.transaction.TransactionFactory;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Author kandoka
 * @Date 2023/2/6 17:55
 */
@Slf4j
public class XMLConfigBuilder extends BaseBuilder {

    private Element root;

    public XMLConfigBuilder(Reader reader) {
        // 1. 调用父类初始化Configuration
        super(new Configuration());
        // 2. dom4j 处理 xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new InputSource(reader));
            this.root = document.getRootElement();
        } catch (DocumentException e) {
            log.error("Error initiating root element of config resource");
        }
    }

    /**
     * parse all and create a configutation
     * 解析配置；类型别名、插件、对象工厂、对象包装工厂、设置、环境、类型转换、映射器
     *
     * @return Configuration
     */
    public Configuration parse() {
        log.info("create a configuration and fulfill it by parsing xml resources");
        try {
            // parse environment
            environmentsElement(root.element("environments"));
            // parse Mapper
            mapperElement(root.element("mappers"));
        } catch (Exception e) {
            throw new RuntimeException("Error parsing SQL Mapper Configuration. Cause: " + e, e);
        }
        return configuration;
    }

    /**
     * <environments default="development">
     * <environment id="development">
     * <transactionManager type="JDBC">
     * <property name="..." value="..."/>
     * </transactionManager>
     * <dataSource type="POOLED">
     * <property name="driver" value="${driver}"/>
     * <property name="url" value="${url}"/>
     * <property name="username" value="${username}"/>
     * <property name="password" value="${password}"/>
     * </dataSource>
     * </environment>
     * </environments>
     */
    private void environmentsElement(Element context) throws InstantiationException, IllegalAccessException {
        String environment = context.attributeValue("default");

        List<Element> environmentList = context.elements("environment");
        for (Element e : environmentList) {
            String id = e.attributeValue("id");
            if (environment.equals(id)) {
                // 事务管理器
                TransactionFactory txFactory = (TransactionFactory) typeAliasRegistry.resolveAlias(e.element("transactionManager").attributeValue("type")).newInstance();

                // 数据源
                Element dataSourceElement = e.element("dataSource");
                DataSourceFactory dataSourceFactory = (DataSourceFactory) typeAliasRegistry.resolveAlias(dataSourceElement.attributeValue("type")).newInstance();
                List<Element> propertyList = dataSourceElement.elements("property");
                Properties props = new Properties();
                for (Element property : propertyList) {
                    props.setProperty(property.attributeValue("name"), property.attributeValue("value"));
                }
                dataSourceFactory.setProperties(props);
                DataSource dataSource = dataSourceFactory.getDataSource();

                // 构建环境
                Environment.Builder environmentBuilder = new Environment.Builder(id)
                        .transactionFactory(txFactory)
                        .dataSource(dataSource);

                log.info("add environment info into configuration: {}", environment);
                configuration.setEnvironment(environmentBuilder.build());
            }
        }
    }

    /**
     * parse Mappers
     * @param mappers
     * @throws Exception
     */
    private void mapperElement(Element mappers) throws Exception {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element mapperE : mapperList) {
            //get mapper resource from resource attr in a mapper element;
            String resource = mapperE.attributeValue("resource");
            Reader reader = Resources.getResourceAsReader(resource);
            SAXReader saxReader = new SAXReader();

            //read file of mapper resource
            Document document = saxReader.read(new InputSource(reader));
            Element root = document.getRootElement();
            //命名空间
            String namespace = root.attributeValue("namespace");

            // SELECT
            List<Element> selectNodes = root.elements("select");
            for (Element node : selectNodes) {
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                // ? 匹配 parse parameters
                Map<Integer, String> parameter = new HashMap<>();
                Pattern pattern = Pattern.compile("(#\\{(.*?)})");
                Matcher matcher = pattern.matcher(sql);
                for (int i = 1; matcher.find(); i++) {
                    String g1 = matcher.group(1);
                    String g2 = matcher.group(2);
                    parameter.put(i, g2);
                    sql = sql.replace(g1, "?");
                }

                String msId = namespace + "." + id;
                String nodeName = node.getName();
                SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

                BoundSql boundSql = new BoundSql(sql, parameter, parameterType, resultType);

                MappedStatement mappedStatement = new MappedStatement.Builder(configuration, msId, sqlCommandType, boundSql).build();
                // 添加解析 SQL
                log.info("add mapper statement info into configuration: {}", mappedStatement.getId());
                configuration.addMappedStatement(mappedStatement);
            }

            // 注册Mapper映射器
            log.info("add mapper info into configuration: {}", namespace);
            configuration.addMapper(Resources.classForName(namespace));
        }
    }
}
