package com.demo.library.mapper;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository
public interface ToolMapper {
    Date now();
}
