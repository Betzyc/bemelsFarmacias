package com.bemels.spring.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bemels.spring.app.entities.Empleado;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("empleados/detalle-empleado-form.pdf")
public class EmpleadoPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Empleado empleado = (Empleado) model.get("empleado");
		Font fuenteTituloPrincipal = FontFactory.getFont("Arial", 18, Color.WHITE);
		PdfPCell celda = null;
		
		PdfPTable tablaTitulo = new PdfPTable(1);
		celda = new PdfPCell(new Phrase("Datos Empleado", fuenteTituloPrincipal));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(31, 45, 65));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaTitulo.addCell(celda);
		tablaTitulo.setSpacingAfter(30);
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		tabla.addCell("Id: "+ empleado.getId());
		tabla.addCell("Dpi: " + empleado.getDpi());
		tabla.addCell("Nombre: " + empleado.getNombre());
		tabla.addCell("Apellido: " + empleado.getApellido());
		tabla.addCell("Teléfono: " + empleado.getTelefono());
		tabla.addCell("Fecha de nacimiento: " + empleado.getFechaNacimiento());
		tabla.addCell("Sueldo: Q" + empleado.getSueldo());
		tabla.addCell("Sucural: " + empleado.getSucursal().getNombre());
		
		document.add(tablaTitulo);
		document.add(tabla);
	}
}
