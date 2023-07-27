package util.login.utils;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.RFC4180Parser;
import com.opencsv.RFC4180ParserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.io.*;
import java.util.*;

@Slf4j
public class CsvUtils {

    /**
     * 根据map生成csv文件
     *
     * @param dataList
     * @return
     */
    public static InputStream createCsv(List<Map<String, String>> dataList) {
        return createCsv(Lists.newArrayList(), dataList);
    }

    /**
     * 根据map生成csv文件
     *
     * @param dataList
     * @return
     */
    public static InputStream createCsv(List<String> headerlist, List<Map<String, String>> dataList) {

        CsvWriter writer = null;
        // 返回流
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = CsvUtil.getWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String[] header;
            if (CollectionUtils.isNotEmpty(headerlist)) {
                header = headerlist.toArray(new String[0]);
            } else {
                header = dataList.get(0).keySet().toArray(new String[0]);
            }
            int len = header.length;

            writer.write(header);
            for (Map<String, String> map : dataList) {
                String[] line = new String[len];
                for (int i = 0; i < len; i++) {
                    line[i] = map.get(header[i]);
                }
                writer.write(line);
            }
            writer.flush();
            byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("文件生成失败");
        } finally {
            try {
                assert writer != null;
                writer.close();
                assert byteArrayInputStream != null;
                byteArrayInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return byteArrayInputStream;
    }


    /**
     * CSV文件生成方法
     *
     * @param head     表头
     * @param dataList 数据，顺序要和表头一致
     * @return
     */
    public static InputStream createCsvByList(List<Object> head, List<List<Object>> dataList) {
        CsvWriter writer = null;
        // 返回流
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = CsvUtil.getWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            if (ObjectUtils.isNotEmpty(head)) {
                writer.write(head.toArray(new String[0]));
            }
            writer.write(dataList);
            writer.flush();
            byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("文件生成失败");
        } finally {
            try {
                assert writer != null;
                writer.close();
                assert byteArrayInputStream != null;
                byteArrayInputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return byteArrayInputStream;
    }

    /**
     * 从流中解析csv文件
     *
     * @param inputStream:文件流
     * @return
     */
    public static List<String[]> getReaderInputStream(InputStream inputStream) {
        try {
            BOMInputStream bomInputStream = new BOMInputStream(inputStream);

            RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
            CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new InputStreamReader(bomInputStream)).withCSVParser(rfc4180Parser);
            return csvReaderBuilder.build().readAll();
        } catch (Exception e) {
            log.error("error", e);
            throw new RuntimeException("文件解析失败");
        }
    }

    /**
     * 下载csv文件，转换问csvReader
     *
     * @param fileUrl
     * @return
     */
    public static CSVReader getReaderFromCsvUrl(String fileUrl) {
        InputStream inputStream = FileUtil.downLoadFile(fileUrl);
        BOMInputStream bomInputStream = new BOMInputStream(inputStream);

        RFC4180Parser rfc4180Parser = new RFC4180ParserBuilder().build();
        CSVReaderBuilder csvReaderBuilder = new CSVReaderBuilder(new InputStreamReader(bomInputStream)).withCSVParser(rfc4180Parser);
        log.info("{}转换为reader成功", fileUrl);
        return csvReaderBuilder.build();
    }

    /**
     * 下载并读取文件内容,一行数据在一个list中
     *
     * @param url
     * @return
     */
    public static List<String[]> readToList(String url) {
        try {
            CSVReader csvReader = getReaderFromCsvUrl(url);
            List<String[]> strings = csvReader.readAll();
            log.info("解析文件{}记录条数{}", url, strings.size());
            return strings;
        } catch (Exception e) {
            log.error("csv文件读取失败，文件地址:{}", url, e);
        }
        return null;
    }

    /**
     * 解析csv文件，生成list<Map></Map>
     *
     * @param data csv解析出来的文件，List<String[]> 专map
     * @return
     */
    public static List<Map<String, String>> parseToMap(List<String[]> data) {
        if (CollectionUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        // 创建Bean集合并将数据存入返回
        List<Map<String, String>> result = new ArrayList<>();
        // 获取文件列名
        String[] header = data.get(0);
        // 从第二行开始遍历
        data.remove(0);
        // 按列遍历
        for (String[] string : data) {
            Map<String, String> object = new HashMap<>();
            // 组装对象数据，key为列名，value为列对应的值
            int length = header.length;
            for (int i = 0; i < length; i++) {
                object.put(header[i], string[i]);
            }
            result.add(object);
        }
        return result;
    }

    /**
     * 获取csv文件表头
     *
     * @param data
     * @return
     */
    public static List<String> getColumns(List<String[]> data) {
        if (CollectionUtils.isNotEmpty(data)) {
            String[] strArr = data.get(0);
            return Arrays.asList(strArr);
        }
        return Collections.emptyList();
    }
}
