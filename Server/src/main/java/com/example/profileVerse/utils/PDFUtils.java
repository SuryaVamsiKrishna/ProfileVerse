package com.example.profileVerse.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
// import org.apache.pdfbox.pdmodel.common.PDRectangle;
// import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
// import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

public class PDFUtils {
    public static String extractTextFromPDF(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting text from PDF", e);
        }
    }

    public static List<MultipartFile> convertBase64ToMultipartFile(List<String> base64List) {
        List<MultipartFile> files = new ArrayList<>();
        base64List.forEach(base64 -> {
            try {
                byte[] fileBytes = Base64.getDecoder().decode(base64);
                MultipartFile multipartFile = new MockMultipartFile(
                        "file",
                        "resume.pdf", // You can customize this filename as needed
                        "application/pdf",
                        fileBytes);
                files.add(multipartFile);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 content", e);
            }
        });
        return files;
    }

    public static String extractGitHubLink(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            for (PDPage page : document.getPages()) {
                List<PDAnnotation> annotations = page.getAnnotations(); // Get all annotations
                for (PDAnnotation annotation : annotations) {
                    if (annotation instanceof PDAnnotationLink) { // Check if the annotation is a link
                        PDAnnotationLink link = (PDAnnotationLink) annotation;
                        if (link.getAction() instanceof PDActionURI) {
                            PDActionURI uriAction = (PDActionURI) link.getAction();
                            String url = uriAction.getURI();
                            if (url != null && url.contains("github.com")) {
                                return url; // Return the first GitHub link found
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error extracting GitHub link from PDF", e);
        }
        return null; // Return null if no GitHub link is found
    }

}
