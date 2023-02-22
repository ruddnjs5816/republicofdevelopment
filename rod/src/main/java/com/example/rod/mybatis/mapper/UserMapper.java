package com.example.rod.mybatis.mapper;

import com.example.rod.mybatis.vo.QuestionMapperVO;
import com.example.rod.mybatis.vo.UserMapperVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    UserMapperVO selectUser(@Param("userId") Long userId);

}
