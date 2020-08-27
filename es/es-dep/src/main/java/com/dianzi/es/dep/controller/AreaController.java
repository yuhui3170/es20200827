package com.dianzi.es.dep.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.dep.entity.AreaEntity;
import com.dianzi.es.dep.service.AreaService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 区域信息
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/area")
public class AreaController {
    @Autowired
    private AreaService areaService;


    @RequestMapping("/rep/list")
    public R repArea(){
        AreaEntity areaEntity = new AreaEntity();
        areaEntity.setAreaName("远程调用测试的区域");
        return R.ok().put("data", Arrays.asList(areaEntity));
    }

    /**
     * 获取地区树形结构
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("dep:area:list")
    public R listWithTree(){

        List<AreaEntity> entities =  areaService.listWithTree();

        return R.ok().put("data", entities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:area:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = areaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{areaId}")
    //@RequiresPermissions("dep:area:info")
    public R info(@PathVariable("areaId") Long areaId){
		AreaEntity area = areaService.getById(areaId);

        return R.ok().put("area", area);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:area:save")
    public R save(@RequestBody AreaEntity area){
		areaService.save(area);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:area:update")
    public R update(@RequestBody AreaEntity area){
		areaService.updateById(area);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:area:delete")
    public R delete(@RequestBody Long[] areaIds){
		//areaService.removeByIds(Arrays.asList(areaIds));

        //批量删除之前需要判断是否被别的地方引用
		areaService.removeAreaByIds(Arrays.asList(areaIds));

        return R.ok();
    }

}
