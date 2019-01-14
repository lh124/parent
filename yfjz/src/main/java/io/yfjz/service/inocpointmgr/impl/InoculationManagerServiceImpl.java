package io.yfjz.service.inocpointmgr.impl;



import io.yfjz.dao.inocpointmgr.ProcessSetDao;
import io.yfjz.entity.basesetting.*;
import io.yfjz.entity.queue.StepType;
import io.yfjz.service.inocpointmgr.InoculationManagerService;
import io.yfjz.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class InoculationManagerServiceImpl implements InoculationManagerService {

    @Autowired
    private ProcessSetDao setDao;



    @Override
    public void processSet(String param) {
        //先删除以前保存的流程，再插入新的流程
        int ret=setDao.deleteByOrgId(Constant.GLOBAL_ORG_ID);
        if (ret>0) {
            String[] row = param.split(",");
            int order=1;
            for (int i = 0; i <row.length ; i++) {
                String s = row[i];

                String[] split = s.split(":");
                ProcessSetEntity pro = new ProcessSetEntity();
                pro.setCreateTime(new Date());
                pro.setOrgId(Constant.GLOBAL_ORG_ID);

                if (split[0].equals("check")){
                    pro.setTowerNatureId(Constant.TOWER_TYPE_CHILD_CHECK);
                    pro.setTowerName("预检");
                }else if (split[0].equals("childProject")){
                    pro.setTowerNatureId(Constant.TOWER_TYPE_CHILD_HEALTHCARE);
                    pro.setTowerName("儿保");
                }else if(split[0].equals("register")){
                    pro.setTowerNatureId(Constant.TOWER_TYPE_REGISTER);
                    pro.setTowerName("登记");
                }else if(split[0].equals("inoculate")){
                    pro.setTowerNatureId(Constant.TOWER_TYPE_INOCULATE);
                    pro.setTowerName("接种");
                }else if (split[0].equals("look")){
                    pro.setTowerNatureId(Constant.TOWER_TYPE_OBSERVATION);
                    pro.setTowerName("留观");
                }
                if (split[1].equals("undefined")){
                    pro.setSequence(order);
                    order++;
                    pro.setStatus(0);
                }else{
                    pro.setSequence(-1);
                    pro.setStatus(1);
                }
                setDao.save(pro);
            }
        }

    }

    @Override
    public Map<String, Object> processList() {
        List<ProcessSetEntity> list= setDao.queryAll(Constant.GLOBAL_ORG_ID);
        List<Map<String, Object>> noselect = new ArrayList<>();
        List<Map<String, Object>> select = new ArrayList<>();
        for (ProcessSetEntity process : list) {
            switch (process.getTowerNatureId()) {
                case 1:
                    process.setTowerEnName("register");
                    break;
                case 2:
                    process.setTowerEnName("inoculate");
                    break;
                case 3:
                    process.setTowerEnName("childProject");
                    break;
                case 4:
                    process.setTowerEnName("check");
                    break;
                case 5:
                    process.setTowerEnName("look");
                    break;
            }
            Map<String, Object> temp = new HashMap<>();
            temp.put("tag", process.getTowerEnName());
            temp.put("text", process.getTowerName());
            if (process.getSequence() > 0) {

                select.add(temp);
            }else{
                noselect.add(temp);
            }

        }
        Map<String, Object> map = new HashMap<>();
        map.put("noselect",noselect);
        map.put("select",select);
        return map;
    }

    @Override
    public StepType getProcessFirst() {
        Integer ret=setDao.queryProcessFirst();
        switch (ret){
            case 1:return StepType.register;
            case 2:return StepType.inoculate;
            case 3:return StepType.healthcare;
            case 4:return StepType.precheck;
            case 5:return StepType.observe;
            default:return null;
        }
    }

    @Override
    public StepType getNextProcess(Integer type) {
        List<Map<String,Integer>> list=setDao.queryAllList();
        Map<String,Integer> temp = new HashMap<>();
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).get("towerId").equals(type)&&i+1<list.size()){
                temp=list.get(i+1);
                break;
            }
        }
        switch (temp.get("towerId")){
            case 1:return StepType.register;
            case 2:return StepType.inoculate;
            case 3:return StepType.healthcare;
            case 4:return StepType.precheck;
            case 5:return StepType.observe;
            default:return null;
        }

    }


}
