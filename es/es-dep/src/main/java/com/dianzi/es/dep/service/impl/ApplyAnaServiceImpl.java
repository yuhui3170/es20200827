package com.dianzi.es.dep.service.impl;

import com.dianzi.common.vo.ApplyInfoBaseInfo;
import com.dianzi.common.vo.BaseInfo;
import com.dianzi.es.dep.dao.ApplyAnaDao;
import com.dianzi.es.dep.dao.StoreDao;
import com.dianzi.es.dep.entity.StoreEntity;
import com.dianzi.es.dep.service.ApplyAnaService;
import com.dianzi.es.dep.vo.ApplyAnaVo;
import com.dianzi.es.dep.vo.ApplyCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/8/17
 */

@Service("ApplyAnaService")
public class ApplyAnaServiceImpl implements ApplyAnaService {

    @Autowired
    private ApplyAnaDao applyAnaDao;

    @Override
    public List<ApplyCount> getInfo() {
        List<ApplyCount> info = applyAnaDao.getInfo();


        return info;
    }

    @Override
    public List<ApplyAnaVo> getBaseApplyInfo(List<ApplyCount> info) {

        List<String> storeNamelist = new ArrayList<>();

        for (ApplyCount applyCount : info) {
            if (!storeNamelist.contains(applyCount.getStoreName())) {
                storeNamelist.add(applyCount.getStoreName());
            }
        }

        List<ApplyAnaVo> applyAnaVoList = new ArrayList<ApplyAnaVo>();

        for (String storeName : storeNamelist) {
            applyAnaVoList.add(new ApplyAnaVo(storeName, new ArrayList<ApplyInfoBaseInfo>()));
        }


        for (ApplyAnaVo applyAnaVo : applyAnaVoList) {
            List<ApplyInfoBaseInfo> applyInfoBaseInfoList = new ArrayList<ApplyInfoBaseInfo>();
            List<String> typeNamelist = new ArrayList<>();

            for (ApplyCount applyCount : info) {
                if (applyAnaVo.getStoreName().equals(applyCount.getStoreName())) {
                    if (!typeNamelist.contains(applyCount.getTypeName())) {
                        typeNamelist.add(applyCount.getTypeName());

                        ApplyInfoBaseInfo applyInfoBaseInfo = new ApplyInfoBaseInfo();
                        applyInfoBaseInfo.setEquipTypeName(applyCount.getTypeName());

                        applyInfoBaseInfoList.add(applyInfoBaseInfo);
                    }
                }
            }

            typeNamelist.clear();

            applyAnaVo.setApplyInfo(applyInfoBaseInfoList);

        }




        for (ApplyAnaVo applyAnaVo : applyAnaVoList) {

            List<ApplyInfoBaseInfo> applyInfo = applyAnaVo.getApplyInfo();

            for (ApplyInfoBaseInfo applyInfoBaseInfo : applyInfo) {

                List<BaseInfo> baseInfoList = new ArrayList<BaseInfo>();
                for (ApplyCount applyCount : info) {


                    if(applyCount.getStoreName().equals(applyAnaVo.getStoreName())
                            && applyCount.getTypeName().equals(applyInfoBaseInfo.getEquipTypeName())){

                        //applyInfoBaseInfo.setEquipTypeName(applyCount.getEquipName());
                        baseInfoList.add(new BaseInfo(applyCount.getEquipName(),applyCount.getCounts()));

                    }
                }

                applyInfoBaseInfo.setInfo(baseInfoList);

            }
        }




        return applyAnaVoList;
    }
}
