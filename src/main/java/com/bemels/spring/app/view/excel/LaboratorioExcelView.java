package com.bemels.spring.app.view.excel;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bemels.spring.app.entities.Laboratorio;

@Component("laboratorios/detalle-laboratorio-form.xlsx")
public class LaboratorioExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"reporte_laboratorio.xlsx\"");
			Laboratorio laboratorio = (Laboratorio) model.get("laboratorio");
			Sheet sheet = workbook.createSheet("Laboratorio");
			
			CellStyle header = workbook.createCellStyle();
			header.setBorderBottom(BorderStyle.MEDIUM);
			header.setBorderTop(BorderStyle.MEDIUM);
			header.setBorderRight(BorderStyle.MEDIUM);
			header.setBorderLeft(BorderStyle.MEDIUM);
			header.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(1);
			cell.setCellValue("Datos del Laboratorio");
			cell.setCellStyle(header);
			
			row = sheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("ID:");
			cell = row.createCell(2);
			cell.setCellValue(laboratorio.getId());
			
			row = sheet.createRow(4);
			cell = row.createCell(1);
			cell.setCellValue("CANTIDAD:");
			cell = row.createCell(2);
			cell.setCellValue(laboratorio.getNombre());
		
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
