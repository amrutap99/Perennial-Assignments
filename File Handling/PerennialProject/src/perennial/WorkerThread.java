package perennial;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class WorkerThread extends Thread {
	private String filePath;
	private String inprocessDir;

	WorkerThread(String filePath, String inprocessDir) {
		this.filePath = filePath;
		this.inprocessDir = inprocessDir;
	}

	private static DatabaseConnection db = new DatabaseConnection();

	public void run() {
		System.out.println("Worker Thread started for file " + filePath);

		try {
			db.getConnection();

			File dir = new File(this.inprocessDir);

			File[] filesList = dir.listFiles();
			Scanner sc = null;
			String read = null;

			FileReader reader = new FileReader("D:\\Eclipse Workspace\\PerennialProject\\FileNames.properties");
			Properties p = new Properties();
			p.load(reader);

			for (File file : filesList) {
				String str = p.getProperty(file.getName().toString().replace(".txt", ""));
				if (str != null) {
					String[] items = str.split(","); // splitting string from.prop file into multiple strings by comma &
														// storing in string array
					sc = new Scanner(file);

					while (sc.hasNextLine()) {
						read = sc.nextLine();
						String commaSeparated = read.replace(" ", ",").replace("[", ",").replace("]", ",");

						String[] splited = commaSeparated.split(","); // splitting string from incoming file into
																		// multiple strings by comma & storing in string
																		// array

						int flag = 0;

						if (splited.length == items.length) {
							for (int i = 0; i < splited.length; i++) {

								if (splited[i].length() != Integer.parseInt(items[i])) {
									flag = 1;

								}

							}
							if (flag == 1) {
								getInvalidRecords(commaSeparated, file);

							} else {
								getValidRecords(commaSeparated, file);

							}
						} else {
							getInvalidRecords(commaSeparated, file);

						}

					}

				}

			}

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}

	}

	private static void getValidRecords(String commaSeparated, File file) throws IOException, SQLException {
		// TODO Auto-generated method stub

		// System.out.println("Valid records in file = " + file.getName());
		// System.out.println(read);
		File validFile = new File(file.getName().toString().replace(".", "_ValidRecord."));
		if (!validFile.exists()) {
			validFile.createNewFile();
		}
		FileWriter fw = new FileWriter(validFile.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(commaSeparated);
		bw.newLine();
		db.insertValidFilerecord(commaSeparated, file);

		bw.close();
		// System.out.println("-----------------------------------------------");

	}

	private static void getInvalidRecords(String commaSeparated, File file) throws IOException, SQLException {
		// TODO Auto-generated method stub
		// System.out.println("InValid records in file =" + file.getName());

		// System.out.println(read);
		File invalidFile = new File(file.getName().toString().replace(".", "_InvalidRecord."));
		if (!invalidFile.exists()) {
			invalidFile.createNewFile();

		}
		FileWriter fw = new FileWriter(invalidFile.getAbsoluteFile(), true);
		System.out.println("\n");

		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(commaSeparated);
		bw.newLine();
		db.insertInValidFilerecord(commaSeparated, file);

		bw.close();

		// System.out.println("-----------------------------------------------");

	}

}
