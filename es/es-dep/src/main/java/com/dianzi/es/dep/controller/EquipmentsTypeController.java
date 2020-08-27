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

import com.dianzi.es.dep.entity.EquipmentsTypeEntity;
import com.dianzi.es.dep.service.EquipmentsTypeService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-14 10:46:00
 */
@RestController
@RequestMapping("dep/equipmentstype")
public class EquipmentsTypeController {
    @Autowired
    private EquipmentsTypeService equipmentsTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:equipmentstype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = equipmentsTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{typeId}")
    //@RequiresPermissions("dep:equipmentstype:info")
    public R info(@PathVariable("typeId") Integer typeId){
		EquipmentsTypeEntity equipmentsType = equipmentsTypeService.getById(typeId);

        return R.ok().put("equipmentsType", equipmentsType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:equipmentstype:save")
    public R save(@RequestBody EquipmentsTypeEntity equipmentsType){
		equipmentsTypeService.save(equipmentsType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:equipmentstype:update")
    public R update(@RequestBody EquipmentsTypeEntity equipmentsType){
		equipmentsTypeService.updateById(equipmentsType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:equipmentstype:delete")
    public R delete(@RequestBody Integer[] typeIds){
		equipmentsTypeService.removeByIds(Arrays.asList(typeIds));

        return R.ok();
    }

}
