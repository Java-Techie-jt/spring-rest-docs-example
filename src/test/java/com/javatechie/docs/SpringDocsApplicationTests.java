package com.javatechie.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class SpringDocsApplicationTests {

    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;



    List<Order>  orders=null;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        orders=Stream.of(new Order(101, "Mobile", 1, 15000)
                ,new Order(102, "laptop", 1, 75000))
                .collect(Collectors.toList());
    }



    @Test
    public void testAddOrder() throws Exception {
        String ordersJson=new ObjectMapper().writeValueAsString(orders);
        mockMvc.perform(post("/PlaceOrder")
                .content(ordersJson)
                .contentType("application/json")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(ordersJson))
                .andDo(document("{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void testGetOrders() throws Exception {
           mockMvc.perform(get("/getOrders")
                   .contentType("application/json")).andDo(print())
                   .andExpect(status().isOk())
                   .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(orders)))
                   .andDo(document("{methodName}",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint())));
    }

}
