package com.example.demo.common.entity;


import com.example.demo.domain.Person;

import java.util.Objects;

/**
 * 主数据分组DTO
 */
public class BasicDataGroupDTO {
    /**
     * 主键
     */
    private Long id;
    /**
     * 类型
     */
    private BasicDataType type;


    public enum BasicDataType{
        COMPANY("COMPANY","公司", Person.class);

        BasicDataType(String code,String meaning,Class cls){
            this.code = code;
            this.meaning = meaning;
            this.cls = cls;
        }

        private String code;
        private String meaning;
        private Class cls;


        public boolean equals(BasicDataType basicDataType){
            if(Objects.nonNull(basicDataType) &&
                Objects.requireNonNull(basicDataType.getCode()).equals(this.getCode())){
                return true;
            }
            return false;
        }
        /*************getter and setter******************/
        public String getCode() {
            return code;
        }

        public BasicDataType setCode(String code) {
            this.code = code;
            return this;
        }

        public String getMeaning() {
            return meaning;
        }

        public BasicDataType setMeaning(String meaning) {
            this.meaning = meaning;
            return this;
        }

        public Class getCls() {
            return cls;
        }

        public BasicDataType setCls(Class cls) {
            this.cls = cls;
            return this;
        }
    }

    public boolean equals(BasicDataGroupDTO basicDataGroupDTO){
        return Objects.nonNull(basicDataGroupDTO) &&
                Objects.requireNonNull(basicDataGroupDTO.getId()).equals(this.getId()) &&
                basicDataGroupDTO.getType().equals(basicDataGroupDTO.getType());
    }
    /*************getter and setter******************/
    public Long getId() {
        return id;
    }

    public BasicDataGroupDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public BasicDataType getType() {
        return type;
    }

    public BasicDataGroupDTO setType(BasicDataType type) {
        this.type = type;
        return this;
    }
}
