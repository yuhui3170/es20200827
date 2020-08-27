package com.dianzi.es.dep;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dianzi.es.dep.entity.ResourceEntity;
import com.dianzi.es.dep.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EsDepApplicationTests {

    @Autowired
    ResourceService resourceService;

    @Test
    public void contextLoads() {


        List<ResourceEntity> resource =
                resourceService.list(new QueryWrapper<ResourceEntity>().eq("resource_id", 1L));

        resource.forEach((item)->{
            System.out.println(item);
        });

//        ResourceEntity resourceEntity = new ResourceEntity();
//
//        resourceEntity.setResourceName("test");
//
//        resourceService.save(resourceEntity);
    }

}
