package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelSheetHandling {

	public void writeExcelSheet(String[] facilityNames, String statename, String districtname) throws IOException {

		String path = System.getProperty("user.dir") + "/src/test/resources/TestData/Demo.xlsx";
		File excelfile = new File(path);
		XSSFWorkbook workbook;
		if (excelfile.exists()) {
			FileInputStream Fis = new FileInputStream(excelfile);
			workbook = new XSSFWorkbook(Fis);
		} else {
			workbook = new XSSFWorkbook();
		}

		XSSFSheet worksheet = workbook.getSheet("Sheet 1");
		if (worksheet == null) {
			worksheet = workbook.createSheet("Sheet 1");
			Row header = worksheet.createRow(0);
			Cell headercell1 = header.createCell(0);
			headercell1.setCellValue("State Name");
			Cell headercell2 = header.createCell(1);
			headercell2.setCellValue("District Name");
			Cell headercell3 = header.createCell(2);
			headercell3.setCellValue("Facility Name");
		}

		int rowNum = worksheet.getLastRowNum();

		for (int i = 1; i < facilityNames.length; i++) {
			Row row = worksheet.createRow(rowNum + i);

			Cell cell1 = row.createCell(0);
			Cell cell2 = row.createCell(1);
			Cell cell3 = row.createCell(2);
			cell1.setCellValue(statename);
			cell2.setCellValue(districtname);
			cell3.setCellValue(facilityNames[i - 1]);

		}

		FileOutputStream Fos = null;
		try {
			Fos = new FileOutputStream(excelfile);
			workbook.write(Fos);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			Fos.close();
		}
	}

}
