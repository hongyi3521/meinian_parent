package com.hongyi.hongyi.test;

import com.hongyi.dao.TravelItemDao;
import com.hongyi.pojo.TravelItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath:applicationContext-dao.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Test01 {
    @Autowired
    private TravelItemDao travelItemDao;
    @Test
    public void run(){
        TravelItem travelItem = new TravelItem();
        travelItem.setAge("111");
        travelItemDao.add(travelItem);
    }
}
