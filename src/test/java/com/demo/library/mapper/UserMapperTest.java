package com.demo.library.mapper;

import com.demo.library.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    Random random;
    @Autowired
    UserMapper userMapper;

    @Before
    public void init() {
        random = new Random(new Date().getTime());
    }

    @Test //已通过
    public void test() {
        //测试添加
        User[] users = new User[10];
        User theUser;
        for (int i = 0; i < 10; i++) {
            users[i] = new User(null, Boolean.FALSE, Long.toString(random.nextLong() % 100000), Long.toString(random.nextLong() % 100000));
            userMapper.insert(users[i]);
        }

        for (int i = 0; i < 10; i++) {
            theUser = userMapper.get(users[i].getId()).get(0);
            Assert.assertEquals("i=" + i, users[i].getName(), theUser.getName());
            Assert.assertEquals("i=" + i, users[i].getPassword(), theUser.getPassword());
        }

        //测试更新
        for (int i = 0; i < 10; i++) {
            if (random.nextBoolean()) {
                users[i].setName(Long.toString(random.nextLong() % 100000));
                users[i].setPassword(Long.toString(random.nextLong() % 100000));
                userMapper.update(users[i]);
            }
        }

        for (int i = 0; i < 10; i++) {
            theUser=userMapper.get(users[i].getId()).get(0);
            Assert.assertEquals(users[i].getName(), theUser.getName());
            Assert.assertEquals(users[i].getPassword(), theUser.getPassword());
        }
    }
}
