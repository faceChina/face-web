package org.dbunit.tools.writer;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * @ClassName: FileWrite 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月3日 下午5:54:18
 */
public class FileWrite {

	private static final String line_bytes = "\n";
	private StringBuilder content = new StringBuilder();
	FileOutputStream fos = null;
	BufferedOutputStream bos = null;

	public FileWrite(String filePath) throws RuntimeException {
		try {
			fos = new FileOutputStream(filePath);
			bos = new BufferedOutputStream(fos);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public FileWrite next(String message) throws RuntimeException {
		content.append(line_bytes).append(message);
		return this;
	}

	public FileWrite append(String message) throws RuntimeException {
		content.append(message);
		return this;
	}
	
	public void write() throws RuntimeException {
		try {
			bos.write(content.toString().getBytes());
			this.flush();
			this.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void flush() throws RuntimeException {
		try {
			bos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// 关闭
	private void close() throws RuntimeException {
		try {
			if (null != bos) {
				bos.close();
			}
			if (null != fos) {
				fos.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
