package util.login.utils;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Slf4j
public class ExcelUtils {

    /**
     * 导出多个 Sheet 页
     *
     * @param sheetList 页数据
     * @param fileName  文件名
     */
    public static InputStream exportExcel(List<SheetDTO> sheetList, String fileName) {
        // 返回流

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ExcelWriter bigWriter = ExcelUtil.getBigWriter();
        // 重命名第一个Sheet的名称，不然会默认多出一个Sheet1的页
        bigWriter.renameSheet(0, sheetList.get(0).getSheetName());
        for (SheetDTO sheet : sheetList) {
            // 指定要写出的 Sheet 页
            bigWriter.setSheet(sheet.getSheetName());
            Integer[] columnWidth = sheet.getColumnWidth();
            if (columnWidth == null || columnWidth.length != sheet.getFieldAndAlias().size()) {
                // 设置默认宽度
                for (int i = 0; i < sheet.getFieldAndAlias().size(); i++) {
                    bigWriter.setColumnWidth(i, 25);
                }
            } else {
                // 设置自定义宽度
                for (int i = 0; i < columnWidth.length; i++) {
                    bigWriter.setColumnWidth(i, columnWidth[i]);
                }
            }
            // 设置字段和别名
            bigWriter.setHeaderAlias(sheet.getFieldAndAlias());
            // 设置只导出有别名的字段
            bigWriter.setOnlyAlias(true);
            // 设置默认行高
            bigWriter.setDefaultRowHeight(18);
            // 设置冻结行
            bigWriter.setFreezePane(1);
            // 一次性写出内容，使用默认样式，强制输出标题
            bigWriter.write(sheet.getCollection(), true);
            // 设置所有列为自动宽度，不考虑合并单元格
//            bigWriter.autoSizeColumnAll();
        }
        bigWriter.flush(outputStream,true);
        bigWriter.close();
        IoUtil.close(outputStream);

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}

