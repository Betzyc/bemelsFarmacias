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

import com.bemels.spring.app.entities.Proveedor;

@Component("proveedores/detalle-proveedor-form.xlsx")
public class ProveedorExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"reporte_proveedor.xlsx\"");
			Proveedor proveedor = (Proveedor) model.get("proveedor");
			Sheet sheet = workbook.createSheet("Proveedor");
			
			CellStyle header = workbook.createCellStyle();
			header.setBorderBottom(BorderStyle.MEDIUM);
			header.setBorderTop(BorderStyle.MEDIUM);
			header.setBorderRight(BorderStyle.MEDIUM);
			header.setBorderLeft(BorderStyle.MEDIUM);
			header.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(1);
			cell.setCellValue("Datos del Proveedor");
			cell.setCellStyle(header);
			
			row = sheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("ID:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getId());
			
			row = sheet.createRow(4);
			cell = row.createCell(1);
			cell.setCellValue("NOMBRE:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getNombre());
			
			row = sheet.createRow(5);
			cell = row.createCell(1);
			cell.setCellValue("NIT:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getNit());
			
			row = sheet.createRow(6);
			cell = row.createCell(1);
			cell.setCellValue("DIRECCIÓN:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getDireccion());
			
			row = sheet.createRow(7);
			cell = row.createCell(1);
			cell.setCellValue("TELÉFONO:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getTelefono());
			
			row = sheet.createRow(8);
			cell = row.createCell(1);
			cell.setCellValue("EMAIL:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getEmail().toString());
			
			row = sheet.createRow(9);
			cell = row.createCell(1);
			cell.setCellValue("DEPARTAMENTO:");
			cell = row.createCell(2);
			cell.setCellValue(proveedor.getDepartamento().getNombre());
			
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
