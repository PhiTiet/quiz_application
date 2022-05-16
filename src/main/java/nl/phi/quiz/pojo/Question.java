package nl.phi.quiz.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Question {
    @JsonProperty("question")
    String question;

    @JsonProperty("incorrect_answers")
    List<String> incorrectAnswers;

    @JsonProperty("correct_answer")
    String correctAnswer;

    public List<String> getRandomizedAnswers(){
        List<String> allAnswers = Stream.concat(incorrectAnswers.stream(), Stream.of(correctAnswer)).collect(Collectors.toList());
        Collections.shuffle(allAnswers);
        return allAnswers;
    }
}
