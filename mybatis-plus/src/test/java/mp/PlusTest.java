package mp;

import com.wesley.growth.mp.App;
import com.wesley.growth.mp.entity.Student;
import com.wesley.growth.mp.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/08/30
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class PlusTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    public void list() {
        List<Student> list = studentMapper.selectList(null);
        list.forEach(System.out::println);
    }

}
