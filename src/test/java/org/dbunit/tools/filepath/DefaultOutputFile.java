package org.dbunit.tools.filepath;

public class DefaultOutputFile implements OutputFile {

	private static final String CLAZZ_PATH = "target/test-classes";
	private static final String JAVA_PATH = "src/test/java";
	private static final String POINT = ".";
	
	private Class<?> clazz;
	private String classPath;
	private String javaPath;
	
	public DefaultOutputFile(Class<?> clazz){
		this(clazz, CLAZZ_PATH, JAVA_PATH);
	}
	public DefaultOutputFile(Class<?> clazz, String classPath, String javaPath){
		if (null == clazz) { 
			throw new RuntimeException("Class can't be null.");
		}
		this.clazz = clazz;
		this.classPath = classPath;
		this.javaPath = javaPath;
	}
	
	public String getDirectory() {
		if (null == clazz) { 
			throw new RuntimeException("Class is null, can't init file path.");
		}
		return clazz.getResource(POINT).getPath().replace(classPath, javaPath);
	}
	
	public String getOutFilePath(String fileName) {
		return this.getDirectory() + fileName;
	}
	
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}
}
