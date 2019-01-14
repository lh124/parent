//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.yfjz.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GenUtils {
    public GenUtils() {
    }

    public static List<String> getTemplates() {
        ArrayList templates = new ArrayList();
        templates.add("template/Entity.java.vm");
        templates.add("template/Dao.java.vm");
        templates.add("template/Dao.xml.vm");
        templates.add("template/Service.java.vm");
        templates.add("template/ServiceImpl.java.vm");
        templates.add("template/Controller.java.vm");
        templates.add("template/list.html.vm");
        templates.add("template/list.js.vm");
        return templates;
    }

    /**
     * @param [table, columns, zip]
     * @return void
     * @describe: 代码生成方法
     * @method_name: generatorCode
     * @author 刘琪
     * @QQ：1018628825@qq.com
     * @tel:15685413726
     * @date: 2017/12/25  21:37
     **/
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns, ZipOutputStream zip) {
        Configuration config = getConfig();
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));
        ArrayList columsList = new ArrayList();

        ColumnEntity context;
        String template;
        for (Iterator map = columns.iterator(); map.hasNext(); columsList.add(context)) {
            Map prop = (Map) map.next();
            context = new ColumnEntity();
            context.setColumnName((String) prop.get("columnName"));
            context.setDataType((String) prop.get("dataType"));
            context.setComments((String) prop.get("columnComment"));
            context.setExtra((String) prop.get("extra"));
            String templates = columnToJava(context.getColumnName());
            context.setAttrName(templates);
            context.setAttrname(StringUtils.uncapitalize(templates));
            template = config.getString(context.getDataType(), "unknowType");
            context.setAttrType(template);
            if ("PRI".equalsIgnoreCase((String) prop.get("columnKey")) && tableEntity.getPk() != null) {
                tableEntity.setPk(context);
            }
        }

        tableEntity.setColumns(columsList);
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        Properties prop1 = new Properties();
        prop1.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop1);
        HashMap map1 = new HashMap();
        map1.put("tableName", tableEntity.getTableName());
        map1.put("comments", tableEntity.getComments());
        map1.put("pk", tableEntity.getPk());
        map1.put("className", tableEntity.getClassName());
        map1.put("classname", tableEntity.getClassname());
        map1.put("pathName", tableEntity.getClassname().toLowerCase());
        map1.put("columns", tableEntity.getColumns());
        map1.put("package", config.getString("package"));
        map1.put("author", config.getString("author"));
        map1.put("email", config.getString("email"));
        map1.put("tel", config.getString("tel"));
        map1.put("datetime", DateUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
        VelocityContext context1 = new VelocityContext(map1);
        List templates1 = getTemplates();
        Iterator var12 = templates1.iterator();

        while (var12.hasNext()) {
            template = (String) var12.next();
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context1, sw);

            try {
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString("package"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException var16) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), var16);
            }
        }

    }

    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }

        return columnToJava(tableName);
    }

    public static Configuration getConfig() {
        try {
            PropertiesConfiguration propsConfig = new PropertiesConfiguration();
            //默认的编码格式是ISO-8859-1，所以才在读取文件之前先设置了编码格式
            propsConfig.setEncoding("UTF-8");
            propsConfig.load("generator.properties");
            return propsConfig;
        } catch (ConfigurationException var1) {
            throw new RRException("获取配置文件失败，", var1);
        }
    }

    public static String getFileName(String template, String className, String packageName) {
        String packagePath = "";
        if (StringUtils.isNotBlank(packageName)) {
            packagePath = packageName.replace(".", File.separator) + File.separator;
        }

        return template.contains("Entity.java.vm") ? packagePath + "entity"
                + File.separator + className + "Entity.java" : (template.contains("Dao.java.vm") ? packagePath
                + "dao" + File.separator + className + "Dao.java" : (template.contains("Dao.xml.vm") ? packagePath
                + "dao" + File.separator + className + "Dao.xml" : (template.contains("Service.java.vm") ? packagePath
                + "service" + File.separator
                + className + "Service.java" : (template.contains("ServiceImpl.java.vm") ? packagePath
                + "service" + File.separator + "impl" + File.separator + className
                + "ServiceImpl.java" : (template.contains("Controller.java.vm") ? packagePath
                + "controller" + File.separator + className
                + "Controller.java" : (template.contains("list.html.vm") ? "html"
                + File.separator + className.toLowerCase() + ".html" : (template.contains("list.js.vm") ? "js"
                + File.separator + className.toLowerCase() + ".js" : null)))))));
    }
}
