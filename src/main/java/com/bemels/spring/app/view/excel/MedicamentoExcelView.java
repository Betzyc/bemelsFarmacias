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

import com.bemels.spring.app.entities.Producto;

@Component("medicamentos/detalle-medicamento-form.xlsx")
public class MedicamentoExcelView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			response.setHeader("Content-Disposition", "attachment; filename=\"reporte_producto.xlsx\"");
			Producto producto = (Producto) model.get("producto");
			Sheet sheet = workbook.createSheet("Producto");
			
			CellStyle header = workbook.createCellStyle();
			header.setBorderBottom(BorderStyle.MEDIUM);
			header.setBorderTop(BorderStyle.MEDIUM);
			header.setBorderRight(BorderStyle.MEDIUM);
			header.setBorderLeft(BorderStyle.MEDIUM);
			header.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
			header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			Row row = sheet.createRow(1);
			Cell cell = row.createCell(1);
			cell.setCellValue("Datos del Medicamento");
			cell.setCellStyle(header);
			
			row = sheet.createRow(3);
			cell = row.createCell(1);
			cell.setCellValue("ID:");
			cell = row.createCell(2);
			cell.setCellValue(producto.getId());
			
			row = sheet.createRow(4);
			cell = row.createCell(1);
			cell.setCellValue("CÓDIGO DE BARRAS:");
			cell = row.createCell(2);
			cell.setCellValue(producto.getCodigobarras());
			
			row = sheet.createRow(5);
			cell = row.createCell(1);
			cell.setCellValue("NOMBRE:");
			cell = row.createCell(2);
			cell.setCellValue(producto.getNombre());
			
			/*row = sheet.createRow(6);
			cell = row.createCell(1);
			cell.setCellValue("LABORATORIO:");
			cell = row.createCell(2);
			cell.setCellValue(producto.getLaboratorio().getNombre());*/
			
			row = sheet.createRow(6);
			cell = row.createCell(1);
			cell.setCellValue("PRECIO COSTO:");
			cell = row.createCell(2);
			cell.setCellValue("Q"+producto.getCosto());
			
			row = sheet.createRow(7);
			cell = row.createCell(1);
			cell.setCellValue("PRECIO VENTA:");
			cell = row.createCell(2);
			cell.setCellValue("Q"+producto.getPrecio());
			
			row = sheet.createRow(8);
			cell = row.createCell(1);
			cell.setCellValue("STOCK MÍNIMO:");
			cell = row.createCell(2);
			cell.setCellValue(producto.getMinimo());
			
			row = sheet.createRow(9);
			cell = row.createCell(1);
			cell.setCellValue("STOCK MÁXIMO:");
			cell = row.createCell(2);
			cell.setCellValue(producto.getMaximo());
			
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
