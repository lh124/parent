package io.yfjz.utils.pdf;

import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.utils.PropertiesUtils;
import io.yfjz.utils.ShiroUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description: PDF打印工具类
* @Param:  
* @return:  
* @Author: 田金海
* @Email: 895101047@qq.com
* @Date: 2018/9/15 18:05
* @Tel  17328595627
*/ 
 abstract  public class PDFUtils {
     /** 
     * @Description: 打印方法
     * @Param: [request, response, srcPath, fileName, namesArray, list, tableName]
      * 参数说明 请求对象；响应对象；jrxml文件源码存放位置；jrxml文件名称如 text；String数组 格式={列名，字段名}；打印的数据；报表名称：如测试统计报表
     * @return: void 
     * @Author: 田金海
     * @Email: 895101047@qq.com
     * @Date: 2018/9/15 18:07
     * @Tel  17328595627
     */ 
    public static void  commonPrintPDF(HttpServletRequest request, HttpServletResponse response, String fileName, String[] namesArray, List list, String tableName){
       //判断是否存在文件

        try {
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            //获取Jasper文件路径
            String targetPath = request.getRealPath("/");
            String filepath = targetPath +"reports/"+fileName+".jrxml";
            //把新生成的文件拷贝到target下面
            File targetFile = new File(filepath);
            if (!targetFile.exists()){
                //创建源文件
                String mainPath = createXml(fileName, namesArray, list, tableName);
                targetFile.createNewFile();
                Files.copy(Paths.get(mainPath),new FileOutputStream(targetFile));
            }
            //获取JasperReport对象
            JasperReport jreport = JasperCompileManager.compileReport(filepath);
            Map<String, Object> params = new HashMap<String,Object>();
            //把数据放到Map中
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            params.put("nowDate",sdf.format(new Date()));
            params.put("doctorName", userEntity.getRealName());
            JRDataSource dataSource = new JRBeanCollectionDataSource(list, true);
            // print对象
            JasperPrint print = JasperFillManager.fillReport(jreport, params,
                    dataSource);
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "inline; filename="
                    + new String("Download".getBytes("utf-8")) + ".pdf" + "");

            JRAbstractExporter exporter = new JRPdfExporter();
            OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                    response.getOutputStream());
            ExporterInput exporterInput = new SimpleExporterInput(print);

            exporter.setExporterOutput(exporterOutput);
            // 设置输入项
            exporter.setExporterInput(exporterInput);
            exporter.exportReport();
            exporterOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String createXml(String fileName, String[] namesArray, List list, String tableName) {
        String[] columns=new String[namesArray.length];
        String[] tableHead=new String[namesArray.length];
        for (int i = 0; i < namesArray.length; i++) {
            String[] temp = namesArray[i].split(",");
            columns[i]=temp[1];
            tableHead[i]=temp[0];
        }
        String srcPath = PropertiesUtils.getProperty("jasperreports.properties", "src_path");
        //计算页面宽度，默认一个列的宽度为100
        int size = list.size();
        //页面宽度为
        int pageWidth=100*tableHead.length+70;
        //打印时间，打印医生
        int printTimePoint=(int)(pageWidth*0.02);
        int doctorPoint=(int)(pageWidth*0.7);
        int titlePoint=(int)(pageWidth*0.5-121);

        String mainPath=srcPath+"\\"+fileName+".jrxml";
        File file = new File(srcPath+"/"+fileName+".jrxml");
        BufferedWriter writer =null;
        if (!file.exists()){
            try {
                file.createNewFile();
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

                String head="<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"Abnomal\" language=\"groovy\" pageWidth=\""+pageWidth+"\" pageHeight=\"842\" columnWidth=\""+(pageWidth-50)+"\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"a97da7ff-6d53-4cb2-b1dd-acfcf753dd23\">\n" +
                        "\t<property name=\"ireport.zoom\" value=\"1.0\"/>\n" +
                        "\t<property name=\"ireport.x\" value=\"257\"/>\n" +
                        "\t<property name=\"ireport.y\" value=\"0\"/>\n" +
                        "\t<parameter name=\"nowDate\" class=\"java.lang.String\"/>\n" +
                        "\t<parameter name=\"doctorName\" class=\"java.lang.String\"/>";
                writer.write(head);
                for (String column : columns) {
                    writer.write("<field name=\""+column+"\" class=\"java.lang.String\"/>");
                }
                String  two="\t<background>\n" +
                        "\t\t<band splitType=\"Stretch\"/>\n" +
                        "\t</background>\n" +
                        "\t<title>\n" +
                        "\t\t<band height=\"79\" splitType=\"Stretch\">\n" +
                        "\t\t\t<staticText>\n" +
                        "\t\t\t\t<reportElement x=\""+titlePoint+"\" y=\"0\" width=\"242\" height=\"47\" uuid=\"d6f2dbab-4ab0-4287-a91b-f060ed816b88\"/>\n" +
                        "\t\t\t\t<textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
                        "\t\t\t\t\t<font fontName=\"宋体\" size=\"18\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                        "\t\t\t\t</textElement>\n" +
                        "\t\t\t\t<text><![CDATA[" +tableName+"]]></text>\n" +
                        "\t\t\t</staticText>\n" +
                        "\t\t\t<staticText>\n" +
                        "\t\t\t\t<reportElement x=\""+printTimePoint+"\" y=\"47\" width=\"90\" height=\"32\" uuid=\"4f365de2-cfcf-4fc7-b3c9-5e19977f389b\"/>\n" +
                        "\t\t\t\t<textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
                        "\t\t\t\t\t<font fontName=\"宋体\" size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                        "\t\t\t\t</textElement>\n" +
                        "\t\t\t\t<text><![CDATA[打印时间：]]></text>\n" +
                        "\t\t\t</staticText>\n" +
                        "\t\t\t<staticText>\n" +
                        "\t\t\t\t<reportElement x=\""+doctorPoint+"\" y=\"47\" width=\"90\" height=\"32\" uuid=\"87648704-e11a-40f2-a8ca-f86330f6f048\"/>\n" +
                        "\t\t\t\t<textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
                        "\t\t\t\t\t<font fontName=\"宋体\" size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                        "\t\t\t\t</textElement>\n" +
                        "\t\t\t\t<text><![CDATA[打印医生：]]></text>\n" +
                        "\t\t\t</staticText>\n" +
                        "\t\t\t<textField isBlankWhenNull=\"true\">\n" +
                        "\t\t\t\t<reportElement x=\""+(printTimePoint+90)+"\" y=\"47\" width=\"100\" height=\"32\" uuid=\"49203d25-8d3c-4462-bfd5-2fe479980983\"/>\n" +
                        "\t\t\t\t<textElement verticalAlignment=\"Middle\">\n" +
                        "\t\t\t\t\t<font size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                        "\t\t\t\t</textElement>\n" +
                        "\t\t\t\t<textFieldExpression><![CDATA[$P{nowDate}]]></textFieldExpression>\n" +
                        "\t\t\t</textField>\n" +
                        "\t\t\t<textField isBlankWhenNull=\"true\">\n" +
                        "\t\t\t\t<reportElement x=\""+(doctorPoint+90)+"\" y=\"47\" width=\"100\" height=\"32\" uuid=\"9349fb19-527b-4bf3-91db-a4d1183d2c84\"/>\n" +
                        "\t\t\t\t<textElement verticalAlignment=\"Middle\">\n" +
                        "\t\t\t\t\t<font fontName=\"宋体\" size=\"12\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                        "\t\t\t\t</textElement>\n" +
                        "\t\t\t\t<textFieldExpression><![CDATA[$P{doctorName}]]></textFieldExpression>\n" +
                        "\t\t\t</textField>\n" +
                        "\t\t</band>\n" +
                        "\t</title>" +
                        "\t<pageHeader>\n" +
                        "\t\t<band height=\"23\" splitType=\"Stretch\"/>\n" +
                        "\t</pageHeader>\n" +
                        "\t<columnHeader>\n" +
                        "\t\t<band height=\"22\" splitType=\"Stretch\">";
                //写入表头
                writer.write(two);
                int xPoint=printTimePoint;
                for (String th : tableHead) {
                    String heads="<staticText>\n" +
                            "\t\t\t\t<reportElement x=\""+xPoint+"\" y=\"2\" width=\"100\" height=\"20\" uuid=\"9a842336-54a0-4b12-969a-1490a24048b9\"/>\n" +
                            "\t\t\t\t<box>\n" +
                            "\t\t\t\t\t<pen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<topPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<leftPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<bottomPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<rightPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t</box>\n" +
                            "\t\t\t\t<textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
                            "\t\t\t\t\t<font fontName=\"宋体\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                            "\t\t\t\t</textElement>\n" +
                            "\t\t\t\t<text><![CDATA["+th+"]]></text>\n" +
                            "\t\t\t</staticText>";
                    writer.write(heads);
                    xPoint+=100;
                }
                String three="</band>\n" +
                        "\t</columnHeader>\n" +
                        "\t<detail>\n" +
                        "\t\t<band height=\"21\" splitType=\"Stretch\">";
                writer.write(three);
                xPoint=printTimePoint;
                for (String column : columns) {
                    String enName="<textField isBlankWhenNull=\"true\">\n" +
                            "\t\t\t\t<reportElement x=\""+xPoint+"\" y=\"0\" width=\"100\" height=\"20\" uuid=\"6afb00d7-e3f8-4f41-ab86-3686a24c61ed\"/>\n" +
                            "\t\t\t\t<box>\n" +
                            "\t\t\t\t\t<pen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<topPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<leftPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<bottomPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t\t<rightPen lineWidth=\"0.5\"/>\n" +
                            "\t\t\t\t</box>\n" +
                            "\t\t\t\t<textElement textAlignment=\"Center\" verticalAlignment=\"Middle\">\n" +
                            "\t\t\t\t\t<font fontName=\"宋体\" pdfFontName=\"STSong-Light\" pdfEncoding=\"UniGB-UCS2-H\" isPdfEmbedded=\"true\"/>\n" +
                            "\t\t\t\t</textElement>\n" +
                            "\t\t\t\t<textFieldExpression><![CDATA[$F{"+column+"}]]></textFieldExpression>\n" +
                            "\t\t\t</textField>";
                    xPoint+=100;
                    writer.write(enName);
                }
                String foot="</band>\n" +
                        "\t</detail>\n" +
                        "\t<columnFooter>\n" +
                        "\t\t<band height=\"10\" splitType=\"Stretch\"/>\n" +
                        "\t</columnFooter>\n" +
                        "\t<pageFooter>\n" +
                        "\t\t<band height=\"10\" splitType=\"Stretch\"/>\n" +
                        "\t</pageFooter>\n" +
                        "\t<summary>\n" +
                        "\t\t<band height=\"10\" splitType=\"Stretch\"/>\n" +
                        "\t</summary>\n" +
                        "</jasperReport>";
                writer.write(foot);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mainPath;
    }
}
