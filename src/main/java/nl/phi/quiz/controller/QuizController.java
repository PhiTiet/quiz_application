package nl.phi.quiz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.phi.quiz.pojo.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
@Controller
public class QuizController {

    private final String apiUrl = "https://opentdb.com/api.php?amount=5";

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private HashMap<String, String> correctAnswers = new HashMap<>();


    @GetMapping("/questions")
    String getQuestionsPage(Model model) {
        String questionsJson = restTemplate.getForEntity(apiUrl, String.class).getBody();
        List<Question> questions;

        try{
            JsonNode jsonNode = objectMapper.readTree(questionsJson);
            questions = objectMapper.readValue(jsonNode.get("results").toString(), new TypeReference<>() {});
        }
        catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        for (Question question : questions) {
            correctAnswers.put(question.getQuestion(), question.getCorrectAnswer());
        }

        model.addAttribute("questions", questions);
        return "question-page";
    }


    @PostMapping(value = "/checkanswer" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<?> checkAnswer(@RequestBody String json) {
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (!jsonNode.has("answer")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("empty");
        }
        boolean result = correctAnswers.get(jsonNode.get("question").textValue()).equals(jsonNode.get("answer").textValue());

        return ResponseEntity.status(HttpStatus.OK).body(Boolean.toString(result));
    }
}
