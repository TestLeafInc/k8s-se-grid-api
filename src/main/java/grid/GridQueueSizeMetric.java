package grid;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GridQueueSizeMetric {

	@Test
	public void getQueueSize() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://40.81.245.14:32000/graphql"))
            .POST(HttpRequest.BodyPublishers.ofString("{\"query\":\"query Summary { grid { sessionQueueSize } }\"}"))
            .header("Content-Type", "application/json")
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        // Using Jackson to parse the response and extract sessionQueueSize
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(responseBody);
        int sessionQueueSize = rootNode.path("data").path("grid").path("sessionQueueSize").asInt();

        System.out.println("Session Queue Size: " + sessionQueueSize);
        

	}
}
