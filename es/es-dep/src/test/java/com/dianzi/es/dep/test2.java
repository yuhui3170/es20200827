package com.dianzi.es.dep;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dianzi.es.dep.entity.AreaEntity;
import com.dianzi.es.dep.entity.ResourceEntity;
import com.dianzi.es.dep.service.AreaService;
import com.dianzi.es.dep.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/6/30
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class test2 {

    @Autowired
    AreaService areaService;

    @Test
    public void test (){

        List<AreaEntity> list = areaService.list();

        list.forEach((item)->{
            System.out.println(item.toString());
        });
    }
}
