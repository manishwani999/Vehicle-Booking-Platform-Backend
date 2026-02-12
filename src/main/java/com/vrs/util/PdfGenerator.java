package com.vrs.util;

import java.io.File;
import java.io.FileOutputStream;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class PdfGenerator {

    public static String generatePdfFromHtml(String html, String outputFilePath) {
        try {
            File out = new File(outputFilePath);
            out.getParentFile().mkdirs();
            try (FileOutputStream os = new FileOutputStream(out)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();
                builder.withHtmlContent(html, null);
                builder.toStream(os);
                builder.run();
            }
            return out.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException("Unable to generate PDF: " + e.getMessage(), e);
        }
    }
}
