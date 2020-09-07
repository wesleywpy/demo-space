package mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wesley.growth.mp.App;
import com.wesley.growth.mp.domain.dto.SearchDTO;
import com.wesley.growth.mp.entity.Student;
import com.wesley.growth.mp.mapper.StudentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *
 * </p>
 *
 * @author Created by Wesley on 2020/08/30
 */
@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class MapperTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    public void list() {
        List<Student> list = studentMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByWrapper() {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "团团");
        List<Student> list = studentMapper.selectList(wrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByLambdaWrapper() {
        List<Student> list = new LambdaQueryChainWrapper<Student>(studentMapper)
                .like(Student::getName, "团").list();
        list.forEach(System.out::println);
    }

    @Test
    public void selectByPage() {
        Page<Student> page = new Page<>();
        page.setSize(5);
        Page<Student> list = studentMapper.selectPage(page, null);
        long total = list.getTotal();
        System.out.println(" ------> total: "+ total);
        list.getRecords().forEach(System.out::println);
    }

    @Test
    public void find() {
        Page<Student> page = new Page<>();
        page.setSize(5);
        SearchDTO search = new SearchDTO();
//        search.setName("");
        IPage<Student> list = studentMapper.findByPage(page, search);
        long total = list.getTotal();
        System.out.println(" ------> total: "+ total);
        list.getRecords().forEach(System.out::println);
    }

    @Test
    public void delete() {
//        studentMapper.deleteById(1);
        studentMapper.deleteAll();
    }

    @Test
    public void insert() {
        Student student = new Student();
        Random random = new Random();
        student.setName("test"+ random.nextInt(1111));
        student.setAge(random.nextInt(99));
        student.setId(random.nextInt(9999));
        studentMapper.insert(student);
    }

    @Test
    public void update() {
        List<Student> list = studentMapper.selectList(Wrappers.<Student>lambdaQuery().gt(Student::getAge, 30));
        Random random = new Random();
        list.stream()
            .findFirst()
            .ifPresent(e -> {
                e.setName("test" + random.nextInt(1111));
                e.setAge(random.nextInt(99));
                studentMapper.updateById(e);
            });
    }

}
