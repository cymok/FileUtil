package cn.mozhx.fileutil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

	/**
	 * Return the charset of file simply.
	 *
	 * @param filePath The path of file.
	 * @return the charset of file simply
	 */
	public static String getFileCharsetSimple(final String filePath) {
		if (filePath == null) {
			throw new RuntimeException("路径不能为空");
		}
		return getFileCharsetSimple(new File(filePath));
	}

	/**
	 * Return the charset of file simply.
	 *
	 * @param file The file.
	 * @return the charset of file simply
	 */
	public static String getFileCharsetSimple(final File file) {
		int p = 0;
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			p = (is.read() << 8) + is.read();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		switch (p) {
		case 0xefbb:
			return "UTF-8";
		case 0xfffe:
			return "Unicode";
		case 0xfeff:
			return "UTF-16BE";
		default:
			return "UNKNOW";
		}
	}

}
