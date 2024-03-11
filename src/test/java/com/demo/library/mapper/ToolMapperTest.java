package com.demo.library.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ToolMapperTest {
    @Autowired
    ToolMapper toolMapper;

    @Test //已通过
    public void testNow() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep((new Random()).nextLong() % 500);
            } catch (Exception e) {
            }
            Assert.assertTrue("时间误差在500ms证明没问题",toolMapper.now().getTime() - (new Date()).getTime() < 500);
        }
    }
}
