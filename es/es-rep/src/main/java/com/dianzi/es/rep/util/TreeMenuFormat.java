package com.dianzi.es.rep.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TreeMenuFormat {
	public static List<Map<String,Object>> formatMenuList(List<Map<String, Object>> menusList,String id,String parentId){
		List<Map<String,Object>> rList = new LinkedList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();	
		
		for (Map<String, Object> m : menusList) {
			map.put(m.get(id)+"", m);
		}
		
		for (Map<String, Object> m : menusList) {
			String pmid = m.get(parentId)+"";
			if(Utils.isEmpty(pmid)){
				rList.add(m);
			}else{
				Map<String,Object> pMap = (Map<String, Object>) map.get(pmid);
				if(pMap == null){
					rList.add(m);
					continue;
				}
				List childList = (List) pMap.get("child");
				if(childList == null){
					childList = new LinkedList();
					pMap.put("child", childList);
				}
				childList.add(m);
			}
		}
		return rList;
	}
}
