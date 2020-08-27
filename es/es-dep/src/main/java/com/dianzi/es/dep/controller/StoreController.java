package com.dianzi.es.dep.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.dep.entity.StoreEntity;
import com.dianzi.es.dep.service.StoreService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 资源所属仓库
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:store:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = storeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{storeId}")
    //@RequiresPermissions("dep:store:info")
    public R info(@PathVariable("storeId") Long storeId){
		StoreEntity store = storeService.getById(storeId);

        return R.ok().put("store", store);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:store:save")
    public R save(@RequestBody StoreEntity store){
		storeService.save(store);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:store:update")
    public R update(@RequestBody StoreEntity store){
		storeService.updateById(store);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:store:delete")
    public R delete(@RequestBody Long[] storeIds){
		storeService.removeByIds(Arrays.asList(storeIds));

        return R.ok();
    }

}
