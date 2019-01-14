package io.yfjz.utils.excel;

import io.yfjz.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @class_name: ExcelUtils
 * @describe:
 * 		实现抽象方法并调用方法返回数据,处理15w条数据以上!
 * 		主要是用来读取百万级别的excel  高版本的excel，防止内存溢出
 * @author: 刘琪
 * @QQ：1018628825@qq.com
 * @tel:15685413726
 * @date: 2018/6/26  17:11
 **/
public final class ExcelUtils extends ExcelHandler {

	private int index;
	//标题头 用来设置标题的字段  在mapper中的key值
	private HashMap<String,String> mapper;
	//存放标题头
	private List<String> listTitle = new ArrayList<>();
	//存放标题头对应的key
	private ArrayList<String> keys = new ArrayList<>();
	private ArrayList<HashMap<String,String>> listHashMaps = new ArrayList<>();

	/**
	 * @method_name: ExcelUtils
	 * @describe:
	 * 		index 指定表头行,标题行必须放在第1行
	 * 		final ExcelImport excel = new ExcelImport(1);
	 * 		excel.readExcel("E:\\test.xlsx");
	 *		获取数据 final ArrayList<HashMap<String,String>> list = excel.getExcelData();
	 * @param: [index]
	 * @return:
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date:
	 **/
	public ExcelUtils(final int index){
		this.index = index;
	}

	/**
	 * @method_name: ExcelUtils
	 * @describe:
	 * 		title 指定表头行,mapper指定表头及数据字段名,标题行必须放在第1行
	 *		final HashMap<String,String> mapper = new HashMap<String,String>();
	 *		mapper.put("接种部位","jiezhongbuwei");
	 *		excel.readExcel("E:\\test.xlsx");
	 *		获取数据：final ArrayList<HashMap<String,String>> list = excel.getExcelData();
	 * @param: [index, mapper]
	 * @return:
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date:
	 **/
	public ExcelUtils(final int index, final HashMap<String,String> mapper){
		this.index = index;
		this.mapper = mapper;
	}
	
	/**
	 * @method_name: optRows
	 * @describe: 实现抽象方法
	 * @param: [countrows, titlelist, rowlist]
	 * @return: void
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/6/26  17:08
	 **/
	@Override
	public void optRows(final int countrows,final ArrayList<String> titlelist,final ArrayList<String> rowlist){
		if(countrows == index){//指定表头行
			listTitle = Arrays.asList(titlelist.get(0).split(","));
			for (int i = 0; i < listTitle.size(); i++){
				for(final String key : mapper.keySet()){
					if(listTitle.get(i).equalsIgnoreCase(key)){
						keys.add(mapper.get(key));
					}
				}
			}
		}else{
			final HashMap<String,String> map = new HashMap<>();
			if(!CommonUtils.isEmpty(rowlist)){
				final List<String> rows = Arrays.asList(rowlist.get(0).split(","));
				for(int i = 0; i < listTitle.size();i++){
					map.put(keys.get(i),rows.get(i));
				}
				listHashMaps.add(map);
			}
		}
	}
	
	/**
	 * @method_name: getExcelData
	 * @describe: 最终获取excel的行数据的方法
	 * @param: []
	 * @return: java.util.ArrayList<java.util.HashMap < java.lang.String , java.lang.String>>
	 * @author: 刘琪
	 * @QQ: 1018628825@qq.com
	 * @tel:15685413726
	 * @date: 2018/6/26  17:06
	 **/
	public ArrayList<HashMap<String,String>> getExcelData(){
		return listHashMaps;
	}

}