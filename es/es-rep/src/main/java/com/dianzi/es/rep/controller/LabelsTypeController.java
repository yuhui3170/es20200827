package com.dianzi.es.rep.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dianzi.es.rep.service.LableOrderAnaService;
import com.dianzi.es.rep.vo.LabelOrderAnaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.rep.entity.LabelsTypeEntity;
import com.dianzi.es.rep.service.LabelsTypeService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-08-14 15:03:22
 */
@RestController
@RequestMapping("rep/labelstype")
public class LabelsTypeController {
    @Autowired
    private LabelsTypeService labelsTypeService;

    @Autowired
    private LableOrderAnaService lableOrderAnaService;


    @RequestMapping("/analysis/LabelOrders")
    public R LabelOrdersAna(){
        //List<LabelOrderAnaVo> data = lableOrderAnaService.GetOrdersByTypesMonths();

        List<LabelOrderAnaVo> data = lableOrderAnaService.GetLabelAna();


        return R.ok().put("data",data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("dep:labelstype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = labelsTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{typeId}")
    //@RequiresPermissions("dep:labelstype:info")
    public R info(@PathVariable("typeId") Integer typeId){
		LabelsTypeEntity labelsType = labelsTypeService.getById(typeId);

        return R.ok().put("labelsType", labelsType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("dep:labelstype:save")
    public R save(@RequestBody LabelsTypeEntity labelsType){
		labelsTypeService.save(labelsType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("dep:labelstype:update")
    public R update(@RequestBody LabelsTypeEntity labelsType){
		labelsTypeService.updateById(labelsType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("dep:labelstype:delete")
    public R delete(@RequestBody Integer[] typeIds){
		labelsTypeService.removeByIds(Arrays.asList(typeIds));

        return R.ok();
    }

}
