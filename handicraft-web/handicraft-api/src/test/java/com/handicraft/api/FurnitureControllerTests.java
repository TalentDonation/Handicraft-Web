package com.handicraft.api;

import com.handicraft.api.controller.FurnitureController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by 고승빈 on 2017-07-31.
 */
public class FurnitureControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new FurnitureController()).build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new FurnitureToFurnitureCategory()).build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new FurnitureToFurnitureCategoryServiceImp()).build();
    }

    @Test
    public void test() throws Exception {

//        this.mockMvc.perform(get("/furniture?page=1&per_page=3"))
//                    .andDo(print());
    }
}
