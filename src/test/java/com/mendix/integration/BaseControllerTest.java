package com.mendix.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> List<T> getListFromMvcResult(MvcResult result) throws IOException {
        return objectMapper.readValue(result.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, com.mendix.dto.RecipemlDto.class));
    }

    protected ResultActions performGet(String path) throws Exception {
        return mockMvc.perform(get(path));
    }

    protected ResultActions performGet(String path, MultiValueMap<String, String> params) throws Exception {

        return mockMvc.perform(get(path).params(params));
    }

    protected ResultActions performPost(String path, Object request) throws Exception {
        return mockMvc.perform(post(path)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON));
    }

    protected String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }
}
