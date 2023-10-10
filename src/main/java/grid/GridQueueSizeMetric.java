package grid;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.util.Config;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;

public class GridQueueSizeMetric {

	@Test
	public void getQueueSize() throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://20.244.26.111:32000/graphql"))
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
        
        ApiClient api_client = Config.defaultClient();
        io.kubernetes.client.openapi.Configuration.setDefaultApiClient(api_client);
        
        CoreV1Api api = new CoreV1Api();

        String customMetricPath = "/apis/custom.metrics.k8s.io/v1beta1/namespaces/default/pods/*/metrics/SEL_GRID_QUEUE_SIZE";

        try {
            V1Node result = api.readNode(customMetricPath, "SEL_GRID_QUEUE_SIZE");
            System.out.println(result.getStatus());
            // Parse and update the metric value in the 'result' JSON, then use an HTTP PUT to update the metric in the API server.
        } catch (ApiException e) {
            System.err.println("Exception when calling CoreV1Api#readNode: " + e.getMessage());
        }


	}
}
