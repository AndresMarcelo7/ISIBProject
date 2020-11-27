package edu.eci.isiot.flinkjobs.sink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class MySink<IN> extends RichSinkFunction<IN> {

    @Override
    public void invoke(Object value, Context context) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(value);
        OkHttpClient client = new OkHttpClient();
        StringEntity entity = new StringEntity(jsonInString,
                ContentType.APPLICATION_JSON);

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://3.81.29.199:8082/stats");
            request.setEntity(entity);

            HttpResponse response;
            response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

        } catch (IOException ex) {
            System.out.println("aa");
        }
    }
}

