package com.dianzi.es.dep.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dianzi.es.dep.service.ApplyAnaService;
import com.dianzi.es.dep.vo.ApplyAnaVo;
import com.dianzi.es.dep.vo.ApplyCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.dep.entity.ApplyinfoEntity;
import com.dianzi.es.dep.service.ApplyinfoService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 设备申请表
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/applyinfo")
public class ApplyinfoController {
    @Autowired
    private ApplyinfoService applyinfoService;

    @Autowired
    private ApplyAnaService applyAnaService;

    @RequestMapping("/analyse/applyAnaInfo")
    public R applyAnaInfoGet(){
        List<ApplyCount> info = applyAnaService.getInfo();

        List<ApplyAnaVo> baseApplyInfo = applyAnaService.getBaseApplyInfo(info);

        return R.ok().put("data",info);

    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:applyinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = applyinfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("dep:applyinfo:info")
    public R info(@PathVariable("id") Long id){
		ApplyinfoEntity applyinfo = applyinfoService.getById(id);

        return R.ok().put("applyinfo", applyinfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:applyinfo:save")
    public R save(@RequestBody ApplyinfoEntity applyinfo){
		applyinfoService.save(applyinfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:applyinfo:update")
    public R update(@RequestBody ApplyinfoEntity applyinfo){
		applyinfoService.updateById(applyinfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:applyinfo:delete")
    public R delete(@RequestBody Long[] ids){
		applyinfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
