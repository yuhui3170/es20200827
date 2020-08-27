package com.dianzi.es.rep.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dianzi.es.rep.feign.AreaFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.rep.entity.ReportorderEntity;
import com.dianzi.es.rep.service.ReportorderService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 客户报修单
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@RestController
@RequestMapping("rep/reportorder")
public class ReportorderController {
    @Autowired
    private ReportorderService reportorderService;


    @Autowired
    private AreaFeignService areaFeignService;

    @RequestMapping("/feigntest")
    public R test(){

        R repArea = areaFeignService.repArea();
        repArea.get("data");
        return  R.ok().put("area", repArea);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("rep:reportorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = reportorderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("rep:reportorder:info")
    public R info(@PathVariable("id") Long id){
		ReportorderEntity reportorder = reportorderService.getById(id);

        return R.ok().put("reportorder", reportorder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("rep:reportorder:save")
    public R save(@RequestBody ReportorderEntity reportorder){
		reportorderService.save(reportorder);

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/insert")
    //@RequiresPermissions("rep:reportorder:save")
    public R insert(@RequestBody Map<String,Object> params){
        reportorderService.insert(params);
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("rep:reportorder:update")
    public R update(@RequestBody ReportorderEntity reportorder){
		reportorderService.updateById(reportorder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("rep:reportorder:delete")
    public R delete(@RequestBody Long[] ids){
		reportorderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
