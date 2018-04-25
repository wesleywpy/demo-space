package com.wesley.study.udf;

import org.apache.hadoop.io.Text;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Created by Wesley on 2017/12/11.
 */
public class UDFTest {

    @Test
    public void testLower(){
        Assert.assertEquals("hive", new Lower().evaluate(new Text("HIVE")).toString());
    }

    @Test
    public void testRemoveQuoter(){
        Assert.assertEquals("abc", new RemoveQuoterUDF().evaluate(new Text("\"abc\"")).toString());
    }

    @Test
    public void testDateConverter(){
        String string = new DateConverterUDF().evaluate(new Text("31/Aug/2015:00:04:53 +0800")).toString();
        Assert.assertEquals("2015-08-31 00:04:53", string);
    }
}