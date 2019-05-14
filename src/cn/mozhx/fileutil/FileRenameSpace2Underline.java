package cn.mozhx.fileutil;

import java.io.File;

public class FileRenameSpace2Underline {
	public static void main(String[] args) {
		File dir = FileUtils.getFileDirPath("输入路径");
		FileUtils.findFile(dir, Constant.ARR_FILE_TYPE, new FileUtils.Listener() {

			@Override
			public void action(File file) {
				String path = file.getAbsolutePath();
				if (path.contains(" ")) {
					path = path.replace(" ", "_");
					File newFile = new File(path);
					file.renameTo(newFile);
				}
			}
		});
	}

}
