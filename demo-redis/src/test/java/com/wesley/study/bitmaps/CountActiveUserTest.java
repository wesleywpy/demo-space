package com.wesley.study.bitmaps;

import com.wesley.study.Application;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Created by Wesley on 2018/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CountActiveUserTest {

    @Autowired
    CountActiveUser countActiveUser;

    @Test
    public void alive() throws Exception {
        for (int i = 0; i < 50; i++){
            countActiveUser.alive(i);
        }
    }

    @Test
    public void count() throws Exception {

    }

}