package edu.eci.isiot.flinkjobs.sink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.util.HashMap;

public class MySink<IN> extends RichSinkFunction<IN> {



    @Override
    public void invoke(Object value, Context context) throws Exception {
        System.out.println("kawabongaaaaaaaa");
        System.out.println("lolazos");
        System.out.println("kawabongax2" + value.toString());
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(value);
        System.out.println("kawabongax3 " + jsonInString );
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.google.com/")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }
}
