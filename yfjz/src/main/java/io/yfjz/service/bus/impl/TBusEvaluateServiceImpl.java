package io.yfjz.service.bus.impl;

import io.yfjz.dao.bus.TBusEvaluateDao;
import io.yfjz.entity.bus.TBusEvaluateEntity;
import io.yfjz.service.bus.TBusEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;




/**
 * 用户评价
 * @describe:
 * @param
 * @return
 * @author 邓召仕
 * @date: 2018-09-05  17:15
 **/
@Service("tBusEvaluateService")
public class TBusEvaluateServiceImpl implements TBusEvaluateService {
	@Autowired
	private TBusEvaluateDao tBusEvaluateDao;

	@Override
	public void save(TBusEvaluateEntity tBusEvaluate){
		tBusEvaluateDao.save(tBusEvaluate);
	}

}
