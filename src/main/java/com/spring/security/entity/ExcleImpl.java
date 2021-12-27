package com.spring.security.entity;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcleImpl {
    public void export(List<String> titles, List<String>teacher, ServletOutputStream out) throws Exception{
        try{
            // 第一步，创建一个workbook，对应一个Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");

            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short

            HSSFRow row = hssfSheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

            //居中样式
            hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.size(); i++) {
                hssfCell = row.createCell(i);//列索引从0开始
                hssfCell.setCellValue(titles.get(i));//列名1
                hssfCell.setCellStyle(hssfCellStyle);//列居中显示
            }


                row = hssfSheet.createRow(1);
                for(int i=0;i<teacher.size();i++){
                    row.createCell(i).setCellValue(teacher.get(i));
                }
            // 第七步，将文件输出到客户端浏览器
            try {
                workbook.write(out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("导出信息失败！");

        }
    }
}