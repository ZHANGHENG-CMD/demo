package com.example.demo.domain;

import com.example.demo.annotation.Info;
import com.example.demo.annotation.StringConversion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * TODO
 *
 * @author heng.zhang06@hand-china.com
 * @version 1.0
 * @date 2021/3/31 14:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Info(isDelete = true,value = "ject")
public class Person {

    /**
     * 姓名
     */
    @StringConversion(targetValue = "jack",addValueByTarget = {"tom","jery"})
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 是否有效
     */
    private boolean isDelete;

    @StringConversion
    private String ids;
    private List<Long> idsList;
    private List<String> nameList;
    @StringConversion(trim = "/")
    private String big;
    private List<BigDecimal> bigList;
}
