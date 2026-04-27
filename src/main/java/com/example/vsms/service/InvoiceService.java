package com.example.vsms.service;

import com.example.vsms.entity.ServiceHistory;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ServiceHistoryService historyService;

    public byte[] generateInvoice(Long historyId) {
        ServiceHistory history = historyService.findEntity(historyId);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font headingFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            document.add(new Paragraph("Vehicle Service Invoice", titleFont));
            document.add(new Paragraph("Invoice Date: " + LocalDate.now()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            addRow(table, "Customer", history.getVehicle().getCustomer().getName(), headingFont);
            addRow(table, "Vehicle", history.getVehicle().getBrand() + " " + history.getVehicle().getModel(), headingFont);
            addRow(table, "Registration", history.getVehicle().getRegistrationNumber(), headingFont);
            addRow(table, "Service Date", history.getServiceDate().toString(), headingFont);
            addRow(table, "Description", history.getDescription(), headingFont);
            addRow(table, "Total Cost", history.getCost().toString(), headingFont);
            document.add(table);

            document.close();
            return outputStream.toByteArray();
        } catch (DocumentException | java.io.IOException ex) {
            throw new IllegalStateException("Unable to generate invoice", ex);
        }
    }

    private void addRow(PdfPTable table, String label, String value, Font headingFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, headingFont));
        labelCell.setBackgroundColor(new Color(238, 242, 247));
        table.addCell(labelCell);
        table.addCell(value == null ? "" : value);
    }
}
