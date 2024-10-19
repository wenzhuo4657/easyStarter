package cn.wenzhuo4657.db.router.util;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/19
 * @description:
 */
public class PropertyUtil {
    private static int springBootVersion = 1;
    static {
        try {
            /**
             *  @author:wenzhuo4657
                des: 通过反射加载加载特点类，来判断当前springboot的版本。
            当然，此处仅仅做了一个简单的判断。
            因为并没有考虑到springboot3,RelaxedPropertyResolver仅在1x版本中存在
            */
            Class.forName("org.springframework.boot.bind.RelaxedPropertyResolver");
        } catch (ClassNotFoundException e) {
            springBootVersion = 2;
        }
    }




}
