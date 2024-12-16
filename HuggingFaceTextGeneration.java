package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class HuggingFaceTextGeneration {
    public static void main(String[] args) {
        try {
            // Configuración de la API
            String apiUrl = "https://api-inference.huggingface.co/models/gpt2";
            String token = "Bearer ingrese token huggingface"; // Token de Hugging Face
            String inputText = "Engineering is art because"; // Texto de entrada

            // Medir tiempo de inicio de carga (simulado)
            long loadStartTime = System.currentTimeMillis();

            // Crear la conexión HTTP
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Configurar el cuerpo de la solicitud
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("inputs", inputText);
            jsonInput.put("parameters", new JSONObject()
                    .put("max_length", 50)           // Longitud máxima del texto generado
                    .put("num_return_sequences", 1)  // Número de secuencias generadas
                    .put("temperature", 0.7)         // Aleatoriedad (0.7 es un buen valor inicial)
                    .put("top_p", 0.9)               // Nucleus sampling (filtra palabras menos probables)
                    .put("repetition_penalty", 1.2)  // Penalización por repetición de palabras
            );


            // Enviar la solicitud
            long generationStartTime = System.currentTimeMillis();
            try (OutputStream os = connection.getOutputStream()) {
                byte[] inputBytes = jsonInput.toString().getBytes("utf-8");
                os.write(inputBytes, 0, inputBytes.length);
            }

            // Leer la respuesta
            int responseCode = connection.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ?
                    connection.getInputStream() : connection.getErrorStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            //System.out.println("Respuesta completa de la API:");
            //System.out.println(response.toString());


            // Procesar la respuesta JSON
            JSONArray outputArray = new JSONArray(response.toString());
            String generatedText = outputArray.getJSONObject(0).getString("generated_text");

            // Calcular tiempos
            long loadTime = generationStartTime - loadStartTime;
            long generationTime = System.currentTimeMillis() - generationStartTime;

            // Mostrar resultados
            System.out.println("\n--- Resultados de la generación ---");
            System.out.printf("Tiempo de carga simulado: %.2f segundos\n", loadTime / 1000.0);
            System.out.printf("Tiempo de generación: %.2f segundos\n", generationTime / 1000.0);
            System.out.println("Texto generado: " + generatedText);
            System.out.println("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
