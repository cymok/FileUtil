package cn.mozhx.fileutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class ConvertUtils {

	public static void main(String[] args) {
		File dir = FileUtils.getFileDirPath("输入路径");
		FileUtils.findFile(dir, Constant.ARR_FILE_TYPE, new FileUtils.Listener() {

			@Override
			public void action(File file) {
				try {
					String inCharset = EncodeUtil.getEncode(file.getAbsolutePath(), true);

					String outChartset = "UTF-8";
					File outFile = new File(file.getAbsolutePath() + "." + outChartset);
					if (inCharset.contains(outChartset)) {
						return;
					}
					other2utf8(file, inCharset, outFile, outChartset);

					File backFile = new File(file.getAbsolutePath() + "." + inCharset + ".back");
//					file.renameTo(backFile);
					file.delete();

					outFile.renameTo(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void other2utf8(File inFile, String inChartset, File outFile, String outCharset) {
		System.out.println("-------");
		System.out.println("编码inFile=" + inFile.getAbsolutePath());
		System.out.println("编码inChartset=" + inChartset);
		try {
			FileInputStream fileInputStream = new FileInputStream(inFile);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, inChartset);
			BufferedReader reader = new BufferedReader(inputStreamReader);

			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, outCharset);

			String line = "";
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				writer.write(line + "\n");
			}

			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static String changeCharset(String oldStr, String charset) throws UnsupportedEncodingException {
		return new String(oldStr.getBytes(), charset);
	}
}
