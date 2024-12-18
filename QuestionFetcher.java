import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class QuestionFetcher {

    private static final String API_URL = "https://opentdb.com/api.php?amount=5&type=multiple"; // Fetch 5 random multiple choice questions

    // Fetches questions from Open Trivia API
    public List<Question> fetchQuestions() {
        List<Question> questions = new ArrayList<>();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Send GET request to Open Trivia API
            HttpGet request = new HttpGet(API_URL);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String jsonResponse = EntityUtils.toString(entity);

                // Parse the response using Jackson
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonResponse);
                JsonNode resultsNode = rootNode.path("results");

                // Loop through the results and extract question data
                for (JsonNode questionNode : resultsNode) {
                    String questionText = questionNode.path("question").asText();
                    JsonNode incorrectAnswersNode = questionNode.path("incorrect_answers");
                    String correctAnswer = questionNode.path("correct_answer").asText();

                    List<String> options = new ArrayList<>();
                    for (JsonNode answerNode : incorrectAnswersNode) {
                        options.add(answerNode.asText());
                    }
                    options.add(correctAnswer); // Add the correct answer at the end

                    // Shuffle the options to randomize their order
                    java.util.Collections.shuffle(options);

                    // Convert the options to an array
                    String[] optionsArray = options.toArray(new String[0]);
                    questions.add(new Question(questionText, optionsArray, correctAnswer));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    // Fetch a random question from the list
    public Question getRandomQuestion(List<Question> questions) {
        java.util.Random rand = new java.util.Random();
        return questions.get(rand.nextInt(questions.size()));
    }
}
