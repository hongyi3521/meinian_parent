package com.hongyi.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hongyi.dao.MemberDao;
import com.hongyi.pojo.Member;
import com.hongyi.service.MemberService;
import com.hongyi.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer> memberCountList = new ArrayList<>();
        // 验证
        if(list!=null && list.size()>0){
            // 遍历
            for(String month : list){
                // String regTime = months+"-31";
                // 获取指定月份的最后一天
                String regTime =  DateUtils.getLastDayOfMonth(month);
                //  迭代过去12个月，每个月注册会员的数量，根据注册日期查询
                Integer memeberCount = memberDao.findMemberCountBeforeDate(regTime);
                memberCountList.add(memeberCount);
            }
        }
        return memberCountList;

    }
}
