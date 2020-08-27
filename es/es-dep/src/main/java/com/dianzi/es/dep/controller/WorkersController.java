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

import com.dianzi.es.dep.entity.WorkersEntity;
import com.dianzi.es.dep.service.WorkersService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 抢修工人
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/workers")
public class WorkersController {
    @Autowired
    private WorkersService workersService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:workers:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = workersService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{workerId}")
    //@RequiresPermissions("dep:workers:info")
    public R info(@PathVariable("workerId") Long workerId){
		WorkersEntity workers = workersService.getById(workerId);

        return R.ok().put("workers", workers);
    }

    /**
     * 根据少量信息获取用户信息
     */
    @RequestMapping("/getByData")
    //@RequiresPermissions("dep:workers:info")
    public R getByData(@RequestBody Map<String,Object> params){
        WorkersEntity workers = workersService.getByData(params);
        return R.ok().put("workers", workers);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:workers:save")
    public R save(@RequestBody WorkersEntity workers){
		workersService.save(workers);

        return R.ok();
    }

    /**
     * 限制保存
     */
    @RequestMapping("/save_limit")
    //@RequiresPermissions("dep:workers:save")
    public R save_limit(@RequestBody Map<String,Object> params){
        Map<String, Object> data = workersService.save_limit(params);
        return R.ok().put("data",data);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:workers:update")
    public R update(@RequestBody WorkersEntity workers){
		workersService.updateById(workers);

        return R.ok();
    }

    /**
     * 限制修改
     */
    @RequestMapping("/update_limit")
    //@RequiresPermissions("dep:workers:update")
    public R update_limit(@RequestBody Map<String,Object> params){
        Map<String, Object> data = workersService.update_limit(params);
        return R.ok().put("data",data);
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:workers:delete")
    public R delete(@RequestBody Long[] workerIds){
		workersService.removeByIds(Arrays.asList(workerIds));

        return R.ok();
    }

}
