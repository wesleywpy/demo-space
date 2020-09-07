package mp;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wesley.growth.mp.App;
import com.wesley.growth.mp.entity.Student;
import com.wesley.growth.mp.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/09/07
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    StudentService studentService;

    @Test
    public void getOne() {
        Student ent = studentService.getOne(Wrappers.<Student>lambdaQuery().gt(Student::getAge, 10), false);
        System.out.println(ent);
    }

    @Test
    public void updateChain() {
        boolean update = studentService.lambdaUpdate().eq(Student::getAge, 22).set(Student::getAge, 30).update();
        System.out.println(update);
    }

}
