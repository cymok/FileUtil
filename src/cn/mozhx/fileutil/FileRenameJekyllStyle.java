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
	private static final String DEFAULT_TIME_OLD="2018-03-17";//原获取到的默认时间,后面可能会设置新默认时间
	private static final String DEFAULT_TIME_NEW="2015-07-01";//7月开始接触Android

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
			if (file.isFile()) {
				String name = file.getName();
				if (!(name.endsWith("txt") || name.endsWith("md") || name.endsWith("markdown"))) {
					continue;
				}
				nameAddTime(file);
			} else if (file.isDirectory()) {
				renameFileAddTime(file);
			}
		}
	}

	/**
	 * 重命名文件 在前面加上时间
	 * 时间取创建时间和修改时间中较前的时间
	 * *复制文件时有可能修改时间比创建时间较前
	 * 
	 * @param file 目标文件
	 */
	private static void nameAddTime(File file) {
		String oldPath = file.getAbsolutePath();
		System.out.println("---");
		System.out.println("文件原名称:\n" + oldPath);

		Date createTime = getFileCreateTime(oldPath);
		Date modifyTime = getFileModifyTime(oldPath);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat SDF = new SimpleDateFormat(pattern, Locale.getDefault());

		Date date;
		if(createTime.before(modifyTime)){
			date = createTime;
		}else{
			date = modifyTime;
		}

		String time = SDF.format(date);
		if(time.equals(DEFAULT_TIME_OLD)){
			time = DEFAULT_TIME_NEW;
		}
		String newName = time + "-" + file.getName();

		File newPath = new File(file.getParent(), newName);
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
			try {
				scanner.close();
			} catch (Exception e) {
				e.printStackTrace();
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
	public static Date getFileCreateTime(String filename) {
		Path path = Paths.get(filename);
		BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attr;
		try {
			attr = basicview.readAttributes();
			Date date = new Date(attr.creationTime().toMillis());
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取文件修改时间
	 * 
	 * @param filename 目标文件
	 * @param pattern  时间格式
	 * @return 修改时间
	 */
	public static Date getFileModifyTime(String filename) {
		Path path = Paths.get(filename);
		BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attr;
		try {
			attr = basicview.readAttributes();
			Date date = new Date(attr.lastModifiedTime().toMillis());
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
