package cn.mozhx.fileutil;

import java.io.File;
import java.util.Scanner;

/**
 * 
 * Jekyll的识别格式必须是yyyy-MM-dd-fileName
 * <p/>
 * 此工具能重命名文件, 在前面添加文件创建时间
 * 
 * @author cymok
 *
 */
public class FileCharsetUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = getFileDirPath();
		charsetConvert(dir);
		System.out.println("---------");
		System.out.println("---end---");
	}

	/**
	 * 
	 * 重命名目录下所有文件, 将会在前面添加上创建时间, 格式是Jekyll模版的格式, 即yyyy-MM-dd-oldName
	 * 
	 * @param dir 目标文件夹
	 */
	private static void charsetConvert(File dir) {
		System.out.println("-------------");
		System.out.println("遍历文件夹:\n" + dir.getAbsolutePath());
		for (File file : dir.listFiles()) {
			if (file == null || !file.exists()) {
				continue;
			}
			if (file.isFile()) {
				String name = file.getName();
				if (!(name.endsWith("txt") || name.endsWith("md") || name.endsWith("markdown"))) {
					continue;
				}
//				nameAddTime(file);
//				logFileCharset(file);
				try {
					String c = EncodeUtil.getEncode(file.getAbsolutePath(), false);
					System.out.println("文件:\n" + file.getAbsolutePath() + "\n编码:" + c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				charsetConvert(file);
			}
		}
	}

	/**
	 * 获取文件内容编码
	 * 
	 * @param file 目标
	 * @return 编码
	 */
	private static void logFileCharset(File file) {
		String charset;
		try {
			charset = EncodeUtil.getEncode(file.getAbsolutePath(), false);
			System.out.println("---");
			System.out.println("文件:" + file.getAbsolutePath());
			System.out.println("编码:" + charset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 手动输入文件夹路径
	 * 
	 * @return 输入的文件夹File对象
	 */
	private static File getFileDirPath() {
		Scanner scanner = new Scanner(System.in);
		String inputStr;
		File dir;
		while (true) {
			System.out.println("输入文件夹路径:");
			inputStr = scanner.nextLine();
			if (inputStr == null || inputStr.length() == 0) {
				continue;
			}
			dir = new File(inputStr);
			if (dir == null || !dir.exists() || !dir.isDirectory()) {
				continue;
			}
			break;
		}
		System.out.println("输入路径为:\n" + inputStr);
		return dir;
	}

}
