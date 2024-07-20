package rezero4j.misc;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
/**
 * @author Shin-Ichiro Serizawa <zawashin@outlook.com>
 */
public class MultiKeyTable {
    public static void main(String[] args) {
        //マップを作成します
        Table<String, String, String> table = HashBasedTable.create();

        // [key1, key2] -> value1
        table.put("key1", "key2", "value1");

        // [key3, key4] -> value2
        table.put("key3", "key4", "value2");

        //マルチキーマップを印刷します
        System.out.println(table);

        //key1とkey2に対応する値を出力します
        System.out.println(table.get("key1", "key2"));
    }
}