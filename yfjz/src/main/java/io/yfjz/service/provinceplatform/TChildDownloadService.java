package io.yfjz.service.provinceplatform;

import io.yfjz.entity.provinceplatform.TChildDownloadEntity;

import java.util.List;
import java.util.Map;

/**
 * 省平台下载新生儿表
 * 
 * @author 饶士培
 * @email 1013147559@qq.com
 * @tel 18798010686
 * @date 2018-07-23 14:33:28
 */
public interface TChildDownloadService {
	
	TChildDownloadEntity queryObject(String id);
	
	List<TChildDownloadEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TChildDownloadEntity tChildDownload);
	
	void update(TChildDownloadEntity tChildDownload);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
