package org.evosuite.basic;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OtherReadFile implements FilenameFilter {

	private Pattern pattern;

	//
	// public OtherReadFile(String regex){
	// pattern = Pattern.compile(regex);
	// }
	//
//	public boolean accept(File dir, String name) {
//		if (name.endsWith(".java")) {
//			return pattern.matcher(name).matches();
//		} else {
//			return false;
//		}
//	}

	public static ArrayList<File> getListFiles(Object obj) {
		File directory = null;
		if (obj instanceof File) {
			directory = (File) obj;
		} else {
			directory = new File(obj.toString());
		}
		ArrayList<File> files = new ArrayList<File>();
		if (directory.isFile()) {
			files.add(directory);
			return files;
		} else if (directory.isDirectory()) {
			File[] fileArr = directory.listFiles();
			for (int i = 0; i < fileArr.length; i++) {
				File fileOne = fileArr[i];
				files.addAll(getListFiles(fileOne));
			}
		}
		return files;
	}
	//
	@Override
	public boolean accept(File dir, String name) {
		if (dir.isDirectory() && name.endsWith(".java")) {
			return true;
		} else {
			return false;
		}
	}
}
