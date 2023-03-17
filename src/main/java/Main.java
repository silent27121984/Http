import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static final String REMOTE_SERVICE_URI =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build()) {
            HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
            CloseableHttpResponse response = httpClient.execute(request);
            List<Cat> cat = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
            });
            Stream<Cat> stream = cat.stream();
            Stream<Cat> collection = stream.filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0);
            collection.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
