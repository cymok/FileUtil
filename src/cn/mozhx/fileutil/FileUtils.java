package cn.mozhx.fileutil;

import java.io.File;
import java.util.Scanner;

public class FileUtils {

	/**
	 * 手动输入文件夹路径
	 * 
	 * @return 输入的文件夹File对象
	 */
	public static File getFileDirPath(String tip) {
		Scanner scanner = new Scanner(System.in);
		String inputStr;
		File dir;
		while (true) {
			System.out.println(tip);
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
		return dir;
	}

	public interface Listener {
		void action(File file);
	}

	public static void findFile(File dir, String[] arrFile, Listener listener) {
		System.out.println("-------------");
		System.out.println("遍历文件夹:\n" + dir.getAbsolutePath());
		for (File file : dir.listFiles()) {
			if (file == null || !file.exists()) {
				continue;
			}
			if (file.isFile()) {
				String name = file.getName();
				boolean isType = false;
				for (int i = 0; i < arrFile.length; i++) {
					String type = arrFile[i];
					if (name.endsWith(type)) {
						isType = true;
					}
				}
				// 如果一个都没有
				if (!isType) {
					continue;
				}
				listener.action(file);
			} else if (file.isDirectory()) {
				findFile(file, arrFile, listener);
			}
		}
	}

}
