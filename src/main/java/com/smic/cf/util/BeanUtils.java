package com.smic.cf.util;

import com.smic.cf.pojo.Cat;
import com.smic.cf.pojo.Dog;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 实体类工具
 * @ClassName BeanUtils
 * @Author 蔡明涛
 * @date 2020.03.02 23:24
 */
public class BeanUtils {

    /**
     * 利用反射比较两个对象是否相同，排除某些属性
     * @param ignoreList 排除的属性名清单, obj1 对象1, obj2 对象2
     * @return boolean
     * @author 蔡明涛
     * @date 2020.03.03 00:41
     **/
    public static boolean compareTwoObjs(List<String> ignoreList, Object obj1, Object obj2){
        //同种类型的对象才可以参与比较
        if (obj1.getClass() == obj2.getClass() && !StringUtils.isEmpty(obj1) && !StringUtils.isEmpty(obj2)){
            //实例化class对象
            Class<?> clazz1 = obj1.getClass();
            Class<?> clazz2 = obj2.getClass();
            //得到对象的全部属性 （修饰符+类型+名字）
            Field[] clazz1DeclaredFields = clazz1.getDeclaredFields();
            Field[] clazz2DeclaredFields = clazz2.getDeclaredFields();
            for (Field clazz1DeclaredField : clazz1DeclaredFields) {
                //排除不需要比较的内容
                if (!ignoreList.contains(clazz1DeclaredField.getName())) {
                    //取消属性的访问权限控制，即使private属性也可以进行访问
                    clazz1DeclaredField.setAccessible(true);
                    try {
                        //获取属性的值
                        Object value1 = clazz1DeclaredField.get(obj1);
                        for (Field clazz2DeclaredField : clazz2DeclaredFields) {
                            clazz2DeclaredField.setAccessible(true);
                            if (!ignoreList.contains(clazz2DeclaredField.getName()) && clazz1DeclaredField.getName().equalsIgnoreCase(clazz2DeclaredField.getName())) {
                                Object value2 = clazz2DeclaredField.get(obj2);
                                if (!value1.equals(value2)) {
                                    return false;
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }else {
            return false;
        }
        return true;
    }

}

