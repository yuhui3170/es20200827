package com.dianzi.es.dep.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.AreaDao;
import com.dianzi.es.dep.entity.AreaEntity;
import com.dianzi.es.dep.service.AreaService;


@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, AreaEntity> implements AreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AreaEntity> page = this.page(
                new Query<AreaEntity>().getPage(params),
                new QueryWrapper<AreaEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<AreaEntity> listWithTree() {

        //查询所有的区域地点
        List<AreaEntity> entities = baseMapper.selectList(null);

        //组装成父子的树形结构
        //1、找到一级区域
        //2、递归查询一级区域的下层区域
        List<AreaEntity> provinceArea = entities.stream().filter(areaEntity -> {
            return areaEntity.getParentId() == 0;
        }).map((list)->{
            list.setChildArea(getChildAreaEntities(list,entities));
            return list;
        }).sorted((list1,list2)->{
            return list1.getSort() - list2.getSort();
        }).collect(Collectors.toList());



        return provinceArea;
    }

    @Override
    public void removeAreaByIds(List<Long> asList) {
        //TODO: 1、删除之前检查是否被其他地方引用
        baseMapper.deleteBatchIds(asList);
    }

    //递归查询区域的子区域
    private List<AreaEntity> getChildAreaEntities(AreaEntity areaEntity,List<AreaEntity> alllist){

        List<AreaEntity> childArea = alllist.stream().filter((list) -> {
            return list.getParentId() == areaEntity.getAreaId();
        }).map((list) -> {
            list.setChildArea(getChildAreaEntities(list, alllist));
            return list;
        }).sorted((list1, list2) -> {
            return list1.getSort() - list2.getSort();
        }).collect(Collectors.toList());

        return childArea;
    }

}