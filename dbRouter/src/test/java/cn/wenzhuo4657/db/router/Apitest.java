package cn.wenzhuo4657.db.router;

import cn.wenzhuo4657.db.router.annotation.DBRouter;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description:
 */
public class Apitest {

    public static void main(String[] args) {
        System.out.println("Hi");
    }

    /**
     *  @author:wenzhuo4657
        des:
     散列值计算，通过路由id计算路由表、数据源
    */
    @Test
    public void test_db_hash() {
        String key = "小傅哥";

        int dbCount = 2, tbCount = 32;
        int size = dbCount * tbCount;
        // 散列
        int idx = (size - 1) & (key.hashCode() ^ (key.hashCode() >>> 16));

        System.out.println(idx);

        int dbIdx = idx / tbCount + 1;
        int tbIdx = idx - tbCount * (dbIdx - 1);

        System.out.println(dbIdx);
        System.out.println(tbIdx);

    }

    /**
     *  @author:wenzhuo4657
        des: 路由格式
    */
    @Test
    public void test_str_format(){
        System.out.println(String.format("db%02d", 1));
        System.out.println(String.format("_%02d", 25));
    }

    /**
     *  @author:wenzhuo4657
        des: 路由id标记
    */
    @Test
    public void test_annotation() throws NoSuchMethodException {
        Class<IUserDao> iUserDaoClass = IUserDao.class;
        Method method = iUserDaoClass.getMethod("insertUser", String.class);
        DBRouter dbRouter = method.getAnnotation(DBRouter.class);
        System.out.println(dbRouter.key());
    }

}
