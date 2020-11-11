package com.bemels.spring.app.view.pdf;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bemels.spring.app.entities.Proveedor;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("proveedores/proveedores.pdf")
public class ProveedoresPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		@SuppressWarnings("unchecked")
		Page<Proveedor> proveedores = (Page<Proveedor>) model.get("proveedores");
		List<Proveedor> proveedorcito = proveedores.getContent();
		
		Font fuenteTituloPrincipal = FontFactory.getFont("Arial", 18, Color.WHITE);
		Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD ,12, new Color(31, 45, 65));
		Font fuenteCeldas = FontFactory.getFont(FontFactory.COURIER ,10,Color.BLACK);
		PdfPCell celda = null;
		
		document.setPageSize(PageSize.LETTER.rotate());
		document.setMargins(-20, -20, 40, 20);
		document.open();
		
		PdfPTable tablaTitulo = new PdfPTable(1);
		
		celda = new PdfPCell(new Phrase("Listado Proveedores", fuenteTituloPrincipal));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(31, 45, 65));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		
		tablaTitulo.addCell(celda);
		tablaTitulo.setSpacingAfter(30);
		
		PdfPTable tablaEmpleados = new PdfPTable(7);
		tablaEmpleados.setWidths(new float[] {0.60f, 1.1f, .9f, 2f, 1.3f, 1.9f, 1.8f});		
		
		celda = new PdfPCell(new Phrase("ID", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NOMBRE", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		celda = new PdfPCell(new Phrase("NIT", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		celda = new PdfPCell(new Phrase("DIRECCIÓN", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TELÉFONO", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		celda = new PdfPCell(new Phrase("EMAIL", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		celda = new PdfPCell(new Phrase("DEPARTAMENTO", fuenteTituloColumnas));
		celda.setBackgroundColor(Color.LIGHT_GRAY);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaEmpleados.addCell(celda);
		
		
		for (Proveedor proveedor : proveedorcito) {
			celda = new PdfPCell(new Phrase(proveedor.getId().toString(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);
			
			celda = new PdfPCell(new Phrase(proveedor.getNombre(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);
			
			celda = new PdfPCell(new Phrase(proveedor.getNit(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);
			
			celda = new PdfPCell(new Phrase(proveedor.getDireccion(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);
			
			celda = new PdfPCell(new Phrase(proveedor.getTelefono(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);
			
			celda = new PdfPCell(new Phrase(proveedor.getEmail(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);
			
			celda = new PdfPCell(new Phrase(proveedor.getDepartamento().getNombre(), fuenteCeldas));
			celda.setPadding(5);
			tablaEmpleados.addCell(celda);		
		}
	
		document.add(tablaTitulo);
		document.add(tablaEmpleados);
	}

}
