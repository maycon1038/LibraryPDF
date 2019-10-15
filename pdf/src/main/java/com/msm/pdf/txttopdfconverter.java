package com.msm.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class txttopdfconverter extends converter {

    public txttopdfconverter(InputStream inStream, OutputStream outStream) {
        super(inStream, outStream);
    }

    @Override
    public void convert() {
        DataInputStream in = null;
        BufferedReader br = null;
        InputStreamReader is = null;
        Document pdfdoc = new Document();
        try {
            PdfWriter.getInstance(pdfdoc, outStream);
            pdfdoc.open();
            pdfdoc.setMarginMirroring(true);
            pdfdoc.setMargins(36, 72, 108, 180);
            pdfdoc.topMargin();
            Font normal_font = new Font();
            Font bold_font = new Font();
            bold_font.setStyle(Font.BOLD);
            bold_font.setSize(12);
            normal_font.setStyle(Font.NORMAL);
            normal_font.setSize(12);
            pdfdoc.add(new Paragraph("\n"));
            in = new DataInputStream(inStream);
            is = new InputStreamReader(in);
            br = new BufferedReader(is);
            String strLine;
            while ((strLine = br.readLine()) != null) {
                System.out.println("strLine = " + strLine);
                Paragraph para = new Paragraph(strLine + "\n", normal_font);
                para.setAlignment(Element.ALIGN_JUSTIFIED);
                pdfdoc.add(para);
            }
            pdfdoc.close();
            in.close();
            br.close();
            is.close();
        } catch (DocumentException ex) {
            Logger.getLogger(txttopdfconverter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(txttopdfconverter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}