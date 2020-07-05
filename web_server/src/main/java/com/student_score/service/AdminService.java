package com.student_score.service;

import com.student_score.dao.AdminDao;
import com.student_score.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: android_api
 * @description:
 * @author: libin
 * @create: 2020-06-28 05:53
 **/
@Service
public class AdminService {
    @Resource
    AdminDao adminDao;

    @Transactional
    void insertAdmin(Admin admin){
        adminDao.insertAdmin(admin);
    }

    @Transactional
    void delAdmin(Admin admin){
        adminDao.delAdmin(admin);
    }

    public List<Admin> queryAdminByName(String username){
        return adminDao.queryAdminByName(username);
    }

}
