package com.dianzi.es.dep.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dianzi.es.dep.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.dep.entity.WorkersCodeEntity;
import com.dianzi.es.dep.service.WorkersCodeService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-10 08:50:03
 */
@RestController
@RequestMapping("dep/workerscode")
public class WorkersCodeController {
    @Autowired
    private WorkersCodeService workersCodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:workerscode:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = workersCodeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cordId}")
    //@RequiresPermissions("dep:workerscode:info")
    public R info(@PathVariable("cordId") Integer cordId){
		WorkersCodeEntity workersCode = workersCodeService.getById(cordId);

        return R.ok().put("workersCode", workersCode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:workerscode:save")
    public R save(@RequestBody WorkersCodeEntity workersCode){
		workersCodeService.save(workersCode);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:workerscode:update")
    public R update(@RequestBody WorkersCodeEntity workersCode){
		workersCodeService.updateById(workersCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:workerscode:delete")
    public R delete(@RequestBody Integer[] cordIds){
		workersCodeService.removeByIds(Arrays.asList(cordIds));

        return R.ok();
    }

    /**
     * 发送验证码
     */
    @RequestMapping("/sendCode")
    //@RequiresPermissions("dep:workerscode:info")
    public R sendCode(@RequestBody Map<String, Object> params){
        Map<String,Object> data = workersCodeService.sendCode(params);
        return R.ok().put("data", data);
    }

    /**
     * 验证验证码
     */
    @RequestMapping("/checkCode")
    //@RequiresPermissions("dep:workerscode:info")
    public R checkCode(@RequestBody Map<String,Object> params){
        Map<String,Object> data = workersCodeService.checkCode(params);
        return R.ok().put("data", data);
    }
}
