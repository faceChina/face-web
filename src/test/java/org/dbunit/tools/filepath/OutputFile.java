package org.dbunit.tools.filepath;

public interface OutputFile {

	/**
	 * 当前类目录
	 * 
	 * @Title: getDirectory
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param clazz
	 *            当前类
	 * @return
	 * @date 2015年7月10日 上午11:44:34
	 * @author lys
	 */
	String getDirectory();

	/**
	 * 获取要生成文件的全路径，指定文件名
	 * 
	 * @Title: getOutFilePath
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param clazz
	 *            当前类
	 * @param fileName
	 *            文件名
	 * @return
	 * @date 2015年7月10日 上午11:45:14
	 * @author lys
	 */
	String getOutFilePath(String fileName);
}
