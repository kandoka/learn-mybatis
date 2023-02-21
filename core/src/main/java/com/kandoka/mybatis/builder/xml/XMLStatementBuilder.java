package com.kandoka.mybatis.builder.xml;

import com.kandoka.mybatis.builder.BaseBuilder;
import com.kandoka.mybatis.builder.MapperBuilderAssistant;
import com.kandoka.mybatis.log.Mark;
import com.kandoka.mybatis.log.MarkableLogger;
import com.kandoka.mybatis.log.MarkableLoggerFactory;
import com.kandoka.mybatis.mapping.MappedStatement;
import com.kandoka.mybatis.mapping.SqlCommandType;
import com.kandoka.mybatis.mapping.SqlSource;
import com.kandoka.mybatis.scripting.LanguageDriver;
import com.kandoka.mybatis.session.Configuration;
import org.dom4j.Element;

import java.util.Locale;

/**
 * @Description mapper statement builder
 * @Author kandoka
 * @Date 2023/2/14 17:13
 */
public class XMLStatementBuilder extends BaseBuilder {

    private final static MarkableLogger log = MarkableLoggerFactory.getLogger(Mark.CONFIG, XMLStatementBuilder.class);

    private MapperBuilderAssistant builderAssistant;
    private Element element;

    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, Element element) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.element = element;
        log.info("Create a statement builder for statement: {}", element.attributeValue("id"));
    }

    /**
     * 解析语句(select|insert|update|delete)<br/>
     * <p>
     * <ul>
     * <li>1. 拆解出parameterType、resultType等属性
     * <li>2. 使用配置的语言驱动器（com.mysql.jdbc.Driver）解析具体的SQL语句
     * <li>3. 最终产物为 {@link MappedStatement} 放入到 {@link Configuration}中
     * <ul/>
     * </p>
     * <p>
     * 待解析样例：
     * <pre>
     {@code
         <select
            id="selectPerson"
            parameterType="int"
            parameterMap="deprecated"
            resultType="hashmap"
            resultMap="personResultMap"
            flushCache="false"
            useCache="true"
            timeout="10000"
            fetchSize="256"
            statementType="PREPARED"
            resultSetType="FORWARD_ONLY">
                SELECT * FROM PERSON WHERE ID = #{id}
         </select>
     }
     <pre/>
     <p/>
     **/
    public void parseStatementNode() {
        String id = element.attributeValue("id");
        log.info("Parse statement in <mapper/>, statement id: {}", id);
        // 参数类型
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveAlias(parameterType);
        // 结果映射
        String resultMap = element.attributeValue("resultMap");
        // 结果类型
        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveAlias(resultType);
        // 获取命令类型(select|insert|update|delete)
        String nodeName = element.getName();
        SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

        // 获取默认语言驱动器
        Class<?> langClass = configuration.getLanguageRegistry().getDefaultDriverClass();
        LanguageDriver langDriver = configuration.getLanguageRegistry().getDriver(langClass);

        SqlSource sqlSource = langDriver.createSqlSource(configuration, element, parameterTypeClass);

        // 调用助手类【便于统一处理参数的包装】
        builderAssistant.addMappedStatement(id,
                sqlSource,
                sqlCommandType,
                parameterTypeClass,
                resultMap,
                resultTypeClass,
                langDriver);
    }

}
