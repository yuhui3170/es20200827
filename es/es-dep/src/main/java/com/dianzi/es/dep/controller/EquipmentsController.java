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

import com.dianzi.es.dep.entity.EquipmentsEntity;
import com.dianzi.es.dep.service.EquipmentsService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 设备资源表
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 11:37:13
 */
@RestController
@RequestMapping("dep/equipments")
public class EquipmentsController {
    @Autowired
    private EquipmentsService equipmentsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:equipments:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = equipmentsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{equipId}")
    //@RequiresPermissions("dep:equipments:info")
    public R info(@PathVariable("equipId") Long equipId){
		EquipmentsEntity equipments = equipmentsService.getById(equipId);

        return R.ok().put("equipments", equipments);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:equipments:save")
    public R save(@RequestBody EquipmentsEntity equipments){
		equipmentsService.save(equipments);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:equipments:update")
    public R update(@RequestBody EquipmentsEntity equipments){
		equipmentsService.updateById(equipments);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:equipments:delete")
    public R delete(@RequestBody Long[] equipIds){
		equipmentsService.removeByIds(Arrays.asList(equipIds));

        return R.ok();
    }

}
