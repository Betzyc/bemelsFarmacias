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

import com.bemels.spring.app.entities.Activo;

@Component("activos/detalle-activo-form.xlsx")
public class ActivoExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"reporte_activo.xlsx\"");
			Activo activo = (Activo) model.get("activo");
			Sheet sheet = workbook.createSheet("Activo");
			
			CellStyle header = workbook.createCellStyle();
			header.setBorderBottom(BorderStyle.MEDIUM);
			header.setBorderTop(BorderStyle.MEDIUM);
			header.setBorderRight(BorderStyle.MEDIUM);
			header.setBorderLeft(BorderStyle.MEDIUM);
			header.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(1);
			cell.setCellValue("Datos del Activo");
			cell.setCellStyle(header);
			
			row = sheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("ID:");
			cell = row.createCell(2);
			cell.setCellValue(activo.getId());
			
			row = sheet.createRow(4);
			cell = row.createCell(1);
			cell.setCellValue("CANTIDAD:");
			cell = row.createCell(2);
			cell.setCellValue(activo.getCantidad());
			
			row = sheet.createRow(5);
			cell = row.createCell(1);
			cell.setCellValue("NOMBRE:");
			cell = row.createCell(2);
			cell.setCellValue(activo.getNombre());
			
			row = sheet.createRow(6);
			cell = row.createCell(1);
			cell.setCellValue("PRECIO UNITARIO:");
			cell = row.createCell(2);
			cell.setCellValue("Q"+activo.getUnitario());
			
			row = sheet.createRow(7);
			cell = row.createCell(1);
			cell.setCellValue("SUCURSAL:");
			cell = row.createCell(2);
			cell.setCellValue(activo.getSucursal().getNombre());
			
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
