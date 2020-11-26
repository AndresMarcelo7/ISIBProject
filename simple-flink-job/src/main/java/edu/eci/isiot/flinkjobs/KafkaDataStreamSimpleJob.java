package edu.eci.isiot.flinkjobs;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.isiot.flinkjobs.model.Plant;
import edu.eci.isiot.flinkjobs.model.Temperature;
import edu.eci.isiot.flinkjobs.sink.MySink;
import org.apache.flink.api.common.Plan;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.HashMap;
import java.util.Properties;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import scala.Int;

public class KafkaDataStreamSimpleJob {

    private static HashMap<String,Integer> affected;
    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "kafka:9092");
        properties.setProperty("zookeeper.connect", "zookeeper:2181");
        properties.setProperty("group.id", "test");

        FlinkKafkaConsumer<String> kafkaConsumer = new FlinkKafkaConsumer<>(
                "kafka-data",
                new SimpleStringSchema(),
                properties);

        kafkaConsumer.setStartFromEarliest();

        DataStream<String> kafkaTopicDataStream = env.addSource(kafkaConsumer);

        //TODO define datastream processing graph
        DataStream<Plant> plantMap = kafkaTopicDataStream.map(new MapFunction<String, Plant>() {
            @Override
            public Plant map(String value) throws Exception {
                Plant p = mapper.readValue(value,Plant.class);
                //System.out.println("la planta" + p.toString());
                return p;
            }
        });

        DataStream<Plant> criticStats = plantMap.filter(new FilterFunction<Plant>() {
            @Override
            public boolean filter(Plant p) throws Exception {
                return validateRange(p);
            }
        });

        criticStats.addSink(new PrintSinkFunction<>());
        criticStats.addSink(new MySink<>());

        env.execute();
    }

    private static HashMap<String,Integer[]> criticValuesSet(){
        HashMap<String,Integer[]> cv = new HashMap<>();
        cv.put("temp", new Integer[]{20,30});
        cv.put("light",new Integer[]{20,30});
        cv.put("humidity",new Integer[]{20,30});
        cv.put("ground",new Integer[]{20,30});
        cv.put("proximity",new Integer[]{20,30});
        return cv;
    }

    private static boolean validateRange(Plant p){
        HashMap<String,Integer[]> cv = criticValuesSet();
        affected = new HashMap<>();
        String[] cons = new String[]{"temp", "light", "humidity", "ground", "proximity"};
        Integer[] currVal = new Integer[]{p.getTemp(),p.getLight(),p.getHumidity(),p.getGround(),p.getProximity()};
        int conta=0;
        for(int i = 0; i<cons.length;i++){
            System.out.println("between"+ between(cv.get(cons[i])[0],cv.get(cons[i])[1],currVal[i]));
            if (!between(cv.get(cons[i])[0],cv.get(cons[i])[1],currVal[i])){
                conta+=1;
                affected.put(cons[i],currVal[i]);
            }
        }
        System.out.println("affecteeed" + affected.values());
        p.setAffected(affected);
        return conta>0;
    }
    private static boolean between(int a, int b, int c){
        return ((c >= a) && (c <= b));
    }
}
