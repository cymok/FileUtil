package cn.mozhx.fileutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * 将目录下所有格式符合的文件复制到指定目录的根目录
 * 
 * @author cymok
 *
 */
public class FileCopy {
	public static String rootDirPath;
	public static String targetDirPath;

	public static void main(String[] args) {
		File dir = getFileDirPath("输入源文件夹路径:");
		rootDirPath = dir.getAbsolutePath();
		File targetDir = getFileDirPath("输入目标文件夹路径:");
		targetDirPath = targetDir.getAbsolutePath();
		copy2newDir(dir, targetDir);
	}

	private static void copy2newDir(File dir, File targetDir) {
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
				String newPath = file.getAbsolutePath().replace(rootDirPath, targetDirPath);
				try {
					File newFile = new File(newPath);
					copyFileUsingJava7Files(file, newFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				copy2newDir(file,targetDir);
			}
		}
	}

	private static void copyFileUsingJava7Files(File source, File dest)
	        throws IOException {
			dest.getParentFile().mkdirs();
	        Files.copy(source.toPath(), dest.toPath());
	}

	/**
	 * 手动输入文件夹路径
	 * 
	 * @return 输入的文件夹File对象
	 */
	private static File getFileDirPath(String tip) {
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

}
