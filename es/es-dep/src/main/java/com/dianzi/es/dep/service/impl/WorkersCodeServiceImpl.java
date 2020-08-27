package com.dianzi.es.dep.service.impl;

import com.dianzi.es.dep.Utils.MD5Util;
import com.dianzi.es.dep.Utils.Utils;
import com.dianzi.es.dep.dao.WorkersDao;
import com.dianzi.es.dep.entity.WorkersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.Random;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dianzi.common.utils.PageUtils;
import com.dianzi.common.utils.Query;

import com.dianzi.es.dep.dao.WorkersCodeDao;
import com.dianzi.es.dep.entity.WorkersCodeEntity;
import com.dianzi.es.dep.service.WorkersCodeService;
import org.springframework.util.DigestUtils;


@Service("workersCodeService")
public class WorkersCodeServiceImpl extends ServiceImpl<WorkersCodeDao, WorkersCodeEntity> implements WorkersCodeService {
    @Autowired
    private WorkersCodeDao workersCodeDao;
    @Autowired
    private WorkersDao workersDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WorkersCodeEntity> page = this.page(
                new Query<WorkersCodeEntity>().getPage(params),
                new QueryWrapper<WorkersCodeEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public Map<String,Object> checkCode(Map<String, Object> params) {
        String type = (String) params.get("type");      //判断登录还是注册
        String code = (String) params.get("code");
        String mobile = (String) params.get("mobile");
        WorkersCodeEntity workersCode = workersCodeDao.getByMobile(params);
        //验证状态：0-验证码正确  1-验证码有误或过期
        String status = "1";
        if(!Utils.isEmpty(code)&&!Utils.isEmpty(workersCode)&&code.equals(workersCode.getCode())){
            status = "0";
            //如果是注册，还要生成新用户信息于数据库中，密码默认手机号后6位
            if("register".equals(type)){
                WorkersEntity worker = new WorkersEntity();
                worker.setWorkerName("用户"+mobile.substring(7));
                worker.setWorkNickname("用户"+mobile.substring(7));
                worker.setWorkMobile(mobile);
                worker.setWorkPassword(DigestUtils.md5DigestAsHex(mobile.substring(5).getBytes()));
                workersDao.insert(worker);
            }
            //如果是修改密码，还要修改用户密码
            if("chgPassword".equals(type)){
                workersDao.updateByMobile(Utils.makeQueryMap("mobile",mobile,"password",DigestUtils.md5DigestAsHex(((String)params.get("passwordOne")).getBytes())));
            }
            //如果是修改手机号，还要修改手机号
            if("chgMobile".equals(type)){
                workersDao.updateMobileByMobile(Utils.makeQueryMap("mobile",mobile,"oldMobile",params.get("oldMobile")));
            }
            //根据手机号获取用户信息
            WorkersEntity workers = workersDao.getByData(Utils.makeQueryMap("mobile",mobile));
            return Utils.makeQueryMap("status",status,"workers",workers);
        }
        return Utils.makeQueryMap("status",status);
    }

    @Override
    public Map<String,Object> sendCode(Map<String, Object> params) {
        //状态码：0-已发送验证码    1-想登录但无该用户    2-想注册但已有该用户
        String type = (String) params.get("type");      //判断登录还是注册
        //1. 根据手机号查找该用户
        WorkersEntity workers = workersDao.getByData(Utils.makeQueryMap("mobile",params.get("mobile")));
        //1.1 想登录(修改密码)但无该用户，直接返回状态码1
        if(Utils.isEmpty(workers) && ("login".equals(type) || "chgPassword".equals(type))){
            return Utils.makeQueryMap("status","1");
        }
        //1.2 想注册但已有该用户，直接返回状态码2
        if(!Utils.isEmpty(workers) && "register".equals(type)){
            return Utils.makeQueryMap("status","2");
        }
        //2. 根据手机号获取未过期的验证码
        WorkersCodeEntity workersCode = workersCodeDao.getByMobile(params);
        String code = "";
        //2.1 若有以此码作为验证码
        if(!Utils.isEmpty(workersCode)){
            code = workersCode.getCode();
        }
        //2.2 若无生成新验证码并保存数据库
        else{
            //生成新验证码、保存(验证码五分钟内有效)
            code = String.format("%04d",new Random().nextInt(9999));
            params.put("code",code);
            workersCodeDao.saveCode(params);
        }
        //3. 调用第三方接口发送短信验证码

        return Utils.makeQueryMap("status","0");
    }

}