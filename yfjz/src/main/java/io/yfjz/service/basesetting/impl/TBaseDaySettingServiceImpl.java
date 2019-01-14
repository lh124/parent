package io.yfjz.service.basesetting.impl;

import io.yfjz.dao.basesetting.TBaseDayHolidayDao;
import io.yfjz.dao.basesetting.TBaseDaySettingDao;
import io.yfjz.entity.basesetting.TBaseDayHolidayEntity;
import io.yfjz.entity.basesetting.TBaseDaySettingEntity;
import io.yfjz.service.basesetting.TBaseDaySettingService;
import io.yfjz.utils.Constant;
import io.yfjz.utils.DateUtils;
import io.yfjz.utils.R;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 机构接种日设置表
 *
 * @author 刘琪
 * @email 1018628825@qq.com
 * @tel 15685423726
 * @date 2018-07-26 17:45:18
 */
@Service("tBaseDaySettingService")
public class TBaseDaySettingServiceImpl implements TBaseDaySettingService {

	@Autowired
	private TBaseDaySettingDao tBaseDaySettingDao;

	@Autowired
	TBaseDayHolidayDao tBaseDayHolidayDao;
	
	@Override
	public TBaseDaySettingEntity queryObject(String id){
		return tBaseDaySettingDao.queryObject(id);
	}
	
	@Override
	public List<TBaseDaySettingEntity> queryList(Map<String, Object> map){
		return tBaseDaySettingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tBaseDaySettingDao.queryTotal(map);
	}
	
	@Override
	public void save(TBaseDaySettingEntity tBaseDaySetting){
		tBaseDaySettingDao.save(tBaseDaySetting);
	}
	
	@Override
	public void update(TBaseDaySettingEntity tBaseDaySetting){
		tBaseDaySettingDao.update(tBaseDaySetting);
	}
	
	@Override
	public void delete(String id){
		tBaseDaySettingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		tBaseDaySettingDao.deleteBatch(ids);
	}


	@Transactional
	@Override
	public R saveInoculationDaySetting(Map map) {
		ArrayList paramList = (ArrayList) map.get("ids");
		String settingType = (String) map.get("settingType");//选中的数据
		if (StringUtils.isEmpty(settingType)){
			return R.error(-1,"未选中设置的类型，请选择类型再提交");
		}

		if (paramList == null  || paramList.size() <=0){
			return R.error(-1,"您尚未选择需要提交的日期，请您选择后再提交");
		}
		tBaseDaySettingDao.removeByUserType(settingType);//优先删除，再保存
		for (int i = 0; i < paramList.size(); i++) {
			TBaseDaySettingEntity tBaseDaySetting = new TBaseDaySettingEntity();
			tBaseDaySetting.setCreateTime(new Date());
			tBaseDaySetting.setOrgId("");
			tBaseDaySetting.setSettingType(settingType);
			tBaseDaySetting.setStatus(-1);
			tBaseDaySetting.setDays(String.valueOf(paramList.get(i)));
			tBaseDaySettingDao.save(tBaseDaySetting);
		}
		return R.ok(0,"保存成功");
	}

	@Override
	public R getCurrentStartDays() {
		String orgId = Constant.GLOBAL_ORG_ID;
		Map map = tBaseDaySettingDao.getCurrentStartDays(orgId);
		if (map == null)
			return R.error(-1,"门诊没有启用接种日设置，请启用其中一种门诊接种设置");
		return R.ok(map);
	}


	@Override
	public R updateBySettingType(String settingType) {
		try {
			tBaseDaySettingDao.updateByNotSettingType(settingType);
			tBaseDaySettingDao.updateBySettingType(settingType);
			return R.ok(0,"启用成功");
		} catch (Exception e) {
			e.printStackTrace();
			return R.error(-1,"启用失败");
		}
	}

	/*@Override
	public R getAnInoculationTime(String orgId, String bioCode, String committeeCode, Date inocDate) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//1.获取当前门诊设置的的门诊类型及设置的日期
        HashMap res = tBaseDaySettingDao.getCurrentStartDaysByCondition(new HashMap<String,Object>(){{
        	put("orgId",orgId);
        	put("bioCode",bioCode);
        	put("committeeCode",committeeCode);
		}});
        if (res == null) {
        	return R.ok().put("inocDate",inocDate);
        }
        String[] days = String.valueOf(res.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ","").split(",");
        int year = DateUtils.getNowYear(inocDate);
        int month = DateUtils.getNowMonth(inocDate);
        //首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
		TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
		List holidayList = null;
		if (tBaseDayHolidayEntity != null){
			holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ","").split(","));
		}
		try {
			//循环查询当前日期是否大于给定的日期，而且还要排除节假日
			for (String day : days) {
				Date calculateDate=sdf.parse(year + "-" + month + "-" + Integer.parseInt(day));
				int i = DateUtils.dateCompare(inocDate,calculateDate);
				if (i<0){//只寻找大于给定推荐日期的日期
					if (tBaseDayHolidayEntity == null){
						return R.ok().put("inocDate",calculateDate);
					}else{
						//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
						if(holidayList.contains(day)){
							//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
							continue;
						}else {
							return R.ok().put("inocDate",calculateDate);
						}
					}
				}else {
					continue;
				}
			}
			//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
			for (String day : days) {
				Date nextcalculateDate=sdf.parse(year + "-" + (month+1) + "-" + Integer.parseInt(day));
				if (tBaseDayHolidayEntity == null){
					return R.ok().put("inocDate",nextcalculateDate);
				}else{
					//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
					if(holidayList.contains(day)){
						//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
						continue;
					}else {
						return R.ok().put("inocDate",nextcalculateDate);
					}
				}
			}
			return R.ok().put("inocDate",inocDate);
		} catch (ParseException e) {
			return R.error("时间解析异常{}"+e.getMessage());
		}
    }*/

	@Override
	public R getAnInoculationTime(String orgId, String bioCode, String committeeCode, Date inocDate) {
		Date currentDate = new Date();
		int  time = currentDate.getDay() - inocDate.getDay();
		//System.out.println(weeks+weekYear);
		//1.获取当前门诊设置的的门诊类型及设置的日期
		Map<String, Object> param = new HashMap<>();
		param.put("orgId", orgId);
		param.put("bioCode", bioCode);
		param.put("committeeCode", committeeCode);
		List<Map<String, Object>> res = tBaseDaySettingDao.getCurrentSettingDaysByCondition(param);
		if (res == null || res.size()<=0) {
			return R.ok().put("inocDate", inocDate);
		}
		String settingType = res.get(0).get("setting_type").toString();
		if("1".equals(settingType) || "3".equals(settingType)|| "5".equals(settingType)){
			return  getWeekSettingInoculationTime(bioCode,committeeCode,inocDate,res);
		}
		if("2".equals(settingType) || "4".equals(settingType) || "6".equals(settingType)){
			return getMonthSettingInoculationTime( bioCode, committeeCode, inocDate,res);
		}
		return R.ok().put("inocDate", inocDate);
	}

	public R getWeekSettingInoculationTime(String bioCode,String committeeCode,Date inocDate,List<Map<String,Object>> res) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int year = DateUtils.getNowYear(inocDate);
		int month = DateUtils.getNowMonth(inocDate);
		Date currentDate = new Date();
		int  time = currentDate.getDay() - inocDate.getDay();
		String settingType = res.get(0).get("setting_type").toString();
		cal.setTime(inocDate);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		if ("1".equals(settingType)) {
				for (int j = 0; j < res.size(); j++) {
					Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
					//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
					TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
					List holidayList = null;
					if (tBaseDayHolidayEntity != null) {
						holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
					}
				//String[] committeeCodes = String.valueOf(map.get("committee_id")).split(",");
					try {
						for (String day : days) {
							int weekday = Integer.parseInt(day);
							/*if(time >= 0) {
								cal.setTime(currentDate);
							}*/
							while (!Arrays.asList(days).contains(String.valueOf(w))){
								cal.add(Calendar.DATE, 1);
								w = cal.get(Calendar.DAY_OF_WEEK) - 1;
								if (w == 0) {
									w = 7;
								}
								if(holidayList!=null && holidayList.contains(String.valueOf(cal.getTime().getDay()))){
									continue;
								}
							}
							inocDate = cal.getTime();
							return R.ok().put("inocDate", inocDate);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

			}
		}
		if("3".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				String[] committeeCodes = String.valueOf(map.get("committee_id")).split(",");
				if(Arrays.asList(committeeCodes).contains(String.valueOf(committeeCode))){
					try {
						for (String day : days) {
							int weekday = Integer.parseInt(day);
							if(time >= 0) {
								cal.setTime(currentDate);
							}
							while (!Arrays.asList(days).contains(String.valueOf(w))){
								cal.add(Calendar.DATE, 1);
								w = cal.get(Calendar.DAY_OF_WEEK) - 1;
								if (w == 0) {
									w = 7;
								}
								if(holidayList!=null && holidayList.contains(String.valueOf(cal.getTime().getDay()))){
									continue;
								}
							}
							inocDate = cal.getTime();
							return R.ok().put("inocDate", inocDate);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if("5".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				String[] bioCodes = String.valueOf(map.get("vacc_code")).split(",");
				if(Arrays.asList(bioCodes).contains(String.valueOf(bioCode))){
					try {
						for (String day : days) {
							int weekday = Integer.parseInt(day);
							if(time >= 0) {
								cal.setTime(currentDate);
							}
							while (!Arrays.asList(days).contains(String.valueOf(w))){
								cal.add(Calendar.DATE, 1);
								w = cal.get(Calendar.DAY_OF_WEEK) - 1;
								if (w == 0) {
									w = 7;
								}
								if(holidayList!=null && holidayList.contains(String.valueOf(cal.getTime().getDay()))){
									continue;
								}
							}
							 inocDate = cal.getTime();
							 return R.ok().put("inocDate", inocDate);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}
		return R.ok().put("inocDate", inocDate);
	}

	public R getMonthSettingInoculationTime(String bioCode,String committeeCode,Date inocDate,List<Map<String,Object>> res) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int year = DateUtils.getNowYear(inocDate);
		int month = DateUtils.getNowMonth(inocDate);
		Date currentDate = new Date();
		int  time = currentDate.getDay() - inocDate.getDay();
		String settingType = res.get(0).get("setting_type").toString();
		if("2".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				try {
					//循环查询当前日期是否大于给定的日期，而且还要排除节假日
					for (String day : days) {
						Date calculateDate = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day));
						int i = DateUtils.dateCompare(inocDate, calculateDate);
						if (i < 0) {//只寻找大于给定推荐日期的日期
							if (tBaseDayHolidayEntity == null) {
								return R.ok().put("inocDate", calculateDate);
							} else {
								//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
								if (holidayList.contains(day)) {
									//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
									continue;
								} else {
									return R.ok().put("inocDate", calculateDate);
								}
							}
						} else {
							continue;
						}
					}
					//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
					for (String day : days) {
						Date nextcalculateDate = sdf.parse(year + "-" + (month + 1) + "-" + Integer.parseInt(day));
						if (tBaseDayHolidayEntity == null) {
							return R.ok().put("inocDate", nextcalculateDate);
						} else {
							//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
							if (holidayList.contains(day)) {
								//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
								continue;
							} else {
								return R.ok().put("inocDate", nextcalculateDate);
							}
						}
					}
					return R.ok().put("inocDate", inocDate);
				} catch (ParseException e) {
					return R.error("时间解析异常{}" + e.getMessage());
				}
			}
		}
		if("4".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				String[] committeeCodes = String.valueOf(map.get("committee_id")).split(",");
				if(Arrays.asList(committeeCodes).contains(String.valueOf(committeeCode))){
					//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
					TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
					List holidayList = null;
					if (tBaseDayHolidayEntity != null) {
						holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
					}
					try {
						//循环查询当前日期是否大于给定的日期，而且还要排除节假日
						for (String day : days) {
							Date calculateDate = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day));
							int i = DateUtils.dateCompare(inocDate, calculateDate);
							if (i < 0) {//只寻找大于给定推荐日期的日期
								if (tBaseDayHolidayEntity == null) {
									return R.ok().put("inocDate", calculateDate);
								} else {
									//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
									if (holidayList.contains(day)) {
										//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
										continue;
									} else {
										return R.ok().put("inocDate", calculateDate);
									}
								}
							} else {
								continue;
							}
						}
						//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
						for (String day : days) {
							Date nextcalculateDate = sdf.parse(year + "-" + (month + 1) + "-" + Integer.parseInt(day));
							if (tBaseDayHolidayEntity == null) {
								return R.ok().put("inocDate", nextcalculateDate);
							} else {
								//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
								if (holidayList.contains(day)) {
									//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
									continue;
								} else {
									return R.ok().put("inocDate", nextcalculateDate);
								}
							}
						}
						return R.ok().put("inocDate", inocDate);
					} catch (ParseException e) {
						return R.error("时间解析异常{}" + e.getMessage());
					}
				}

			}
		}
		if("6".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				String[] bioCodes = String.valueOf(map.get("vacc_code")).split(",");
				if(Arrays.asList(bioCodes).contains(String.valueOf(bioCode))){
					//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
					TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
					List holidayList = null;
					if (tBaseDayHolidayEntity != null) {
						holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
					}
					try {
						//循环查询当前日期是否大于给定的日期，而且还要排除节假日
						for (String day : days) {
							Date calculateDate = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day));
							int i = DateUtils.dateCompare(inocDate, calculateDate);
							if (i < 0) {//只寻找大于给定推荐日期的日期
								if (tBaseDayHolidayEntity == null) {
									return R.ok().put("inocDate", calculateDate);
								} else {
									//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
									if (holidayList.contains(day)) {
										//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
										continue;
									} else {
										return R.ok().put("inocDate", calculateDate);
									}
								}
							} else {
								continue;
							}
						}
						//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
						for (String day : days) {
							Date nextcalculateDate = sdf.parse(year + "-" + (month + 1) + "-" + Integer.parseInt(day));
							if (tBaseDayHolidayEntity == null) {
								return R.ok().put("inocDate", nextcalculateDate);
							} else {
								//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
								if (holidayList.contains(day)) {
									//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
									continue;
								} else {
									return R.ok().put("inocDate", nextcalculateDate);
								}
							}
						}
						return R.ok().put("inocDate", inocDate);
					} catch (ParseException e) {
						return R.error("时间解析异常{}" + e.getMessage());
					}
				}

			}
		}

		return R.ok().put("inocDate", inocDate);
	}

	@Override
	public R queryListDaySettings(String orgId, String committeeCode, Date startInocDate, Date endInocDate, String bioCode) {
		/*Date currentDate = new Date();
		int  timeSatrt = startInocDate.getDay();
		int  timeEnd = endInocDate.getDay();*/
		//System.out.println(weeks+weekYear);
		//1.获取当前门诊设置的的门诊类型及设置的日期
		Map<String, Object> param = new HashMap<>();
		param.put("orgId", orgId);
		param.put("bioCode", bioCode);
		param.put("committeeCode", committeeCode);
		List<Map<String, Object>> res = tBaseDaySettingDao.getCurrentSettingDaysByCondition(param);
		if (res == null || res.size()<=0) {
			return R.ok().put("inocDate", startInocDate);
		}
		String settingType = res.get(0).get("setting_type").toString();
		if("1".equals(settingType) || "3".equals(settingType)|| "5".equals(settingType)){
			return getWeekSettingDaysInoculationTime(bioCode,committeeCode,startInocDate,endInocDate,res);
		}
		if("2".equals(settingType) || "4".equals(settingType) || "6".equals(settingType)){
			return getMonthSettingDaysInoculationTime( bioCode, committeeCode, startInocDate,endInocDate,res);
		}
		return R.ok().put("inocDate", startInocDate);
		//return null;
	}


	public R getWeekSettingDaysInoculationTime(String bioCode,String committeeCode,Date startInocDate,Date endInocDate,List<Map<String,Object>> res) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		List listInoculationTime = new ArrayList<>();
		int yearStart = DateUtils.getNowYear(startInocDate);
		int monthStart = DateUtils.getNowMonth(startInocDate);
		//int yearEnd = DateUtils.getNowYear(endInocDate);
		//int monthEnd = DateUtils.getNowMonth(endInocDate);
		String settingType = res.get(0).get("setting_type").toString();
		cal.setTime(startInocDate);
		cal2.setTime(endInocDate);
		int dayCount = (int) ((endInocDate.getTime() - startInocDate.getTime()) / (1000*3600*24));
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int w2 = cal2.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		if (w2 < 0) {
			w2 = 0;
		}
		if ("1".equals(settingType)) {
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(monthStart);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				//String[] committeeCodes = String.valueOf(map.get("committee_id")).split(",");
				try {
					while(cal.getTime().getTime() < cal2.getTime().getTime()) {
						for (String day : days) {
							int weekday = Integer.parseInt(day);
							while (!Arrays.asList(days).contains(String.valueOf(w)) && cal.getTime().getTime() < cal2.getTime().getTime()) {
								cal.add(Calendar.DATE, 1);
								w = cal.get(Calendar.DAY_OF_WEEK) - 1;
								if (w == 0) {
									w = 7;
								}
								if (holidayList != null && holidayList.contains(String.valueOf(cal.getTime().getDay()))) {
									continue;
								}
							}
							if(cal.getTime().getTime() < cal2.getTime().getTime()) {
								listInoculationTime.add(cal.getTime());
								w = 0;
								//inocDate = cal.getTime();
							}
						}
					}
					return R.ok().put("inocDate", listInoculationTime);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		if("3".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(monthStart);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				String[] committeeCodes = String.valueOf(map.get("committee_id")).split(",");
				if(Arrays.asList(committeeCodes).contains(String.valueOf(committeeCode))){
					try {
							while(cal.getTime().getTime() < cal2.getTime().getTime()) {
								for (String day : days) {
									int weekday = Integer.parseInt(day);
									while ((!Arrays.asList(days).contains(String.valueOf(w))) && cal.getTime().getTime() < cal2.getTime().getTime()) {
										cal.add(Calendar.DATE, 1);
										w = cal.get(Calendar.DAY_OF_WEEK) - 1;
										if (w == 0) {
											w = 7;
										}
										if (holidayList != null && holidayList.contains(String.valueOf(cal.getTime().getDay()))) {
											continue;
										}
									}
									if(cal.getTime().getTime() < cal2.getTime().getTime()) {
										listInoculationTime.add(cal.getTime());
										w = 0;
									}

								}
							}
						return R.ok().put("inocDate", listInoculationTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if("5".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(monthStart);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				String[] bioCodes = String.valueOf(map.get("vacc_code")).split(",");
				if(Arrays.asList(bioCodes).contains(String.valueOf(bioCode))){
					try {
						while(cal.getTime().getTime() < cal2.getTime().getTime()) {
							for (String day : days) {
								int weekday = Integer.parseInt(day);
							/*if(time >= 0) {
								cal.setTime(currentDate);
							}*/
								while (!Arrays.asList(days).contains(String.valueOf(w))&& cal.getTime().getTime() < cal2.getTime().getTime()) {
									cal.add(Calendar.DATE, 1);
									w = cal.get(Calendar.DAY_OF_WEEK) - 1;
									if (w == 0) {
										w = 7;
									}
									if (holidayList != null && holidayList.contains(String.valueOf(cal.getTime().getDay()))) {
										continue;
									}
								}
								if(cal.getTime().getTime() < cal2.getTime().getTime()) {
									listInoculationTime.add(cal.getTime());
									w = 0;
								}
							}
						}
						return R.ok().put("inocDate", listInoculationTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}
		return R.ok().put("inocDate", listInoculationTime);
	}

	public R getMonthSettingDaysInoculationTime(String bioCode,String committeeCode,Date startInocDate,Date endInocDate,List<Map<String,Object>> res) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		List listInoculationTime = new ArrayList<>();
		int yearStart = DateUtils.getNowYear(startInocDate);
		int monthStart = DateUtils.getNowMonth(startInocDate);
		int yearEnd = DateUtils.getNowYear(endInocDate);
		int monthEnd = DateUtils.getNowMonth(endInocDate);
		Date currentDate = new Date();
		cal.setTime(startInocDate);
		cal2.setTime(endInocDate);
		int dayCount = (int) ((endInocDate.getTime() - startInocDate.getTime()) / (1000*3600*24));
		//int dayCount = cal2.get(Calendar.DAY_OF_YEAR)-cal.get(Calendar.DAY_OF_YEAR);
		//int  dayCount = endInocDate.getDay() - startInocDate.getDay();
		String settingType = res.get(0).get("setting_type").toString();
		if("2".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
				TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(monthStart);
				List holidayList = null;
				if (tBaseDayHolidayEntity != null) {
					holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
				}
				try {
					//循环查询当前日期是否大于给定的日期，而且还要排除节假日
					while(cal.getTime().getTime() < cal2.getTime().getTime()) {
						for (String day : days) {
							int da = Integer.parseInt(day);
							Date calculateDate = sdf.parse(yearStart + "-" + monthStart + "-" + da);
							int i = DateUtils.dateCompare(startInocDate, calculateDate);
							int i2 = DateUtils.dateCompare(endInocDate, calculateDate);
							if (i < 0 && i2 > 0) {//只寻找大于给定推荐日期的日期
								if (tBaseDayHolidayEntity == null) {
									listInoculationTime.add(calculateDate);
									cal.add(Calendar.DATE, 1);
									//monthStart++;
									//return R.ok().put("inocDate", calculateDate);
								} else {
									//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
									if (holidayList.contains(day)) {
										//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
										continue;
									} else {
										listInoculationTime.add(calculateDate);
									}
								}
							} else {
								cal.add(Calendar.DATE, dayCount/days.length);
								continue;
							}
						}
						if(monthStart==12){
							monthStart=1;
							yearStart++;
							continue;
						}
						monthStart++;

					}
					//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
					/*for (String day : days) {
						Date nextcalculateDate = sdf.parse(yearStart + "-" + (monthStart + 1) + "-" + Integer.parseInt(day));
						if (tBaseDayHolidayEntity == null) {
							return R.ok().put("inocDate", nextcalculateDate);
						} else {
							//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
							if (holidayList.contains(day)) {
								//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
								continue;
							} else {
								listInoculationTime.add(nextcalculateDate);
							}
						}
					}*/
					return R.ok().put("inocDate", listInoculationTime);
				} catch (ParseException e) {
					return R.error("时间解析异常{}" + e.getMessage());
				}
			}
		}
		/*if("4".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				String[] committeeCodes = String.valueOf(map.get("committee_id")).split(",");
				if(Arrays.asList(committeeCodes).contains(String.valueOf(committeeCode))){
					//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
					TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
					List holidayList = null;
					if (tBaseDayHolidayEntity != null) {
						holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
					}
					try {
						//循环查询当前日期是否大于给定的日期，而且还要排除节假日
						for (String day : days) {
							Date calculateDate = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day));
							int i = DateUtils.dateCompare(inocDate, calculateDate);
							if (i < 0) {//只寻找大于给定推荐日期的日期
								if (tBaseDayHolidayEntity == null) {
									return R.ok().put("inocDate", calculateDate);
								} else {
									//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
									if (holidayList.contains(day)) {
										//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
										continue;
									} else {
										return R.ok().put("inocDate", calculateDate);
									}
								}
							} else {
								continue;
							}
						}
						//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
						for (String day : days) {
							Date nextcalculateDate = sdf.parse(year + "-" + (month + 1) + "-" + Integer.parseInt(day));
							if (tBaseDayHolidayEntity == null) {
								return R.ok().put("inocDate", nextcalculateDate);
							} else {
								//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
								if (holidayList.contains(day)) {
									//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
									continue;
								} else {
									return R.ok().put("inocDate", nextcalculateDate);
								}
							}
						}
						return R.ok().put("inocDate", inocDate);
					} catch (ParseException e) {
						return R.error("时间解析异常{}" + e.getMessage());
					}
				}

			}
		}
		if("6".equals(settingType)){
			for (int j = 0; j < res.size(); j++) {
				Map map = res.get(j);
				String[] days = String.valueOf(map.get("days")).replaceAll("\\[", "").replaceAll("]", "").replaceAll(" ", "").split(",");
				String[] bioCodes = String.valueOf(map.get("vacc_code")).split(",");
				if(Arrays.asList(bioCodes).contains(String.valueOf(bioCode))){
					//首先查询出国家节假日列表，计算给定推荐日期所在月份的国家节假日
					TBaseDayHolidayEntity tBaseDayHolidayEntity = tBaseDayHolidayDao.queryNationalHolidays(month);
					List holidayList = null;
					if (tBaseDayHolidayEntity != null) {
						holidayList = Arrays.asList(tBaseDayHolidayEntity.getDay().replaceAll(" ", "").split(","));
					}
					try {
						//循环查询当前日期是否大于给定的日期，而且还要排除节假日
						for (String day : days) {
							Date calculateDate = sdf.parse(year + "-" + month + "-" + Integer.parseInt(day));
							int i = DateUtils.dateCompare(inocDate, calculateDate);
							if (i < 0) {//只寻找大于给定推荐日期的日期
								if (tBaseDayHolidayEntity == null) {
									return R.ok().put("inocDate", calculateDate);
								} else {
									//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
									if (holidayList.contains(day)) {
										//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
										continue;
									} else {
										return R.ok().put("inocDate", calculateDate);
									}
								}
							} else {
								continue;
							}
						}
						//如果循环完毕之后还是没有一个日期大于给定的推荐日期，那就获取下一个月的第一个日期
						for (String day : days) {
							Date nextcalculateDate = sdf.parse(year + "-" + (month + 1) + "-" + Integer.parseInt(day));
							if (tBaseDayHolidayEntity == null) {
								return R.ok().put("inocDate", nextcalculateDate);
							} else {
								//保留当前的日期，然后和给定推荐日期所在月份的国家假节日过滤掉后，返回日期
								if (holidayList.contains(day)) {
									//如果国家节假日包含了门诊日，需要重新寻找下一个门诊日
									continue;
								} else {
									return R.ok().put("inocDate", nextcalculateDate);
								}
							}
						}
						return R.ok().put("inocDate", inocDate);
					} catch (ParseException e) {
						return R.error("时间解析异常{}" + e.getMessage());
					}
				}

			}
		}*/

		return R.ok().put("inocDate", listInoculationTime);
	}
}
