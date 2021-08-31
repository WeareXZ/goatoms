package cn.goat.oms.uitls;

import cn.goat.oms.entity.response.CustomException;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
* @Description:excel导出
* @author: heyz
* @Date: 2021/8/31 10:29
*/
public class ExcelDownUtil {

    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook excel数据
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        OutputStream out = null;
        try {
            response.setContentType("application/vnd.ms-excel;");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (Exception e) {
            throw new CustomException("文件导出错误!");
        }
    }
}
