package cn.mozhx.fileutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
		File dir = FileUtils.getFileDirPath("输入源文件夹路径:");
		rootDirPath = dir.getAbsolutePath();
		File targetDir = FileUtils.getFileDirPath("输入目标文件夹路径:");
		targetDirPath = targetDir.getAbsolutePath();
		FileUtils.findFile(dir, Constant.ARR_FILE_TYPE, new FileUtils.Listener() {
			
			@Override
			public void action(File file) {
				String newPath = file.getAbsolutePath().replace(rootDirPath, targetDirPath);
				try {
					File newFile = new File(newPath);
					copyFileUsingJava7Files(file, newFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static void copyFileUsingJava7Files(File source, File dest)
	        throws IOException {
			dest.getParentFile().mkdirs();
	        Files.copy(source.toPath(), dest.toPath());
	}

}
