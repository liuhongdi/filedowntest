package com.filedowntest.demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.util.StreamUtils;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.*;

@AutoConfigureMockMvc
@SpringBootTest
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("测试下载一个excel文件")
    void downExcel() throws Exception{
        //MvcResult mvcResult =
        mockMvc.perform(get("/home/downexcel")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andDo(new ResultHandler() {
                    @Override
                    public void handle(MvcResult mvcResult) throws Exception {
                        File file  = new File("/data/file/html/e2.xls");
                        file.delete();
                        FileOutputStream fout = new FileOutputStream(file);
                        ByteArrayInputStream bin = new ByteArrayInputStream(mvcResult.getResponse().getContentAsByteArray());
                        StreamUtils.copy(bin,fout);
                        fout.close();
                        System.out.println("is exist:"+file.exists());

                        System.out.println("file length:"+file.length());
                        assertThat(file.exists(), equalTo(true));
                        assertThat(file.length(), greaterThan(1024L));
                    }
                });
    }



}