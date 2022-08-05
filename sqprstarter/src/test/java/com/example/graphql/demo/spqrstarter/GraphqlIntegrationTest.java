package com.example.graphql.demo.spqrstarter;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author zhengqian
 * @date 2022.08.04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GraphqlIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookService bookService;

    private static final String GRAPHQL_PATH = "/graphql";

    @Test
    public void givenNoBooks_whenReadAll_thenStatusIsOk() throws Exception {

        String getAllBooksQuery = "{ getAllBooks {id author title } }";

        // 1、因为graphql-spqr-spring-boot-starter自带的GraphQLController是异步请求，因此这里获取数据的方式与spqr略有不同
        // 2、返回数据包装了一层data
        MvcResult mvcResult = this.mockMvc.perform(post(GRAPHQL_PATH).content(toJSON(getAllBooksQuery))
                .contentType(
                        MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.getAllBooks").isEmpty());
    }

    @Test
    public void whenAddBook_thenStatusIsOk() throws Exception {

        String addBookMutation = "mutation { addBook(newBook: {id: 123, author: \"J.R.R. Tolkien\", "
                + "title: \"The Lord of the Rings\"}) { id author title } }";

        MvcResult mvcResult = this.mockMvc.perform(post(GRAPHQL_PATH).content(toJSON(addBookMutation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(request().asyncStarted())
                .andReturn();
        this.mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.addBook.id").value("123"))
                .andExpect(jsonPath("$.data.addBook.author").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.data.addBook.title").value("The Lord of the Rings"));
    }

    private String toJSON(String query) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("query", query);
        return jsonObject.toString();
    }
}
