package com.dianzi.es.rep.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dianzi.es.rep.service.UrgentVoService;
import com.dianzi.es.rep.vo.UrgentAnaVo;
import com.dianzi.es.rep.vo.UrgentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dianzi.es.rep.entity.UrgentorderEntity;
import com.dianzi.es.rep.service.UrgentorderService;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.R;



/**
 * 抢修单
 *
 * @author esn
 * @email esn@gmail.com
 * @date 2020-07-09 16:58:07
 */
@RestController
@RequestMapping("rep/urgentorder")
public class UrgentorderController {
    @Autowired
    private UrgentorderService urgentorderService;

    @Autowired
    private UrgentVoService urgentVoService;

    @RequestMapping("/analysis/UrgentData")
    public R UrgentDataAna(){
        List<UrgentAnaVo> list = urgentVoService.UrgentAnaList();
        //System.out.println(list);

        return R.ok().put("data", list);
    }

    @RequestMapping("/feign/all")
    public R esUrgentInfo(){

        List<UrgentVo> list = urgentVoService.findAll();

        return R.ok().put("data", list);
    }

    @RequestMapping("/feign/list")
    public R urgentorders(){
        List<UrgentorderEntity> list = urgentorderService.list();
        return R.ok().put("data", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("rep:urgentorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = urgentorderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("rep:urgentorder:info")
    public R info(@PathVariable("id") Long id){
		UrgentorderEntity urgentorder = urgentorderService.getById(id);

        return R.ok().put("urgentorder", urgentorder);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("rep:urgentorder:save")
    public R save(@RequestBody UrgentorderEntity urgentorder){
		urgentorderService.save(urgentorder);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("rep:urgentorder:update")
    public R update(@RequestBody UrgentorderEntity urgentorder){
		urgentorderService.updateById(urgentorder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("rep:urgentorder:delete")
    public R delete(@RequestBody Long[] ids){
		urgentorderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
