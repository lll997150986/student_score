package com.student_score.dao;

import com.student_score.domain.Admin;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDao {

    void insertAdmin(Admin admin);
    void delAdmin(Admin admin);
    List<Admin> queryAdminByName(String username);
}
