package com.wesley.study.bitmaps;

import com.wesley.study.Application;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;

/**
 * @author Created by Wesley on 2018/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserCheckInTest {

    @Autowired
    UserCheckIn userCheckIn;

    @Test
    public void checkIn() throws Exception {
        assertEquals(true, userCheckIn.checkIn(07130001L));
    }

    @Test
    public void fillCheck() throws Exception {
        assertEquals(true, userCheckIn.fillCheck(07130001L, LocalDate.of(2018, 7, 2)));
    }


}