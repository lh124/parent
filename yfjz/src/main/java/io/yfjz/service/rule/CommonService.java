package io.yfjz.service.rule;

/**
 * 规则公共接口
 *
 * @author 邓召仕
 * @Description:
 * @date: 2018-09-07 11:17
 */
public interface CommonService {
    /**
     * 查询脊灰接种总数
     * @describe:
     * @param childCode
     * @param monthAge
     * @return int
     * @author 邓召仕
     * @date: 2018-09-07  11:22
     **/
    int queryPvTotal(String childCode, int monthAge);

    /**
     * 除bOPV外脊灰接种总数
     * @describe:
     * @param childCode
     * @param monthAge
     * @return int
     * @author 邓召仕
     * @date: 2018-09-07  11:24
     **/
    int queryUnBOPVTotal(String childCode, int monthAge);

    /**
     * ipv总数，不区分基础加强
     * @describe:
     * @param childCode
     * @param monthAge
     * @return int
     * @author 邓召仕
     * @date: 2018-09-07  11:24
     **/
    int queryIPVTotal(String childCode, int monthAge);

    /**
     * 百白破加白破总剂次
     * @describe:
     * @param childCode
     * @param monthAge
     * @return int
     * @author 邓召仕
     * @date: 2018-09-07  11:25
     **/
    int queryDtapDtTotal(String childCode, int monthAge);
}
