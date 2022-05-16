package nl.phi.quiz.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import nl.phi.quiz.pojo.Question;
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
    private String apiUrl = "https://opentdb.com/api.php?amount=5";

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private HashMap<String, String> correctAnswers = new HashMap<>();

    @SneakyThrows
    @GetMapping("/questions")
    String getQuestionsPage(Model model) {
        String questionsJson = restTemplate.getForEntity(apiUrl, String.class).getBody();
        JsonNode jsonNode = objectMapper.readTree(questionsJson);
        List<Question> questions = objectMapper.readValue(jsonNode.get("results").toString(), new TypeReference<>() {});

        for (Question question : questions) {
            correctAnswers.put(question.getQuestion(), question.getCorrectAnswer());
        }

        model.addAttribute("questions", questions);
        return "question-page";
    }

    @SneakyThrows
    @PostMapping("/checkanswer")
    @ResponseBody
    Boolean checkAnswer(@RequestBody String json) {
        JsonNode jsonNode = objectMapper.readTree(json);
        if (!jsonNode.has("answer")) {
            return false;
        }
        return correctAnswers.get(jsonNode.get("question").textValue()).equals(jsonNode.get("answer").textValue());
    }
}
