package cn.mozhx.fileutil;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
public class FileRenameJekyllStyle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = getFileDirPath();
		renameFileAddTime(dir);
	}

	/**
	 * 
	 * 重命名目录下所有文件, 将会在前面添加上创建时间, 格式是Jekyll模版的格式, 即yyyy-MM-dd-oldName
	 * 
	 * @param dir 目标文件夹
	 */
	private static void renameFileAddTime(File dir) {
		System.out.println("-------------");
		System.out.println("遍历文件夹:\n" + dir.getAbsolutePath());
		for (File file : dir.listFiles()) {
			if (file == null || !file.exists()) {
				continue;
			}
			if (file.isFile() && !file.getName().equals(".DS_Store")) {
				nameAddTime(file);
			} else if (file.isDirectory()) {
				renameFileAddTime(file);
			}
		}
	}

	/**
	 * 重命名文件 在前面加上时间
	 * 
	 * @param file 目标文件
	 */
	private static void nameAddTime(File file) {
		String oldPath = file.getAbsolutePath();
		System.out.println("---");
		System.out.println("文件原名称:\n" + oldPath);
		String pattern = "yyyy-MM-dd";
		String createTime = getFileTimeInfo(oldPath, pattern);
		File newPath = new File(file.getParent(), createTime + "-" + file.getName());
		boolean b = file.renameTo(newPath);
		if (b) {
			System.out.println("重命名成功, 文件名为:\n" + newPath.getAbsolutePath());
		} else {
			System.out.println("重命名失败");
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

	/**
	 * 获取文件创建时间
	 * 
	 * @param filename 目标文件
	 * @param pattern  时间格式
	 * @return 创建时间
	 */
	public static String getFileTimeInfo(String filename, String pattern) {
		Path path = Paths.get(filename);
		BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attr;
		try {
			attr = basicview.readAttributes();
			// attr.lastModifiedTime();
			Date createTimeDate = new Date(attr.creationTime().toMillis());

			SimpleDateFormat SDF = new SimpleDateFormat(pattern, Locale.getDefault());
			String createTime = SDF.format(createTimeDate);
			return createTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
