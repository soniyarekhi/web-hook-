package com.example.webhookapp;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class WebhookService {

    public void executeFlow() {

        try {
            System.out.println("🚀 STEP 1: Starting Webhook Flow...");

            RestTemplate restTemplate = new RestTemplate();

            String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

            Map<String, String> request = new HashMap<>();
            request.put("name", "Soniya Kaur Rekhi");
            request.put("regNo", "ADT23SOC1147");
            request.put("email", "soniyakaur2412@gmail.com");

            System.out.println("📡 STEP 2: Sending POST request to generate webhook...");
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            System.out.println("✅ STEP 3: Response received!");

            if (response.getBody() == null) {
                System.out.println("❌ ERROR: Response body is NULL");
                return;
            }

            String webhookUrl = (String) response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

            System.out.println("🔗 Webhook URL: " + webhookUrl);
            System.out.println("🔐 Access Token: " + accessToken);

            if (webhookUrl == null || accessToken == null) {
                System.out.println("❌ ERROR: Missing webhook URL or access token");
                return;
            }

            String finalQuery = "SELECT p.AMOUNT AS SALARY, CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE, d.DEPARTMENT_NAME FROM PAYMENTS p JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID WHERE DAY(p.PAYMENT_TIME) != 1 ORDER BY p.AMOUNT DESC LIMIT 1;";

            System.out.println("🧠 STEP 4: Preparing SQL query...");
            System.out.println("📄 SQL QUERY: " + finalQuery);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = new HashMap<>();
            body.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

            System.out.println("📡 STEP 5: Sending final SQL to webhook...");

            ResponseEntity<String> finalResponse =
                    restTemplate.postForEntity(webhookUrl, entity, String.class);

            System.out.println("✅ STEP 6: Submission done!");
            System.out.println("📥 Response from server: " + finalResponse.getBody());

        } catch (Exception e) {
            System.out.println("💥 ERROR OCCURRED:");
            e.printStackTrace();
        }
    }
}
