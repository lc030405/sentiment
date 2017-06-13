package kse.seu.edu.cn.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kse.seu.edu.cn.classifier.Phrase;

/**
 * Created by LiuFeng on 2017/6/6.
 */
public class FileUtils {

	public static Map<String, Double> file2Dic(String phraseFilePath) {
		Map<String, Double> results = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(phraseFilePath));
			results = new HashMap<>();
			String line = null;
			while((line=br.readLine())!=null){
				if(line.isEmpty())
					continue;
				int index = 0;
				if(line.contains("...") && line.contains("	"))
					index = line.trim().lastIndexOf("	");
				else{
					if(line.contains("\t"))
						index= line.trim().lastIndexOf("\t");
					else if(line.contains("//"))
						index= line.trim().lastIndexOf("//")+2;
					else 
						index= line.trim().lastIndexOf(" ");
				}
					
				if (index <= 0){
					continue;
				} 
				results.put(line.substring(0, index).trim(),Double.parseDouble(line.substring(index).trim()) );
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(phraseFilePath+" file is not exist");
		} catch (IOException e) {
			throw new RuntimeException(phraseFilePath+" file has IO error");
		} finally{
			try {
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("IO error");
			}
		}
		return results;
	}

	public static List<Phrase> parsePhraseDic(String phraseFilePath) {
		List<Phrase> results = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(phraseFilePath));
			results = new ArrayList<>();
			String line = null;
			while((line=br.readLine())!=null){
				if(line.isEmpty())
					continue;
				String[] fields = line.trim().split("\\s+");
				if(fields.length>=2){
					Phrase phrase = new Phrase(fields[0], fields[1]);
					
					if(fields.length>2){
						for (int i = 2; i < fields.length; i++) {
							String[] res = fields[i].trim().split(":");
							if(res[0].trim().equals("start")){
								phrase.setStart(res[1]);
							} else if(res[0].trim().equals("end")){
								phrase.setEnd(res[1]);
							} else if(res[0].trim().equals("head")){
								phrase.setHead(res[1]);
							} else if(res[0].trim().equals("between_tag")){
								phrase.setBetween_tag(res[1]);
							} 
							
						}
					}
					
					results.add(phrase);

				} else
					System.out.println(fields[0].trim());
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(phraseFilePath+" file is not exist");
		} catch (IOException e) {
			throw new RuntimeException(phraseFilePath+" file has IO error");
		} finally{
			try {
				if(br!=null){
					br.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("IO error");
			}
		}
		return results;
	}
	
	public static void writeString2File(String runoutFilePath, String clause) {
		BufferedWriter bw = null;
		try { // true表示追加
			bw = new BufferedWriter(new FileWriter(runoutFilePath, true));
		} catch (IOException e) {
			throw new RuntimeException(runoutFilePath+" file has IO error");
		} finally {
			try {
				if(bw!=null){
					bw.close();
				}
			} catch (IOException e) {
				throw new RuntimeException("IO error");
			}
		}	
	}

	public static void writeSList2File(String runoutFilePath, List<String> clauses) {
		writeString2File(runoutFilePath,clauses.toString());	
	}

	public static List<String> readLines(String filePath) {
		ArrayList<String> contentLines = new ArrayList<>();
		try {
			File aFile = new File(filePath);
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				String line = null;
				while ((line = input.readLine()) != null) {
					if(line.isEmpty())
						continue;
					contentLines.add(line);
				}
			} finally {
				input.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return contentLines;
	}
}
