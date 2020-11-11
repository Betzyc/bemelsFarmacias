package com.bemels.spring.app.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bemels.spring.app.entities.Proveedor;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("proveedores/detalle-proveedor-form.pdf")
public class ProveedorPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Proveedor proveedor = (Proveedor) model.get("proveedor");
		Font fuenteTituloPrincipal = FontFactory.getFont("Arial", 18, Color.WHITE);
		PdfPCell celda = null;
		
		PdfPTable tablaTitulo = new PdfPTable(1);
		celda = new PdfPCell(new Phrase("Datos Proveedor", fuenteTituloPrincipal));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(31, 45, 65));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaTitulo.addCell(celda);
		tablaTitulo.setSpacingAfter(30);
		
		PdfPTable tabla = new PdfPTable(1);
		tabla.setSpacingAfter(20);
		
		tabla.addCell("Id: "+ proveedor.getId());
		tabla.addCell("Nombre: " + proveedor.getNombre());
		tabla.addCell("Nit: " + proveedor.getNit());
		tabla.addCell("Dirección: " + proveedor.getDireccion());
		tabla.addCell("Teléfono: " + proveedor.getTelefono());
		tabla.addCell("Email: " + proveedor.getEmail());
		tabla.addCell("Departamento: " + proveedor.getDepartamento().getNombre());
		
		document.add(tablaTitulo);
		document.add(tabla);
	}
}
