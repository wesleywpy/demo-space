package com.wesley.growth.streaming.log.dao;

import com.google.common.collect.Lists;
import com.wesley.growth.streaming.log.domain.ClickCount;
import com.wesley.growth.streaming.log.support.HBaseUtils;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/20
 */
public class ClickCountDao {
    private static final String TABLE_NAME = "uoko_clickcount";
    private static final String CF_NAME = "cf1";
    private static final String QUALIFER = "click_count";
    private static final HTable TABLE = HBaseUtils.getInstance().getTable(TABLE_NAME);

    public static void save(List<ClickCount> clickCounts){

        clickCounts.forEach(clickCount -> {
            try {
                TABLE.incrementColumnValue(Bytes.toBytes(clickCount.getRowkey()), Bytes.toBytes(CF_NAME), Bytes.toBytes(QUALIFER), clickCount.getClickCount());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static long count(String rowkey) throws IOException {
        Get get = new Get(Bytes.toBytes(rowkey));
        byte[] value = TABLE.get(get).getValue(Bytes.toBytes(CF_NAME), Bytes.toBytes(QUALIFER));
        return Objects.isNull(value) ? 0L : Bytes.toLong(value);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> rowkeys = Lists.newArrayList("20180920_chenghuaqu", "20180920_jinjiangqu");
        rowkeys.add("20180920_jinniuqu");
        rowkeys.add("20180920_qingyangqu");
        rowkeys.add("20180920_tianfuxinqu");
        rowkeys.add("20180920_wenjiangqu");
        rowkeys.add("20180920_wuhouqu");

        for (String rowkey : rowkeys) {
            System.out.println(rowkey + ":  "+ count(rowkey));
        }
    }

}
