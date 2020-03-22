package mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import mp.entity.User;
import mp.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SimpleQuery {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectById(){
        User user = userMapper.selectById(1L);
        System.out.println("查询结果：" + user);
    }

    @Test
    public void selectByIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 4L));
        System.out.println("查询结果：" + users);
    }

    @Test
    public void selectByMap(){
        Map<String, Object> params = new HashMap<>();
        params.put("email", null);
        List<User> users = userMapper.selectByMap(params);
        System.out.println("查询结果：" + users);
    }
    @Test
    public void selectByWrapper1(){
        QueryWrapper<User> wr = new QueryWrapper<User>().like("name", "雨").lt("age", 40);
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }

    @Test
    public void selectByWrapper2(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .like("name", "雨")
                .between("age", 20, 40)
                .isNotNull("create_time");
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }

    @Test
    public void selectByWrapper3(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .likeRight("name", "王")
                .or()
                .ge("age", 40)
                .orderByDesc("age")
                .orderByAsc("id");
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }

    @Test
    public void selectByWrapper4(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .apply("date_format(create_time, '%Y-%m-%d') = {0}", "2020-03-10")
                .inSql("manager_id", "select id from mp_user where name like '王%'");
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }

    @Test
    public void selectByWrapper5(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .likeRight("name", "张")
                .and(s -> s.gt("age", 20).or().isNotNull("email"));
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }


    @Test
    public void selectByWrapper6(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .likeRight("name", "张")
                .or(s -> s.between("age", 20, 40).isNotNull("email"));
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }


    @Test
    public void selectByWrapper7(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .nested(s -> s.eq("name", "张明强").or().isNotNull("email"))
                .gt("age", 20);
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }

    @Test
    public void selectByWrapper8(){
        QueryWrapper<User> wr = new QueryWrapper<User>()
                .in("age", Arrays.asList(15, 32))
                .last("limit 1");
        List<User> users = userMapper.selectList(wr);
        System.out.println("查询结果：" + users);
    }
}
