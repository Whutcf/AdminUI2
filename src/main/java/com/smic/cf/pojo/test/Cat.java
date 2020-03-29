package com.smic.cf.pojo.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description for test
 * @ClassName Dog
 * @Author 蔡明涛
 * @date 2020.03.03 00:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {
    private int id;
    private String cname;
    private int age;
    private String nickname;
}