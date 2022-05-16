package nl.phi.quiz.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class QuizControllerTests {
    private MockMvc mockMvc;

    @InjectMocks
    private QuizController quizController = new QuizController();

    @Mock
    RestTemplate restTemplate;

    @BeforeEach
    void initialize(){
        mockMvc = MockMvcBuilders.standaloneSetup(new QuizController()).build();
    }

    @Test
    void testGetQuestionsPage() throws Exception {
        mockMvc.perform(get("/questions")).andExpect(status().isOk());
    }

    @Test
    void testCheckAnswer() throws Exception{
        mockMvc.perform(post("/checkanswer")).andExpect(status().isBadRequest());
    }

}
