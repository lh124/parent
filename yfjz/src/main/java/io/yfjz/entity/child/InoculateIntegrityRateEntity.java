package io.yfjz.entity.child;

import io.yfjz.entity.PersistentEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @method_name: 儿童接种信息完整性统计实体
 * @describe:
 * @param 
 * @return 
 * @author 饶士培
 * @QQ: 1013147559@qq.com
 * @tel:18798010686
 * @date: 2018-09-14  11:47
 **/
@Getter
@Setter
public class InoculateIntegrityRateEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String inocBactCode;
	private String bioName;
	private int totalInoTimes;
	private int fullCount;
	private int fullBioNameCount;//接种疫苗
	private int fullInoDateCount;//接种日期
	private int fullInocBatchnoCount;//疫苗批号
	private int fullInocCorpCodeCount;//生产厂家
	private int fullInocRoadCount;//接种途径
	private int fullInocInplIdCount;//接种部位
	private int fullInocPlaceCount;//接种地点
	private int fullPayStatusCount;//收费状态
	private int fullInocNurseCount;//接种医生
	private int fullTimelyCount;//及时录入
	private double inocIntegrityRate;//完整率
	public double getInocIntegrityRate() {
		double result = (getFullCount()*1.0/getTotalInoTimes()*100);
		result = Double.parseDouble(String.format("%.2f", result));
		return result;
	}

	public void setInocIntegrityRate(double integrityRate) {
		this.inocIntegrityRate = integrityRate;
	}
}


