package com.wesley.growth.log;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author yani
 * Email yani@uoko.com
 * Created by 2018/09/11
 */
public class LogBolt extends BaseRichBolt {
    public static final String FILED_PHONE = "phone";

    public static final String FILED_ROWKEY = "rowkey";
    public static final String FILED_LONGITUDE = "longitude";
    public static final String FILED_LATITUDE = "latitude";

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        try{
            String value = input.getStringByField("value");

            String[] splits = value.split("\t");
            String phone = splits[0];
            String[] temp = splits[1].split(",");
            String longitude = temp[0];
            String latitude = temp[1];
            long time = DateUtils.getInstance().getTime(splits[2]);

            String rowkey = phone + "," + time;
            System.out.println(rowkey + ", "+ longitude + "," + latitude);
            collector.emit(new Values(rowkey, longitude, latitude));
            this.collector.ack(input);
        }catch (Exception e){
            e.printStackTrace();
            this.collector.fail(input);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(FILED_ROWKEY, FILED_LONGITUDE, FILED_LATITUDE));
    }

}
