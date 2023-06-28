package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HyperLink {
	public static void addLink() throws IOException
	{
		// Create a Workbook
		XSSFWorkbook myWorkbook = new XSSFWorkbook();

		// Create a Spread Sheet
		XSSFSheet newSpreadsheet
			= myWorkbook.createSheet("Custom Links");
		XSSFCell cell;

		// Create Helpers
		CreationHelper helper
			= myWorkbook.getCreationHelper();
		XSSFCellStyle linkStyle
			= myWorkbook.createCellStyle();
		XSSFFont linkFont = myWorkbook.createFont();

		// Setting the Link Style
		linkFont.setUnderline(XSSFFont.U_SINGLE);
		//linkFont.setColor(HSSFColor.BLUE.index);
		//inkFont.setColor(HSSFColor.toHSSFColor(null));
		linkStyle.setFont(linkFont);

		// Adding a Link
		/*
		 * cell = newSpreadsheet.createRow(1).createCell( (short)2);
		 * cell.setCellValue("Link"); XSSFHyperlink link =
		 * (XSSFHyperlink)helper.createHyperlink( Hyperlink.LINK_URL);
		 * 
		 * link.setAddress("http://www.geeksforgeeks.com/");
		 * cell.setHyperlink((XSSFHyperlink)link); cell.setCellStyle(linkStyle);
		 */
		// Writing the File
		FileOutputStream output = new FileOutputStream(
			new File("C:/HyperLink.xlsx"));

		// Writing the content
		myWorkbook.write(output);
		output.close();
	}
	public static void main(String[] args) throws Exception
	{
		addLink();
	}
}
