package io.yfjz.service.mgr.impl;

import io.yfjz.dao.mgr.*;
import io.yfjz.entity.mgr.*;
import io.yfjz.entity.sys.SysUserEntity;
import io.yfjz.service.mgr.TMgrStockInfoService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.RRException;
import io.yfjz.utils.ShiroUtils;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.ejb.TransactionManagement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service("tMgrStockInfoService")
public class TMgrStockInfoServiceImpl implements TMgrStockInfoService {
	@Autowired
	private TMgrStockInfoDao tMgrStockInfoDao;
	@Autowired
	private TMgrStockInTotalDao tMgrStockInTotalDao;
	@Autowired
	private TMgrStockInItemDao itemDao;
	@Autowired
	private TMgrStockInfoDao  infoDao;
    @Autowired
    private TMgrWastageDao wastageDao;

    @Autowired
    private TMgrStockOutTotalDao outTotalDao;
    @Autowired
    private TMgrStockOutItemDao outItemDao;
    @Autowired
    private TMgrCheckDao checkDao;

	
	@Override
	public TMgrStockInfoEntity queryObject(String id){
		return tMgrStockInfoDao.queryObject(id);
	}
	
	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> map){

		return tMgrStockInfoDao.queryListMap(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tMgrStockInfoDao.queryTotal(map);
	}

	@Override
	public void update(TMgrStockInfoEntity tMgrStockInfo){
		tMgrStockInfoDao.update(tMgrStockInfo);
	}

	@Override
	public int save(Map<String, Object> map) throws ParseException {
		int result= 0;
		try {
			String order = map.get("order").toString();//入库单号
			String saveUser = map.get("saveUser").toString();//入库人
			String remark = map.get("remark").toString();//入库备注
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date saveTime = sdf.parse(map.get("saveTime").toString());//入库时间
			String store = map.get("store").toString();//入库仓库
			String provider = map.get("provider").toString();//供货人
			/*String provider = map.get("provider").toString();//供货单位
			String providerName = map.get("providerName").toString();//供货人*/
			//入库的产品
			List<Map<String,Object>> rows= (List<Map<String, Object>>) map.get("rows");
			//插入总的入库记录
			TMgrStockInTotalEntity total = new TMgrStockInTotalEntity();
			String id = UUID.randomUUID().toString();
			total.setId(id);//Id
			total.setCreateTime(new Date());//创建时间
			total.setFkCreateUserId(ShiroUtils.getUserEntity().getUserId());//创建人
			total.setInType(Constant.IN_TYPE_NORMAL);//入库类型
			total.setOrgId(Constant.GLOBAL_ORG_ID);//机构编码
			total.setRemark(remark);//入库备注
			total.setStockInCode(order);//入库单号
			total.setFkInStockUser(saveUser);//入库人
            TMgrStoreEntity storeEntity = new TMgrStoreEntity();
            storeEntity.setId(store);
            total.setStore(storeEntity);//仓库ID
			total.setStorageTime(saveTime);//入库时间
			tMgrStockInTotalDao.save(total);
			result = 0;
			//插入库存明细
			if (rows.size()>0){
                for (Map<String, Object> row : rows) {
                    TMgrStockInItemEntity item = new TMgrStockInItemEntity();
                    String baseInfoId = row.get("id").toString();
                    Long amount =null;
                    item.setFkStockInfoId(baseInfoId);//库存产品基本信息表ID
                    Long personAmount=null;
                    if (!StringUtils.isEmpty(row.get("conversion").toString())){
                        Long conversion= Long.valueOf(row.get("conversion").toString());
                        amount=Long.valueOf(row.get("personAmount").toString())/conversion;
                        personAmount=amount*conversion;//人份总数=入库数量*转化人份数
                        item.setPersonAmount(personAmount);
                    }
                    item.setAmount(amount);//入库数量
                    if(row.get("provider")!=null){
                        item.setProvider(row.get("provider")==null||"".equals(row.get("provider"))?provider:row.get("provider").toString());//供货人
                    }
                    if(row.get("providerFactory")!=null){
                        item.setProviderFactory(row.get("providerFactory").toString());//供货单位
                    }
                    String equipmentId=null;
                    if(row.get("equipmentName")!=null){
                        equipmentId=row.get("equipmentName").toString();
                        item.setFkEquipmentId(equipmentId);
                    }
                    item.setCreateTime(new Date());
                    item.setTotalId(total.getId());//汇总记录ID
                    //插入库存明细
                    itemDao.save(item);
                    //插入或更新库存信息
                    //根据产品ID,仓库ID,设备ID查询库存中是否存在该产品
                    TMgrStockInfoEntity baseInfo = infoDao.queryByBaseInfoId(baseInfoId,store,equipmentId);
                    if (baseInfo==null){
                        baseInfo=new TMgrStockInfoEntity();
                        //新增库存信息
                        baseInfo.setAmount(amount);//入库数量
                        baseInfo.setFkBaseInfo(baseInfoId);//库存产品基本信息表ID
                        baseInfo.setFkCreateUserId(ShiroUtils.getUserEntity().getUserId());//创建人ID
                        baseInfo.setFkStoreId(store);//入库仓库ID
                        baseInfo.setCreateTime(new Date());//创建时间
                        baseInfo.setFkEquipmentId(equipmentId);//设备ID
                        if (personAmount!=null){
                            baseInfo.setPersonAmount(personAmount);//人份总数
                        }
                        infoDao.save(baseInfo);
                    }else{
                        //更新库存信息,只需要更新库存数量，人份总数量
                        Long baseAmount = baseInfo.getAmount();//原数量
                        Long totalAmount=baseAmount+amount;
                        baseInfo.setAmount(totalAmount);//总数量
                        if (personAmount!=null){
                            baseInfo.setPersonAmount(baseInfo.getPersonAmount()+personAmount);//人份总数
                        }
                        result=infoDao.update(baseInfo);
                    }
                }
            }
            result=1;//没有发生异常返回1，默认返回0
		} catch (Exception e) {
			e.printStackTrace();
		}


		return result;
	}

	@Override
	public int damage(Map param) {

        try {
            updateStock(param,Constant.OUT_TYPE_DAMAGE);
            //报损表中插入记录
            TMgrWastageEntity wastageEntity = new TMgrWastageEntity();
            String infoId = param.get("infoId").toString();
            //String type = param.get("type").toString();
            String storeId = param.get("storeId").toString();
            Object conversion = param.get("conversion");
            Long number = Long.valueOf(param.get("number").toString());
            String reason=param.get("reason").toString();
            SysUserEntity user = ShiroUtils.getUserEntity();
            wastageEntity.setAmount(number);//报损数量
            wastageEntity.setApplyUser(user.getRealName());//报损填报人
            wastageEntity.setCreateTime(new Date());//报损时间
            wastageEntity.setOrgId(Constant.GLOBAL_ORG_ID);//机构代码
            //只有疫苗存在
            if (conversion!=null&&!"".equals(conversion)){
                wastageEntity.setPersonAmount( Long.valueOf(conversion.toString())*number);//报损人份
            }
            wastageEntity.setStockInfoId(infoId);//库存信息ID
            wastageEntity.setType(Constant.WASTAGE_SCRAP);//损耗类型 ：报废
            wastageEntity.setRemark(reason);//报废原因
            wastageDao.save(wastageEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
	}

    @Override
    public List<Map<String, Object>> queryRegisterList(Map<String, Object> map) {

        return tMgrStockInfoDao.queryRegisterList(map);
    }

    @Override
    public int queryRegisterTotal(Map<String, Object> map) {
        return tMgrStockInfoDao.queryRegisterTotal(map);
    }

    @Override
    public int returnGoods(Map param) {
        try {
            updateStock(param,Constant.OUT_TYPE_RETURN);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return 1;
    }

    @Override
    public String getOrderNumber(String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        if (!StringUtils.isEmpty(type)){
	       if (type.equals("stockIn")){
               String orders=   tMgrStockInTotalDao.queryOrderId();
               if (orders==null){
                   return date+"RK001";
               }else{
                   String number = orders.substring(orders.length() - 3);
                   Integer ret=Integer.valueOf(number)+1;
                   String result="";
                   int length = ret.toString().length();
                   for (int i = length; i < 3; i++) {
                       result+="0";
                   }
                   return  date+"RK"+result+ret;
               }
           }else if (type.equals("check")){
               String orders=    checkDao.queryOrderId();
               if (orders==null){
                   return date+"PD001";
               }else{
                   String number = orders.substring(orders.length() - 3);
                   Integer ret=Integer.valueOf(number)+1;
                   String result="";
                   int length = ret.toString().length();
                   for (int i = length; i < 3; i++) {
                       result+="0";
                   }
                   return  date+"PD"+result+ret;
               }
           }
       }
        return null;
    }

    @Override
    public int carryOverStock() {
	    return tMgrStockInfoDao.insertStockInit();
    }

    private void updateStock(Map param,Integer outType){
        String infoId = param.get("infoId").toString();
        //String type = param.get("type").toString();
        String storeId = param.get("storeId").toString();
        Long number = Long.valueOf(param.get("number").toString());
        String reason=param.get("reason").toString();
        SysUserEntity user = ShiroUtils.getUserEntity();
        //更新库存表中的数量

        TMgrStockInfoEntity infoEntity = infoDao.queryObject(infoId);
        if (number>infoEntity.getAmount()||number<1){
            throw new RRException("报损数量，必须大于0，小于库存数量");
        }
        Long amount=infoEntity.getAmount()-number;
        infoEntity.setAmount(amount);//库存数量
        Long personSub=null;
        if(infoEntity.getConversion()!=null&&infoEntity.getConversion()!=0){
            personSub=number*infoEntity.getConversion();
            infoEntity.setPersonAmount(infoEntity.getPersonAmount()-personSub);//更新人份总数
        }
        //更新库存
        infoDao.update(infoEntity);
        //插入出库记录
        /**
         *插入出库总表记录
         */
        TMgrStockOutTotalEntity out = new TMgrStockOutTotalEntity();
        //出库时间
        out.setCreateTime(new Date());
        //创建人
        out.setCreateUserId(user.getUserId());
        //出库人
        out.setOutStockUserId(user.getUserId());
        //出库类型
        out.setOutType(outType);
        //机构编码
        out.setOrgId(Constant.GLOBAL_ORG_ID);
        //出库单号 默认时间戳
        out.setStockOutCode(System.currentTimeMillis()+"");
        //出库备注
        out.setRemark(reason);
        //出库ID
        String totalId = UUID.randomUUID().toString();
        out.setId(totalId);
        out.setStoreId(storeId);
        outTotalDao.save(out);
        /**
         * 插入出库记录明细
         */
        TMgrStockOutItemEntity outItem = new TMgrStockOutItemEntity();
        //出库数量
        outItem.setAmount(number);
        //创建时间
        outItem.setCreateTime(new Date());
        //库存信息ID
        outItem.setStockInfoId(infoId);
        //创建人
        outItem.setCreateUserId(user.getUserId());
        //出库合计编号
        outItem.setTotalId(totalId);
        //出库设备
        if( param.get("equipId")!=null&&!"".equals(param.get("equipId"))){
            outItem.setFkEquipmentId(param.get("equipId").toString());
        }

        //出库人份总数
        if (param.get("conversion")!=null&&!"".equals(param.get("conversion"))){
            Long conversion = Long.valueOf(param.get("conversion").toString());
            Long towerPersonAmount=number*conversion;
            outItem.setPersonAmount(towerPersonAmount);
        }
        //插入出库明细
        outItemDao.save(outItem);

    }

    @Override
    public TMgrStockInfoEntity queryStockInfoByEquipmentAndStore(Map map) {
        return infoDao.queryStockInfoByEquipmentAndStore(map);
    }

    @Override
    public List<Map<String, Object>> queryDispatchList(Map<String, Object> map) {
        List<Map<String, Object>> list = infoDao.queryDispatchList(map);
        //结余
        int remainAmount=0;
        int remainPersonAmount=0;
        if (list.size()>0){
            for (Map<String, Object> temp : list) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                temp.put("createTime",sdf.format(temp.get("createTime")));
                if(!StringUtils.isEmpty(temp.get("inAmount"))){
                    Integer inAmount = Integer.valueOf(temp.get("inAmount").toString());
                    Integer inPersonAmount = Integer.valueOf(temp.get("inPersonAmount").toString());
                    remainAmount+=inAmount;
                    remainPersonAmount+=inPersonAmount;
                    temp.put("remainBatchnum",temp.get("inBatchnum"));
                    temp.put("remainExpiryDate",temp.get("inExpiryDate"));
                    temp.put("remainAmount",remainAmount);
                    temp.put("remainPersonAmount",remainPersonAmount);
                }
                if(!StringUtils.isEmpty(temp.get("outAmount"))){
                    Integer outAmount = Integer.valueOf(temp.get("outAmount").toString());
                    Integer outPersonAmount = Integer.valueOf(temp.get("outPersonAmount").toString());
                    remainAmount-=outAmount;
                    remainPersonAmount-=outPersonAmount;
                    temp.put("remainAmount",remainAmount);
                    temp.put("remainPersonAmount",remainPersonAmount);
                    temp.put("remainBatchnum",temp.get("outBatchnum"));
                    temp.put("remainExpiryDate",temp.get("outExpiryDate"));
                }

            }
        }
        return list;
    }
}
