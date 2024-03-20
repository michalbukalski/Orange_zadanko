package michalbukalski;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DeviceTester {
    private String deviceSN;
    private String testingStation;
    private int maxRetries;

    public DeviceTester(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public String testDevice() throws IOException {
        int retryCount = 0;
        while (retryCount < maxRetries) {
            String status = testOnce();
            if (!status.equals("INCONCLUSIVE")) {
                System.out.println("Test result: " + status);
                return status;
            }
            retryCount++;
            System.out.println("Test inconclusive, retrying... (attempt " + retryCount + "/" + maxRetries + ")");
            try {
                Thread.sleep(1000); // Optional delay between retries
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Max retries reached. Reporting potential error...");
        reportPotentialError();
        return null;
    }

    private String testOnce() throws IOException {
        String url = "http://app.orange.pl/testDevice";
        String json = "{\"SN\": \"" + deviceSN + "\", \"TestingStation\": \"" + testingStation + "\"}";
        return sendRequest(url, json);
    }

    public String getLastTestResult() throws IOException {
        String url = "http://app.orange.pl/getLastTestResult";
        String json = "{\"SN\": \"" + deviceSN + "\"}";
        return sendRequest(url, json);
    }

    private String sendRequest(String urlString, String json) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            return responseBody;
        }
    }

    private void reportPotentialError() throws IOException {
        String url = "http://app.orange.pl/reportPotentialError";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String json = "{\"SN\": \"" + deviceSN + "\", \"TestingStation\": \"" + testingStation + "\", \"Date\": \"" +
                LocalDateTime.now().format(formatter) + "\"}";
        String response = sendRequest(url, json);
        System.out.println("Error reported. Response status: " + response);
    }

    public void sendAuthenticatedRequest(String endpointUrl, String accessToken) throws IOException {
        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();
            System.out.println("Response from protected endpoint: " + responseBody);
        }
    }
}
