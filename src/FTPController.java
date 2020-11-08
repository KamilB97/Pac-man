

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class FTPController {
	//  put name and score 
	public void saveGame(String name, int result) {
		FTPdownloader downloader = new FTPdownloader();
		FTPuploader uploader = new FTPuploader();
		

		downloader.downloadSaveFile();

		try {
		String saveTxt = readTxtFile(FTPStaticData.LOCAL_SAVE_PATH);
			saveTxt += name + "," + result + ";\n";

			saveTxt = ResultsSorter.sortResults(saveTxt);

			System.out.println(saveTxt);

			saveTxtFile(saveTxt, FTPStaticData.LOCAL_SAVE_PATH);
			uploader.uploadSaveFile();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static  String downloadResults() throws IOException {
		FTPdownloader downloader = new FTPdownloader();
		downloader.downloadSaveFile();
		String saveTxt = readTxtFile(FTPStaticData.LOCAL_SAVE_PATH);
		System.out.println("Odczytano save:");
		System.out.println(saveTxt);

		return saveTxt;

	}
// get  label 
	public String[] download3BestResults(int resultsSize) {
		try {
			String results[] = downloadResults().split(";\n");
			String bestResult[] = new String[resultsSize];
			for (int i = 0; i <= resultsSize-1; i++) {
				if(i<results.length)
					bestResult[i] = results[i];
				else 
					bestResult[i] = "brak";

			}
			return bestResult;
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	private String saveTxtFile(String data, String path) throws FileNotFoundException {

		try (PrintWriter out = new PrintWriter(path)) {
			out.println(data);
			out.close();
		}

		return "";
	}

	private static String readTxtFile(String filePath) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		String txtString = "";
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				if (line != "\n" && line != "" && !line.equals("") && !line.equals("\n")) {
					sb.append(line);
					sb.append(System.lineSeparator());
				}
				line = br.readLine();
			}
			txtString = sb.toString();
		} finally {
			br.close();
		}

		return txtString;

	}
}
