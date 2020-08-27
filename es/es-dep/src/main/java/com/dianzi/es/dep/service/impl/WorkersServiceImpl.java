package com.dianzi.es.dep.service.impl;

import com.dianzi.es.dep.Utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.WorkersDao;
import com.dianzi.es.dep.entity.WorkersEntity;
import com.dianzi.es.dep.service.WorkersService;
import org.springframework.util.DigestUtils;


@Service("workersService")
public class WorkersServiceImpl extends ServiceImpl<WorkersDao, WorkersEntity> implements WorkersService {
    @Autowired
    private WorkersDao workersDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String workerName = (String) params.get("workerName");
        String workMobile = (String) params.get("workMobile");
        String resourceId = (String) params.get("resourceId");
        IPage<WorkersEntity> page = this.page(
                new Query<WorkersEntity>().getPage(params),
                new QueryWrapper<WorkersEntity>()
                        .like(StringUtils.isNotBlank(workerName),"worker_name", workerName)
                        .eq(resourceId != null,"resource_id", resourceId)
                        .or()
                        .like(StringUtils.isNotBlank(workMobile),"work_mobile", workMobile)
                        .eq(resourceId != null,"resource_id", resourceId)
        );

        return new PageUtils(page);
    }

    @Override
    public WorkersEntity getByData(Map<String, Object> params) {
        //如果传过来的有密码password，表示登录，则要加密密码后传递
        String password = (String) params.get("password");
        if(!Utils.isEmpty(password)){
            params.put("password",DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        return workersDao.getByData(params);
    }

    @Override
    public Map<String,Object> save_limit(Map<String, Object> params) {
        //status：0-新增成功  1-手机号重复  2-用户名重复
        String workMobile = (String) params.get("workMobile");
        //根据手机号搜索用户，如果有的话禁止插入，并返回状态码1
        WorkersEntity worker = workersDao.getByData(Utils.makeQueryMap("mobile", workMobile));
        if(!Utils.isEmpty(worker)){
            return Utils.makeQueryMap("status","1");
        }
        //根据用户名搜索用户，如果有的话禁止插入，并返回状态码2
        worker = workersDao.getByData(Utils.makeQueryMap("username", params.get("workNickname")));
        if(!Utils.isEmpty(worker)){
            return Utils.makeQueryMap("status","2");
        }
        workersDao.save_limit(Utils.makeQueryMap(params,"workPassword",DigestUtils.md5DigestAsHex(workMobile.substring(5).getBytes())));
        return Utils.makeQueryMap("status","0");
    }

    @Override
    public Map<String,Object> update_limit(Map<String, Object> params) {
        //status：0-修改成功  1-手机号重复  2-用户名重复
        String workMobile = (String) params.get("workMobile");
        //根据手机号搜索用户，如果有的话禁止修改，并返回状态码1
        WorkersEntity worker = workersDao.getByData(Utils.makeQueryMap("mobile", workMobile,"workerId",params.get("workerId")));
        if(!Utils.isEmpty(worker)){
            return Utils.makeQueryMap("status","1");
        }
        //根据用户名搜索用户，如果有的话禁止修改，并返回状态码2
        worker = workersDao.getByData(Utils.makeQueryMap("username", params.get("workNickname"),"workerId",params.get("workerId")));
        if(!Utils.isEmpty(worker)){
            return Utils.makeQueryMap("status","2");
        }
        workersDao.update_limit(Utils.makeQueryMap(params,"workPassword",DigestUtils.md5DigestAsHex(workMobile.substring(5).getBytes())));
        return Utils.makeQueryMap("status","0");
    }
}