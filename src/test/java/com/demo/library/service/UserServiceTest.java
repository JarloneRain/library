package com.demo.library.service;

import com.demo.library.entity.User;
import com.demo.library.mapper.UserMapper;
import com.demo.library.service.impl.UserServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {
    // Simulate a database.
    List<User> users;
    UserMapper userMapper = new UserMapper() {
        @Override
        public List<User> get(Integer id) {
            return users.stream().filter(u -> id == null || u.getId().equals(id)).toList();
        }

        @Override
        public List<User> query(String keyword) {
            return users.stream().filter(u ->
                    u.getName().contains(keyword.substring(1, keyword.length() - 2))
            ).toList();
        }

        @Override
        public Integer insert(User user) {
            return users.add(new User(users.size(), user.getAdmin(), user.getName(), user.getPassword())) ? 1 : 0;
        }

        @Override
        public Integer update(User user) {
            users = users.stream().map(b -> b.getId().equals(user.getId()) ? new User(
                    user.getId(),
                    user.getAdmin() == null ? b.getAdmin() : user.getAdmin(),
                    user.getName() == null ? b.getName() : user.getName(),
                    user.getPassword() == null ? b.getPassword() : user.getPassword()
            ) : b).toList();
            return 1;
        }
    };
    UserService userService = new UserServiceImpl(userMapper);

    @BeforeEach
    public void initUsers() {
        users = Lists.newArrayList(
                new User(0, false, "name", "password"),
                new User(1, false, "sfgf", "rgdadtdtosh"),
                new User(2, false, "adhdfg", "soifgosh"),
                new User(3, false, "sagasfg", "dhfjfsosh")
        );
    }

    @Test
    public void testRegister() {

        userService.register("name", "password");

        Assertions.assertEquals("name", users.get(users.size() - 1).getName());
    }

    @Test
    public void testLogIn() {
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Assertions.assertFalse(userService.login(65535, "password"), "incorrect id");
        });
        Assertions.assertFalse(userService.login(0, "wordpass"), "incorrect password");
        Assertions.assertTrue(userService.login(0, "password"), "correct");
    }

    @Test
    public void testGetAll() {
        Assertions.assertEquals(users, userService.getAll());
    }

    @Test
    public void testGetOne() {
        User user;
        for(int id = 0; id < users.size(); id++) {
            user = userService.getOne(id);
            Assertions.assertEquals(userMapper.get(id).get(0), user);
        }
    }

    @Test
    public void testSearchName() {
        Assertions.assertEquals(userMapper.query("%a%"), userService.searchName("a"));
    }

    @Test
    public void testModify() {
        User user = new User(0, false, "newname", "newpassword");
        userService.modify(0, "newname", "newpassword");
        Assertions.assertEquals(user, users.get(0));
    }


//    @InjectMocks
//    UserServiceImpl userService;
//    @Mock
//    UserMapper userMapper;
//
//    List<User> users = Lists.newArrayList(
//            new User(1, false, "a", "soifghjosh"),
//            new User(2, false, "sfgf", "rgdadtdtosh"),
//            new User(3, false, "adhdfg", "soifgosh"),
//            new User(4, false, "sagasfg", "dhfjfsosh")
//    );
//    User user = new User(1, false, "name", "password");
//
//    @Test
//    public void testRegister() {
//        Mockito.when(userMapper.insert(Mockito.any(User.class))).thenReturn(0);
//
//        userService.register("name", "password");
//
//        Mockito.verify(userMapper).insert(Mockito.any(User.class));
//    }
//
//    @Test
//    public void testLogIn() {
//
//        Mockito.when(userMapper.get(1)).thenReturn(Lists.newArrayList(user));
//
//        Assert.assertFalse("incorrect id", userService.logIn(65535, "password"));
//        Assert.assertFalse("incorrect password", userService.logIn(1, "wordpass"));
//        Assert.assertTrue("correct", userService.logIn(1, "password"));
//    }
//
//    @Test
//    public void testGetAll() {
//        Mockito.when(userMapper.get(null)).thenReturn(users);
//
//        Assert.assertEquals(users, userService.getAll());
//    }
//
//    @Test
//    public void testGetOne() {
//        Mockito.when(userMapper.get(Mockito.any(Integer.class))).thenReturn(Lists.newArrayList(user));
//
//        Assert.assertEquals(user, userService.getOne(1));
//    }
//
//    @Test
//    public void testSearchName() {
//        List<User> theUsers = users.stream().filter(u -> u.getName().contains("a")).toList();
//        Mockito.when(userMapper.query("a")).thenReturn(theUsers);
//
//        Assert.assertEquals(theUsers, userService.searchName("a"));
//    }
//
//    @Test
//    public void testModify() {
//        Mockito.when(userMapper.update(Mockito.any(User.class))).thenReturn(0);
//
//        userService.modify(user.getId(), user.getName(), user.getPassword());
//
//        Mockito.verify(userMapper).insert(Mockito.any(User.class));
//    }

}
