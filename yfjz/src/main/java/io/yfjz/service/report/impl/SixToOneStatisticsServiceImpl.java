package io.yfjz.service.report.impl;

import io.yfjz.dao.statistics.SixToOneStatisticsDao;
import io.yfjz.entity.statistics.SixToOneResult;
import io.yfjz.managerservice.rule.common.PlanDicContext;
import io.yfjz.service.jwxplat.ServiceChild;
import io.yfjz.service.jwxplat.ServiceChildServiceLocator;
import io.yfjz.service.report.SixToOneStatisticsService;
import io.yfjz.utils.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * create by tianjinhai on 2018/9/29 9:55
 */
@Service
public class SixToOneStatisticsServiceImpl implements SixToOneStatisticsService {
    private static ServiceChildServiceLocator serviceChildServiceLocator = new ServiceChildServiceLocator();
    @Autowired
    private SixToOneStatisticsDao sixToOneDao;


    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> queryMap, String residence) {
        List<SixToOneResult> list = sixToOneDao.queryList(queryMap);
        Map<String, List<SixToOneResult>> result = list.parallelStream().collect(Collectors.groupingBy(SixToOneResult::getCommittee));
        List<Map<String, Object>>  ret = new ArrayList<>();
        Collection<List<SixToOneResult>> values = result.values();
        for (List<SixToOneResult> value : values) {
//            System.out.println(value);
            Map<String, Object> map = new TreeMap<>();
            for (SixToOneResult sixToOneResult : value) {
                map.put("committee",sixToOneResult.getCommittee());
                if (residence.equals("local")){
                    //常住应种
                    map.put(sixToOneResult.getVaccine()+sixToOneResult.getTimes()+"s",sixToOneResult.getLocalShould());
                    //常住实种
                    map.put(sixToOneResult.getVaccine()+sixToOneResult.getTimes()+"r",sixToOneResult.getLocalReal());
                }else if (residence.equals("move")){
                    //流动应种
                    map.put(sixToOneResult.getVaccine()+sixToOneResult.getTimes()+"s",sixToOneResult.getMoveShould());
                    //流动实种
                    map.put(sixToOneResult.getVaccine()+sixToOneResult.getTimes()+"r",sixToOneResult.getMoveReal());
                }else if (residence.equals("total")){
                    //合计应种
                    map.put(sixToOneResult.getVaccine()+sixToOneResult.getTimes()+"s",sixToOneResult.getMoveShould()+sixToOneResult.getLocalShould());
                    //合计实种
                    map.put(sixToOneResult.getVaccine()+sixToOneResult.getTimes()+"r",sixToOneResult.getMoveReal()+sixToOneResult.getLocalReal());
                }
            }
            ret.add(map);
        }
        //计算乙肝第一针及时针次
        List<SixToOneResult>  firstTimely =  sixToOneDao.articleFirstTimely(queryMap);
        for (Map<String, Object> map : ret) {
            Object committee = map.get("committee");
            for (SixToOneResult tmp : firstTimely) {

                if (committee.equals(tmp.getCommittee())){
                    if (residence.equals("local")){
                        //常住实种
                        map.put("HepB1T",tmp.getLocalReal());
                    }else if (residence.equals("move")){
                        //流动实种
                        map.put("HepB1T",tmp.getMoveReal());
                    }else if (residence.equals("total")){
                        //合计实种
                        map.put("HepB1T",tmp.getMoveReal()+tmp.getLocalReal());
                    }
                }

            }
        }
        return ret;
    }

    @Override
    public List<SixToOneResult> queryTotalList(Map<String, Object> map, String residence) {
        List<SixToOneResult> list = new ArrayList<>(35);
        //乙肝
        SixToOneResult hepB1 = new SixToOneResult();//1机
        hepB1.setVaccine(PlanDicContext.HepB);
        hepB1.setTimes(1);
        list.add(hepB1);
        SixToOneResult hepB1v = new SixToOneResult();//1及时剂
        hepB1v.setVaccine(PlanDicContext.HepB);
        hepB1v.setTimes(1);
        hepB1v.setCommittee("hepb");
        list.add(hepB1v);
        SixToOneResult hepB2 = new SixToOneResult();//2剂
        hepB2.setVaccine(PlanDicContext.HepB);
        hepB2.setTimes(2);
        list.add(hepB2);
        SixToOneResult hepB3 = new SixToOneResult();//3剂
        hepB3.setVaccine(PlanDicContext.HepB);
        hepB3.setTimes(3);
        list.add(hepB3);
        //卡介
        SixToOneResult bcg = new SixToOneResult();//1剂
        bcg.setVaccine(PlanDicContext.BCG);
        bcg.setTimes(1);
        list.add(bcg);
        //脊灰
        SixToOneResult ipv = new SixToOneResult();//1剂
        ipv.setVaccine(PlanDicContext.IPV);
        ipv.setTimes(1);
        list.add(ipv);
        SixToOneResult opv1 = new SixToOneResult();//2剂
        opv1.setVaccine(PlanDicContext.OPV);
        opv1.setTimes(1);
        list.add(opv1);
        SixToOneResult opv2 = new SixToOneResult();//2剂
        opv2.setVaccine(PlanDicContext.OPV);
        opv2.setTimes(2);
        list.add(opv2);
        SixToOneResult opv3 = new SixToOneResult();//3剂
        opv3.setVaccine(PlanDicContext.OPV);
        opv3.setTimes(3);
        list.add(opv3);
        //百白破
        SixToOneResult dTaP1 = new SixToOneResult();//1剂
        dTaP1.setVaccine(PlanDicContext.DTaP);
        dTaP1.setTimes(1);
        list.add(dTaP1);
        SixToOneResult dTaP2 = new SixToOneResult();//2剂
        dTaP2.setVaccine(PlanDicContext.DTaP);
        dTaP2.setTimes(2);
        list.add(dTaP2);
        SixToOneResult dTaP3 = new SixToOneResult();//3剂
        dTaP3.setVaccine(PlanDicContext.DTaP);
        dTaP3.setTimes(3);
        list.add(dTaP3);
        SixToOneResult dTaP4 = new SixToOneResult();//4剂
        dTaP4.setVaccine(PlanDicContext.DTaP);
        dTaP4.setTimes(4);
        list.add(dTaP3);
        //百破
        SixToOneResult dT1 = new SixToOneResult();//1剂
        dT1.setVaccine(PlanDicContext.DT);
        dT1.setTimes(1);
        list.add(dT1);
        //麻风
        SixToOneResult mr1 = new SixToOneResult();//1剂
        mr1.setVaccine(PlanDicContext.MR);
        mr1.setTimes(1);
        list.add(mr1);
        SixToOneResult mr2 = new SixToOneResult();//2剂
        mr2.setVaccine(PlanDicContext.MR);
        mr2.setTimes(2);
        list.add(mr2);
        //麻腮风

        SixToOneResult mmr1 = new SixToOneResult();//1剂
        mmr1.setVaccine(PlanDicContext.MMR);
        mmr1.setTimes(1);
        list.add(mmr1);
        SixToOneResult mmr2 = new SixToOneResult();//2剂
        mmr2.setVaccine(PlanDicContext.MMR);
        mmr2.setTimes(2);
        list.add(mmr2);
        //麻腮
        SixToOneResult ms1 = new SixToOneResult();//1剂
        ms1.setVaccine("13");
        ms1.setTimes(1);
        list.add(ms1);
        SixToOneResult ms2 = new SixToOneResult();//2剂
        ms2.setVaccine("13");
        ms2.setTimes(2);
        list.add(ms2);
        //麻疹
        SixToOneResult mz1 = new SixToOneResult();//1剂
        mz1.setVaccine("09");
        mz1.setTimes(1);
        list.add(mz1);
        SixToOneResult mz2 = new SixToOneResult();//2剂
        mz2.setVaccine("09");
        mz2.setTimes(2);
        list.add(mz2);
        //A群
        SixToOneResult mPSV_A1 = new SixToOneResult();//1剂
        mPSV_A1.setVaccine(PlanDicContext.MPSV_A);
        mPSV_A1.setTimes(1);
        list.add(mPSV_A1);
        SixToOneResult mPSV_A2 = new SixToOneResult();//2剂
        mPSV_A2.setVaccine(PlanDicContext.MPSV_A);
        mPSV_A2.setTimes(2);
        list.add(mPSV_A2);
        //A+C群
        SixToOneResult mPSV_AC1 = new SixToOneResult();//1剂
        mPSV_AC1.setVaccine(PlanDicContext.MPSV_AC);
        mPSV_AC1.setTimes(1);
        list.add(mPSV_AC1);
        SixToOneResult mPSV_AC2 = new SixToOneResult();//2剂
        mPSV_AC2.setVaccine(PlanDicContext.MPSV_AC);
        mPSV_AC2.setTimes(2);
        list.add(mPSV_AC2);
        //乙脑减毒
        SixToOneResult jE_L1 = new SixToOneResult();//1剂
        jE_L1.setVaccine(PlanDicContext.JE_L);
        jE_L1.setTimes(1);
        list.add(jE_L1);
        SixToOneResult jE_L2 = new SixToOneResult();//2剂
        jE_L2.setVaccine(PlanDicContext.JE_L);
        jE_L2.setTimes(2);
        list.add(jE_L2);
        //乙脑灭活
        SixToOneResult jE_I1 = new SixToOneResult();//1剂
        jE_I1.setVaccine(PlanDicContext.JE_I);
        jE_I1.setTimes(1);
        list.add(jE_I1);
        SixToOneResult jE_I2 = new SixToOneResult();//2剂
        jE_I2.setVaccine(PlanDicContext.JE_I);
        jE_I2.setTimes(2);
        list.add(jE_I2);
        SixToOneResult jE_I3 = new SixToOneResult();//3剂
        jE_I3.setVaccine(PlanDicContext.JE_I);
        jE_I3.setTimes(3);
        list.add(jE_I3);
        SixToOneResult jE_I4 = new SixToOneResult();//4剂
        jE_I4.setVaccine(PlanDicContext.JE_I);
        jE_I4.setTimes(4);
        list.add(jE_I4);
        //甲肝减毒
        SixToOneResult hepA_L1 = new SixToOneResult();//1剂
        hepA_L1.setVaccine(PlanDicContext.HepA_L);
        hepA_L1.setTimes(1);
        list.add(hepA_L1);
        //甲肝灭活
        SixToOneResult hepA_I1 = new SixToOneResult();//1剂
        hepA_I1.setVaccine(PlanDicContext.HepA_I);
        hepA_I1.setTimes(1);
        list.add(hepA_I1);
        SixToOneResult hepA_I2 = new SixToOneResult();//2剂
        hepA_I2.setVaccine(PlanDicContext.HepA_I);
        hepA_I2.setTimes(2);
        list.add(hepA_I2);
        //开始计算应种实种
        Vector<Thread> vectors=new Vector<Thread>();
        for (SixToOneResult result : list){
            Map<String, Object> queryMap = new HashMap<String, Object>();
            queryMap.putAll(map);
            Thread childrenThread=new Thread(new Runnable(){
                public void run(){
                    try {
                        queryMap.put("planClassId",result.getVaccine());
                        queryMap.put("agentTimes",result.getTimes());
                        queryMap.put("selectType","local");
                        queryMap.put("org_id", ShiroUtils.getUserEntity().getOrgId());
                        result.setLocalShould(sixToOneDao.queryShouldIno(queryMap));
                        if ("hepb".equals(result.getCommittee())){//查询卡介的及时
                            result.setLocalReal(sixToOneDao.queryRealInoTimely(queryMap));
                        }else if ("13".equals(result.getVaccine()) || "09".equals(result.getVaccine())){//出来麻腮、麻疹
                            result.setLocalReal(sixToOneDao.queryRealInoFromIno(queryMap));
                        }else {
                            result.setLocalReal(sixToOneDao.queryRealIno(queryMap));
                        }
                        queryMap.put("selectType","move");
                        result.setMoveShould(sixToOneDao.queryShouldIno(queryMap));
                        if ("hepb".equals(result.getCommittee())){//查询卡介的及时
                            result.setMoveReal(sixToOneDao.queryRealInoTimely(queryMap));
                        }else if ("13".equals(result.getVaccine()) || "09".equals(result.getVaccine())){//出来麻腮、麻疹
                            result.setMoveReal(sixToOneDao.queryRealInoFromIno(queryMap));
                        }else {
                            result.setMoveReal(sixToOneDao.queryRealIno(queryMap));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            vectors.add(childrenThread);
            childrenThread.start();
        }
        //主线程
        for(Thread thread : vectors){
            try{
                thread.join();//使用join来保证childrenThread的5个线程都执行完后，才执行主线程
            }catch (Exception e){}
        }
        //List<SixToOneResult> list = sixToOneDao.queryTotalList(queryMap);
        return list;
    }

    @Override
    public R uploadPlatform(Map<String, Object> queryMap) {
        if (queryMap.get("endDate") == null || "".equals(queryMap.get("endDate").toString().trim())){
            return R.error(400,"请先选择查询结束日期");
        }
        if (Constant.GLOBAL_ORG_ID ==null){
            return R.error(400,"登录超时，请退出重新登录在试");
        }
        queryMap.put("nationcode",Constant.GLOBAL_ORG_ID.substring(6));
       // List<Map<String, Object>> vacs = sixToOneDao.queryUploadList(queryMap);
        List<SixToOneResult> list = new ArrayList<>(35);
        List<SixToOneResult> list2 = new ArrayList<>(35);
        //乙肝
        SixToOneResult hepB1 = new SixToOneResult();//1机
        hepB1.setVaccine(PlanDicContext.HepB);
        hepB1.setBioCode("02");
        hepB1.setTimes(1);
        list.add(hepB1);
        list2.add(hepB1);
        SixToOneResult hepB1v = new SixToOneResult();//1及时剂
        hepB1v.setVaccine(PlanDicContext.HepB);
        hepB1v.setBioCode("02");
        hepB1v.setTimes(1);
        hepB1v.setCommittee("hepb");
        list.add(hepB1v);
        list2.add(hepB1v);
        SixToOneResult hepB2 = new SixToOneResult();//2剂
        hepB2.setVaccine(PlanDicContext.HepB);
        hepB2.setBioCode("02");
        hepB2.setTimes(2);
        list.add(hepB2);
        list2.add(hepB2);
        SixToOneResult hepB3 = new SixToOneResult();//3剂
        hepB3.setVaccine(PlanDicContext.HepB);
        hepB3.setBioCode("02");
        hepB3.setTimes(3);
        list.add(hepB3);
        list2.add(hepB3);
        //卡介
        SixToOneResult bcg = new SixToOneResult();//1剂
        bcg.setVaccine(PlanDicContext.BCG);
        bcg.setBioCode("01");
        bcg.setTimes(1);
        list.add(bcg);
        list2.add(bcg);
        //脊灰
        SixToOneResult ipv = new SixToOneResult();//1剂
        ipv.setVaccine(PlanDicContext.IPV);
        ipv.setBioCode("03");
        ipv.setTimes(1);
        list.add(ipv);
        list2.add(ipv);
        SixToOneResult opv1 = new SixToOneResult();//2剂
        opv1.setVaccine(PlanDicContext.OPV);
        opv1.setBioCode("03");
        opv1.setTimes(1);
        list.add(opv1);
        list2.add(opv1);
        SixToOneResult opv2 = new SixToOneResult();//3剂
        opv2.setVaccine(PlanDicContext.OPV);
        opv2.setBioCode("03");
        opv2.setTimes(2);
        list.add(opv2);
        list2.add(opv2);
        SixToOneResult opv3 = new SixToOneResult();//4剂
        opv3.setVaccine(PlanDicContext.OPV);
        opv3.setBioCode("03");
        opv3.setTimes(3);
        list.add(opv3);
        list2.add(opv3);
        //百白破
        SixToOneResult dTaP1 = new SixToOneResult();//1剂
        dTaP1.setVaccine(PlanDicContext.DTaP);
        dTaP1.setBioCode("04");
        dTaP1.setTimes(1);
        list.add(dTaP1);
        list2.add(dTaP1);
        SixToOneResult dTaP2 = new SixToOneResult();//2剂
        dTaP2.setVaccine(PlanDicContext.DTaP);
        dTaP2.setBioCode("04");
        dTaP2.setTimes(2);
        list.add(dTaP2);
        list2.add(dTaP2);
        SixToOneResult dTaP3 = new SixToOneResult();//3剂
        dTaP3.setVaccine(PlanDicContext.DTaP);
        dTaP3.setBioCode("04");
        dTaP3.setTimes(3);
        list.add(dTaP3);
        list2.add(dTaP3);
        SixToOneResult dTaP4 = new SixToOneResult();//4剂
        dTaP4.setVaccine(PlanDicContext.DTaP);
        dTaP4.setBioCode("04");
        dTaP4.setTimes(4);
        list.add(dTaP3);
        list2.add(dTaP3);
        //百破
        SixToOneResult dT1 = new SixToOneResult();//1剂
        dT1.setVaccine(PlanDicContext.DT);
        dT1.setBioCode("06");
        dT1.setTimes(1);
        list.add(dT1);
        list2.add(dT1);
        //麻风
        SixToOneResult mr1 = new SixToOneResult();//1剂
        mr1.setVaccine(PlanDicContext.MR);
        mr1.setBioCode("14");
        mr1.setTimes(1);
        list.add(mr1);
        list2.add(mr1);
        SixToOneResult mr2 = new SixToOneResult();//2剂
        mr2.setVaccine(PlanDicContext.MR);
        mr2.setBioCode("14");
        mr2.setTimes(2);
        list.add(mr2);
        list2.add(mr2);
        //麻腮风
        SixToOneResult mmr1 = new SixToOneResult();//1剂
        mmr1.setVaccine(PlanDicContext.MMR);
        mmr1.setBioCode("12");
        mmr1.setTimes(1);
        list.add(mmr1);
        list2.add(mmr1);
        SixToOneResult mmr2 = new SixToOneResult();//2剂
        mmr2.setVaccine(PlanDicContext.MMR);
        mmr2.setBioCode("12");
        mmr2.setTimes(2);
        list.add(mmr2);
        list2.add(mmr2);
        //麻腮
        SixToOneResult ms1 = new SixToOneResult();//1剂
        ms1.setVaccine("13");
        ms1.setBioCode("13");
        ms1.setTimes(1);
        list.add(ms1);
        list2.add(ms1);
        SixToOneResult ms2 = new SixToOneResult();//2剂
        ms2.setVaccine("13");
        ms2.setBioCode("13");
        ms2.setTimes(2);
        list.add(ms2);
        list2.add(ms2);
        //麻疹
        SixToOneResult mz1 = new SixToOneResult();//1剂
        mz1.setVaccine("09");
        mz1.setBioCode("09");
        mz1.setTimes(1);
        list.add(mz1);
        list2.add(mz1);
        SixToOneResult mz2 = new SixToOneResult();//2剂
        mz2.setVaccine("09");
        mz2.setBioCode("09");
        mz2.setTimes(2);
        list.add(mz2);
        list2.add(mz2);
        //A群
        SixToOneResult mPSV_A1 = new SixToOneResult();//1剂
        mPSV_A1.setVaccine(PlanDicContext.MPSV_A);
        mPSV_A1.setBioCode("16");
        mPSV_A1.setTimes(1);
        list.add(mPSV_A1);
        list2.add(mPSV_A1);
        SixToOneResult mPSV_A2 = new SixToOneResult();//2剂
        mPSV_A2.setVaccine(PlanDicContext.MPSV_A);
        mPSV_A2.setBioCode("16");
        mPSV_A2.setTimes(2);
        list.add(mPSV_A2);
        list2.add(mPSV_A2);
        //A+C群
        SixToOneResult mPSV_AC1 = new SixToOneResult();//1剂
        mPSV_AC1.setVaccine(PlanDicContext.MPSV_AC);
        mPSV_AC1.setBioCode("17");
        mPSV_AC1.setTimes(1);
        list.add(mPSV_AC1);
        list2.add(mPSV_AC1);
        SixToOneResult mPSV_AC2 = new SixToOneResult();//2剂
        mPSV_AC2.setVaccine(PlanDicContext.MPSV_AC);
        mPSV_AC2.setBioCode("17");
        mPSV_AC2.setTimes(2);
        list.add(mPSV_AC2);
        list2.add(mPSV_AC2);
        //乙脑减毒
        SixToOneResult jE_L1 = new SixToOneResult();//1剂
        jE_L1.setVaccine(PlanDicContext.JE_L);
        jE_L1.setBioCode("1801");
        jE_L1.setTimes(1);
        list.add(jE_L1);
        list2.add(jE_L1);
        SixToOneResult jE_L2 = new SixToOneResult();//2剂
        jE_L2.setVaccine(PlanDicContext.JE_L);
        jE_L2.setBioCode("1801");
        jE_L2.setTimes(2);
        list.add(jE_L2);
        list2.add(jE_L2);
        //乙脑灭活
        SixToOneResult jE_I1 = new SixToOneResult();//1剂
        jE_I1.setVaccine(PlanDicContext.JE_I);
        jE_I1.setBioCode("1802");
        jE_I1.setTimes(1);
        list.add(jE_I1);
        list2.add(jE_I1);
        SixToOneResult jE_I2 = new SixToOneResult();//2剂
        jE_I2.setVaccine(PlanDicContext.JE_I);
        jE_I2.setBioCode("1802");
        jE_I2.setTimes(2);
        list.add(jE_I2);
        list2.add(jE_I2);
        SixToOneResult jE_I3 = new SixToOneResult();//3剂
        jE_I3.setVaccine(PlanDicContext.JE_I);
        jE_I3.setBioCode("1802");
        jE_I3.setTimes(3);
        list.add(jE_I3);
        list2.add(jE_I3);
        SixToOneResult jE_I4 = new SixToOneResult();//4剂
        jE_I4.setVaccine(PlanDicContext.JE_I);
        jE_I4.setBioCode("1802");
        jE_I4.setTimes(4);
        list.add(jE_I4);
        list2.add(jE_I4);
        //甲肝减毒
        SixToOneResult hepA_L1 = new SixToOneResult();//1剂
        hepA_L1.setVaccine(PlanDicContext.HepA_L);
        hepA_L1.setBioCode("1901");
        hepA_L1.setTimes(1);
        list.add(hepA_L1);
        list2.add(hepA_L1);
        //甲肝灭活
        SixToOneResult hepA_I1 = new SixToOneResult();//1剂
        hepA_I1.setVaccine(PlanDicContext.HepA_I);
        hepA_I1.setBioCode("1903");
        hepA_I1.setTimes(1);
        list.add(hepA_I1);
        list2.add(hepA_I1);
        SixToOneResult hepA_I2 = new SixToOneResult();//2剂
        hepA_I2.setVaccine(PlanDicContext.HepA_I);
        hepA_I2.setBioCode("1903");
        hepA_I2.setTimes(2);
        list.add(hepA_I2);
        list2.add(hepA_I2);
        //开始计算应种实种
        Vector<Thread> vectors=new Vector<Thread>();
        for (SixToOneResult result : list){
            //Map<String, Object> queryMap = new HashMap<String, Object>();
            //queryMap.putAll(map);
            try {
                queryMap.put("planClassId",result.getVaccine());
                queryMap.put("agentTimes",result.getTimes());
                queryMap.put("selectType","local");
                queryMap.put("org_id", ShiroUtils.getUserEntity().getOrgId());
                result.setLocalShould(sixToOneDao.queryShouldIno(queryMap));
                if ("hepb".equals(result.getCommittee())){//查询卡介的及时
                    result.setLocalReal(sixToOneDao.queryRealInoTimely(queryMap));
                }else if ("13".equals(result.getVaccine()) || "09".equals(result.getVaccine())){//出来麻腮、麻疹
                    result.setLocalReal(sixToOneDao.queryRealInoFromIno(queryMap));
                }else {
                    result.setLocalReal(sixToOneDao.queryRealIno(queryMap));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        for (SixToOneResult result : list2){
            //Map<String, Object> queryMap = new HashMap<String, Object>();
            //queryMap.putAll(map);
            try {
                queryMap.put("planClassId",result.getVaccine());
                queryMap.put("agentTimes",result.getTimes());
                queryMap.put("selectType","move");
                queryMap.put("org_id", ShiroUtils.getUserEntity().getOrgId());
                result.setMoveShould(sixToOneDao.queryShouldIno(queryMap));
                if ("hepb".equals(result.getCommittee())){//查询卡介的及时
                    result.setMoveReal(sixToOneDao.queryRealInoTimely(queryMap));
                }else if ("13".equals(result.getVaccine()) || "09".equals(result.getVaccine())){//出来麻腮、麻疹
                    result.setMoveReal(sixToOneDao.queryRealInoFromIno(queryMap));
                }else {
                    result.setMoveReal(sixToOneDao.queryRealIno(queryMap));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (list == null || list.isEmpty()){
            return R.error(400,"没有查询到信息");
        }
        String nation_code = PropertiesUtils.getProperty("mongodb.properties", "nnation.code");
        if (nation_code == null || "".equals(nation_code.trim())){
            return R.error(400,"报告单位编码没有配置正确");
        }

        String yearMonth = ""+queryMap.get("endDate").toString().substring(0,4)+queryMap.get("endDate").toString().substring(5,7);//
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("routineImmuReport");
        Element immuh = root.addElement("routineImmuh");
        Element yearxml = immuh.addElement("roir_statdate");
        yearxml.setText(yearMonth);
        Element depa = immuh.addElement("roir_depa_id");
        depa.setText(nation_code);
        Element nationcode = immuh.addElement("roir_nationcode");
        nationcode.setText(Constant.GLOBAL_ORG_ID);
        Element infe = immuh.addElement("roir_infe_id");
        infe.setText("1");
        //统计信息
        Element immubs = root.addElement("routineImmubs");

        /*for (Map<String, Object> map : list){*/
        for (SixToOneResult map : list){
            Element immub = immubs.addElement("routineImmub");
            //居住属性
            Element residence = immub.addElement("roir_residence");
            residence.setText("1");
            //疫苗编码
            Element bactcode = immub.addElement("roir_bact_code");
            //bactcode.setText(""+map.get("classCode"));
            bactcode.setText(""+map.getBioCode());
            //接种剂次
            Element inoctimes = immub.addElement("roir_inoctimes");
            inoctimes.setText(""+map.getTimes());
            //应种数
            Element shouldinoc = immub.addElement("roir_shouldinoc");
            shouldinoc.setText(""+map.getLocalShould());
            //实种数
            Element inoculated = immub.addElement("roir_inoculated");
            inoculated.setText(""+map.getLocalReal());
        }
        for (SixToOneResult map : list2){
            Element immub = immubs.addElement("routineImmub");
            //居住属性
            Element residence = immub.addElement("roir_residence");
            residence.setText("2");
            //疫苗编码
            Element bactcode = immub.addElement("roir_bact_code");
            //bactcode.setText(""+map.get("classCode"));
            bactcode.setText(""+map.getBioCode());
            //接种剂次
            Element inoctimes = immub.addElement("roir_inoctimes");
            inoctimes.setText(""+map.getTimes());
            //应种数
            Element shouldinoc = immub.addElement("roir_shouldinoc");
            shouldinoc.setText(""+map.getMoveShould());
            //实种数
            Element inoculated = immub.addElement("roir_inoculated");
            inoculated.setText(""+map.getMoveReal());
        }

        try{
            String xmlString = XmlUtils.formatXml(doc);
            System.out.println(xmlString);
            byte[] zippedBytes = XmlUtils.zipBytes(xmlString, "RoutineImmuReport.xml");
            ServiceChild serviceChild = serviceChildServiceLocator.getChildService();
            //String result = "1";
            String result = serviceChild.uploadRoutineImmuReport(Constant.GLOBAL_ORG_ID, Constant.GLOBAL_ORG_ID, zippedBytes);
            if("1".equals(result)){
                return R.error(1,"上传成功");
            }if("2".equals(result)){
                return R.error(400,"您没有权限上报数据");
            }if("3".equals(result)){
                return R.error(400,"单位编码或密码错误");
            }if("4".equals(result)){
                return R.error(400,"数据操作失败，可能数据格式不对");
            }if("5".equals(result)){
                return R.error(400,"上级单位已经上报数据，上传失败");
            }else{
                return R.error(400,"上传失败！");
            }
        }catch (Exception e){

        }

        return R.ok();
    }
}
