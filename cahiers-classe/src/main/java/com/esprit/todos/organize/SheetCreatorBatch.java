package com.esprit.todos.organize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ego
 */
public class SheetCreatorBatch {

	static XSSFRow row;
	List<Model> models = new ArrayList<>();
	private String laClasse;
	private String leModule;
	private Map<String, List<String>> map;

	public void createFolders() throws Exception {
		InputStream fis = getClass().getResourceAsStream("/Liste_module.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = spreadsheet.iterator();

		int i = 0;
		while (rowIterator.hasNext()) {
			row = (XSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {

				case Cell.CELL_TYPE_STRING:

					// Path newDirectoryPath = Paths
					// .get("C:\\Users\\daly\\Google Drive\\cahiers de classe\\2015-2016\\S1\\P2\\"
					// + folder);

					// if (!Files.exists(newDirectoryPath)) {
					// try {
					// Files.createDirectory(newDirectoryPath);
					// } catch (IOException e) {
					// System.err.println(e);
					// }
					// }

					if (i % 2 == 0) {
						laClasse = cell.getStringCellValue().trim();
					} else {
						leModule = cell.getStringCellValue().trim()
								.replace(":", "-").replace("\"", "'")
								.replace("/", "-");
					}

					i++;
					break;
				}

			}
			models.add(new Model(laClasse, leModule));
		}
		fis.close();

		for (Model model : models) {
			// System.out.println(model);
		}

		map = models.stream().collect(
				Collectors.groupingBy(
						Model::getGroupe,
						Collectors.mapping(Model::getModule,
								Collectors.toList())));

		// map.forEach((k, v) -> System.out.println(k + v));

		map.forEach((k, v) -> {

			createSubFolder(k);
			for (String s : v) {
				createSubFolder(k + "\\" + s);
				try {
					// créer un nouveau fichier excel
					FileOutputStream out = new FileOutputStream(
							"C:\\Users\\daly\\Google Drive\\cahiers de classe\\2015-2016\\S1\\P2\\"
									+ k + "\\" + s + "\\" + "[" + s
									+ "][2015-2016][S1][P2][" + k + "].xls");
					// créer un classeur
					Workbook wb = new HSSFWorkbook();
					// créer une feuille
					Sheet mySheet = wb.createSheet();

					// créer une ligne de à l'index 2 dans la feuille Excel
					Row myRow = null;
					myRow = mySheet.createRow(2);

					// Ajouter des données dans les cellules
					myRow.createCell(0).setCellValue("exemple");

					wb.write(out);

					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}

	public void createSubFolder(String subFolder) {
		Path newDirectoryPath = Paths
				.get("C:\\Users\\daly\\Google Drive\\cahiers de classe\\2015-2016\\S1\\P2\\"
						+ subFolder);

		if (!Files.exists(newDirectoryPath)) {
			try {
				Files.createDirectory(newDirectoryPath);
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

	public static void main(String[] args) throws Exception {
		new SheetCreatorBatch().createFolders();
	}
}