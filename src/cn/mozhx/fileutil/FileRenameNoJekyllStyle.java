package cn.mozhx.fileutil;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 
 * Jekyll的识别格式必须是yyyy-MM-dd-fileName
 * <p/>
 * 此工具能重命名文件, 去掉非txt和md后缀文件在前面的时间
 * 
 * @author cymok
 *
 */
public class FileRenameNoJekyllStyle {

	public static final String REGEX = "^\\d{4}\\-\\d{2}\\-\\d{2}\\-";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File dir = getFileDirPath();
		renameFileRemoveTime(dir);
	}

	/**
	 * 
	 * 重命名目录下所有文件, 将会去掉在前面的时间, 即yyyy-MM-dd-realName --> realName
	 * 
	 * @param dir 目标文件夹
	 */
	private static void renameFileRemoveTime(File dir) {
		System.out.println("-------------");
		System.out.println("遍历文件夹:\n" + dir.getAbsolutePath());
		for (File file : dir.listFiles()) {
			if (file == null || !file.exists()) {
				continue;
			}
			if (file.isFile()) {
				String name = file.getName();
				if (name.endsWith("txt") || name.endsWith("md") || name.endsWith("markdown")) {
					continue;
				}
				nameRemoveTime(file);
			} else if (file.isDirectory()) {
				renameFileRemoveTime(file);
			}
		}
	}

	/**
	 * 重命名文件 将会去掉在前面的时间
	 * 
	 * @param file 目标文件
	 */
	private static void nameRemoveTime(File file) {
		String oldPath = file.getAbsolutePath();
		String oldName = file.getName();
		System.out.println("---");
		System.out.println("文件原名称:\n" + oldPath);

		boolean match = Pattern.matches(REGEX+".+", oldName);
		if(!match){
			return;
		}
		String newName = oldName.replaceAll(REGEX, "");

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

}
