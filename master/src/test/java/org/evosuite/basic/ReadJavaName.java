package org.evosuite.basic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ReadJavaName {

	public static void main(String[] args) {
		// File path = new File("../../SF110-20130704-src/.java");
		// String[] list;
		// if (args.length == 0) {
		// list = path.list();
		// }else {
		// list = path.list(new OtherReadFile(args[0]));
		// }
		// for(String dir:list){
		// System.out.println(dir);
		// }

		ArrayList<File> files = OtherReadFile.getListFiles("../../SF110-20130704-src/");
//		System.out.println(files.size());
		// System.out.println(files.get(0));
		int [] getClassIndex = new int[50];
		Random rand = new Random();
		for (int i = 0; i < getClassIndex.length; i++) {
			getClassIndex[i] = rand.nextInt(files.size());
			System.out.println(files.get(getClassIndex[i]));
		}
	}

}
