package com.dianzi.es.dep.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dianzi.common.valid.AddGroup;
import com.dianzi.common.valid.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.dep.entity.ResourceEntity;
import com.dianzi.es.dep.service.ResourceService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;

import javax.validation.Valid;


/**
 * 部门资源
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/ResourcesGet")
    public R ResourcesGet(@RequestParam Map<String, Object> params){
        List<ResourceEntity> data = resourceService.list();

        return R.ok().put("data", data);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:resource:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = resourceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{resourceId}")
    //@RequiresPermissions("dep:resource:info")
    public R info(@PathVariable("resourceId") Long resourceId){
		ResourceEntity resource = resourceService.getById(resourceId);

        return R.ok().put("resource", resource);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:resource:save")
    public R save(@RequestBody ResourceEntity resource){
        resourceService.save(resource);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:resource:update")
    public R update(@RequestBody ResourceEntity resource){
		resourceService.updateById(resource);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:resource:delete")
    public R delete(@RequestBody Long[] resourceIds){
		resourceService.removeByIds(Arrays.asList(resourceIds));

        return R.ok();
    }

}
