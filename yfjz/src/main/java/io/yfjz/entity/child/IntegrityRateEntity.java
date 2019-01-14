package io.yfjz.entity.child;

import io.yfjz.entity.PersistentEntity;

import java.io.Serializable;

/**
 * @method_name: 儿童基本资料完整性汇总
 * @describe:
 * @param 
 * @return 
 * @author 饶士培
 * @QQ: 1013147559@qq.com
 * @tel:18798010686
 * @date: 2018-04-19  9:47
 **/
public class IntegrityRateEntity implements Serializable, PersistentEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int totalChild;
	private int year;
	private int fullCount;
	private int fullNameCount;//
	private int fullSexCount;
	private int fullBirthTimeCount;
	private int fullTelCount;//联系电话
	private int fullMothernameCount;//家长姓名-监护人姓名
	private int fullHabiIdCount;//户口县国标
	private int fullHukouAddressCount;
	private int fullContactAddressCount;
	private int fullCreateSiteCount;
	private double integrityRate;
	/*private String qualifiedNumber;
	private String qualifiedRate;*/

	public int getFullCreateSiteCount() {
		return fullCreateSiteCount;
	}

	public void setFullCreateSiteCount(int fullCreateSiteCount) {
		this.fullCreateSiteCount = fullCreateSiteCount;
	}

	public int getTotalChild() {
		return totalChild;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setTotalChild(int totalChild) {
		this.totalChild = totalChild;
	}

	public int getFullCount() {
		return fullCount;
	}

	public void setFullCount(int fullCount) {
		this.fullCount = fullCount;
	}

	public int getFullNameCount() {
		return fullNameCount;
	}

	public void setFullNameCount(int fullNameCount) {
		this.fullNameCount = fullNameCount;
	}

	public int getFullSexCount() {
		return fullSexCount;
	}

	public void setFullSexCount(int fullSexCount) {
		this.fullSexCount = fullSexCount;
	}

	public int getFullBirthTimeCount() {
		return fullBirthTimeCount;
	}

	public void setFullBirthTimeCount(int fullBirthTimeCount) {
		this.fullBirthTimeCount = fullBirthTimeCount;
	}

	public int getFullTelCount() {
		return fullTelCount;
	}

	public void setFullTelCount(int fullTelCount) {
		this.fullTelCount = fullTelCount;
	}

	public int getFullMothernameCount() {
		return fullMothernameCount;
	}

	public void setFullMothernameCount(int fullMothernameCount) {

		this.fullMothernameCount = fullMothernameCount;
	}

	public int getFullHabiIdCount() {
		return fullHabiIdCount;
	}

	public void setFullHabiIdCount(int fullHabiIdCount) {
		this.fullHabiIdCount = fullHabiIdCount;
	}

	public int getFullHukouAddressCount() {
		return fullHukouAddressCount;
	}

	public void setFullHukouAddressCount(int fullHukouAddressCount) {
		this.fullHukouAddressCount = fullHukouAddressCount;
	}

	public int getFullContactAddressCount() {
		return fullContactAddressCount;
	}

	public void setFullContactAddressCount(int fullContactAddressCount) {
		this.fullContactAddressCount = fullContactAddressCount;
	}

	public double getIntegrityRate() {
		double result = (getFullCount()*1.0/getTotalChild()*100);
				result = Double.parseDouble(String.format("%.2f", result));

		return result;
	}

	public void setIntegrityRate(double integrityRate) {
		this.integrityRate = integrityRate;
	}

}
