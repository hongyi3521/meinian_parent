package com.hongyi.dao;

import com.hongyi.pojo.Member;

public interface MemberDao {
    // 根据手机号查询会员信息（唯一）
    Member findByTelephone(String telephone);
    // 添加会员
    void add(Member member);

    Integer findMemberCountBeforeDate(String regTime);

    int getTodayNewMember(String today);

    int getTotalMember();

    int getThisWeekAndMonthNewMember(String weekMonday);
}
