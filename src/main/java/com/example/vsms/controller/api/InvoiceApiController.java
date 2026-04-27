package com.example.vsms.controller.api;

import com.example.vsms.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceApiController {

    private final InvoiceService invoiceService;

    @GetMapping("/history/{historyId}/pdf")
    public ResponseEntity<byte[]> invoice(@PathVariable Long historyId) {
        byte[] pdf = invoiceService.generateInvoice(historyId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("service-invoice-" + historyId + ".pdf")
                                .build()
                                .toString())
                .body(pdf);
    }
}
