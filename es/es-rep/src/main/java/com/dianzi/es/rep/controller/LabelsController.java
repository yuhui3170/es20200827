package com.dianzi.es.rep.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.rep.entity.LabelsEntity;
import com.dianzi.es.rep.service.LabelsService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 事故标签
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@RestController
@RequestMapping("rep/labels")
public class LabelsController {
    @Autowired
    private LabelsService labelsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("rep:labels:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = labelsService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{labelId}")
    //@RequiresPermissions("rep:labels:info")
    public R info(@PathVariable("labelId") Long labelId){
		LabelsEntity labels = labelsService.getById(labelId);

        return R.ok().put("labels", labels);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("rep:labels:save")
    public R save(@RequestBody LabelsEntity labels){
		labelsService.save(labels);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("rep:labels:update")
    public R update(@RequestBody LabelsEntity labels){
		labelsService.updateById(labels);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("rep:labels:delete")
    public R delete(@RequestBody Long[] labelIds){
		labelsService.removeByIds(Arrays.asList(labelIds));

        return R.ok();
    }

}
