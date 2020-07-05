package com.mobileclient.domain.result;

/**
 * Created by user on 2020/6/29.
 */

public class SignResult {
    String signNum;
    String stuNum;

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    @Override
    public String toString() {
        return "SignResult{" +
                "signNum='" + signNum + '\'' +
                ", stuNum='" + stuNum + '\'' +
                '}';
    }
}
