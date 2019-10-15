package com.msm.pdf;

import android.content.Context;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.gson.JsonObject;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class documentUtil {

    private static String TAG = "documentUtil";

    private static Font fBigTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private static Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font fBody = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private static Font fBody2 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    private static HashMap<String, documentUtil> instances = new HashMap<String, documentUtil>();
    private ArrayList<String> bitTitle = null, title = null, subTitle = null;
    private String assBody, subAssBody;
    private String infDate, body = null;

    public documentUtil setAssBody(String assBody, String subAssBody) {
        this.assBody = assBody;
        this.subAssBody = subAssBody;
        return getDefault(context);
    }

    public documentUtil setDate(double lat, double lng){
        Geocoder gcd = new Geocoder(context);
        List<Address> addresses = null;
        try {
            if(lat != 0 && lng != 0){

                addresses = gcd.getFromLocation(lat, lng, 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        if (addresses != null && addresses.size() > 0) {
          //  local = addresses.get(0).getAddressLine(0);
          //  Log.d(TAG, "Locale addresses1 "+ addresses.get(0).getSubLocality() + " addresses2 "+ addresses.get(0).getLocality());
         //   Log.d(TAG, "Locale addresses1 "+ addresses.get(0).getAdminArea() + " addresses2 "+ addresses.get(0).getSubAdminArea());
            infDate = ("Quartel em "+ addresses.get(0).getSubAdminArea() + ", " + df.format(new Date()));
        }else {
            infDate =   df.format(new Date());
        }

        return getDefault(context);
    }

    private static int numeColumns = 0;
    private static String titleTable = null;
    private static ArrayList<Integer> ElementAlign;
    private static ArrayList<Integer> configColspan;
    private static ArrayList<JsonObject> listBody;

    private static int numeColumns2 = 0;
    private static String titleTable2= null;
    private static ArrayList<Integer> ElementAlign2;
    private static ArrayList<Integer> configColspan2;
    private static ArrayList<JsonObject> listBody2;


    private String name;
    private Context context;
    private File FILE;
    private pdfCreate pdfCreated;

    private documentUtil(Context context, String name) {
        this.context = context;
        this.name = name;
    }

    public static documentUtil with(Context context) {
       numeColumns = 0;
        titleTable = null;
       ElementAlign = null;
        configColspan = null;
       listBody = null;

        numeColumns2 = 0;
        titleTable2 = null;
        ElementAlign2  = null;
        configColspan2 = null;
        listBody2 = null;

        return getDefault(context);
    }

    private static documentUtil getDefault(Context context) {
        return getInstance(context, "documentUtil");
    }

    private static documentUtil getInstance(Context context, String name) {
        if (context == null) {
            throw new NullPointerException("O Context não pode ser null");
        } else {
            documentUtil instance = instances.get(name);
            if (instance == null)
                instances.put(name, instance = new documentUtil(context, name));
            return instance;
        }
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public documentUtil setPage(ArrayList<String> bitTitle, ArrayList<String> title, ArrayList<String> subTitle, String body) {
        this.bitTitle = bitTitle;
        this.title = title;
        this.subTitle = subTitle;
        this.body = body;
        return getDefault(context);
    }

    public documentUtil setFileOut(File filepart, String fileName) {
        if (filepart != null && fileName != null) {
            File f = new File(filepart, fileName);
            if(f != null && f.exists()){
                f.delete();
            }
            FILE = new File(f.getPath());
            return getDefault(context);
        } else {
            throw new NullPointerException("File não pode ser null");
        }
    }

    public documentUtil setTable(String titleTable,int numeColumns, ArrayList<Integer> ElementAlign, ArrayList<Integer> configColspan, ArrayList<JsonObject> listBody) {

        if (ElementAlign != null && configColspan != null && listBody != null && ElementAlign.size() == configColspan.size() && configColspan.size() == listBody.get(0).getAsJsonObject().keySet().size()) {

            if (numeColumns < 1) {
                documentUtil.numeColumns = ElementAlign.size();
            } else {
                documentUtil.numeColumns = numeColumns;
            }
            documentUtil.titleTable = titleTable;
            documentUtil.ElementAlign = ElementAlign;
            documentUtil.configColspan = configColspan;
            documentUtil.listBody = listBody;
        }
        return getDefault(context);
    }




    public documentUtil setSecondTable(String titleTable,
                                    int numeColumns,
                                    ArrayList<Integer> ElementAlign,
                                    ArrayList<Integer> configColspan,
                                    ArrayList<JsonObject> listBody) {

        if (ElementAlign != null && configColspan != null && listBody != null && ElementAlign.size() == configColspan.size() &&
                configColspan.size() == listBody.get(0).getAsJsonObject().keySet().size()) {

            if (numeColumns < 1) {
                documentUtil.numeColumns2 = ElementAlign.size();
            } else {
                documentUtil.numeColumns2 = numeColumns;
            }
            documentUtil.titleTable2 = titleTable;

            documentUtil.ElementAlign2 = ElementAlign;
            documentUtil.configColspan2 = configColspan;
            documentUtil.listBody2 = listBody;
        }
        return getDefault(context);


    }


    public documentUtil setFontBigTitle(Font.FontFamily fontFamily, int size, int style, BaseColor color) {
        if (fontFamily != null && color != null) {
            documentUtil.fBigTitle = new Font(fontFamily, size, style, color);
            return getDefault(context);
        } else if (fontFamily != null) {
            documentUtil.fBigTitle = new Font(fontFamily, size, style);
            return getDefault(context);
        } else {
            throw new NullPointerException("A font Family não pode ser null");
        }
    }

    public documentUtil setFontTitle(Font.FontFamily fontFamily, int size, int style, BaseColor color) {
        if (fontFamily != null && color != null) {
            documentUtil.fTitle = new Font(fontFamily, size, style, color);
            return getDefault(context);
        } else if (fontFamily != null) {
            documentUtil.fTitle = new Font(fontFamily, size, style);
            return getDefault(context);
        } else {
            throw new NullPointerException("A font Family não pode ser null");
        }
    }

    public documentUtil setFontsubTitle(Font.FontFamily fontFamily, int size, int style, BaseColor color) {
        if (fontFamily != null && color != null) {
            documentUtil.fSubTitle = new Font(fontFamily, size, style, color);
            return getDefault(context);
        } else if (fontFamily != null) {
            documentUtil.fSubTitle = new Font(fontFamily, size, style);
            return getDefault(context);
        } else {
            throw new NullPointerException("A font Family não pode ser null");
        }
    }

    public documentUtil setFontBody(Font.FontFamily fontFamily, int size, int style, BaseColor color) {
        if (fontFamily != null && color != null) {
            documentUtil.fBody = new Font(fontFamily, size, style, color);
            return getDefault(context);
        } else if (fontFamily != null) {
            documentUtil.fBody = new Font(fontFamily, size, style);
            return getDefault(context);
        } else {
            throw new NullPointerException("A font Family não pode ser null");
        }
    }

    private void addPage(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        // We add one empty line

        addEmptyLine(preface, 1);

        if (bitTitle != null) {
            for (String txt : bitTitle) {
                Paragraph paragraph = new Paragraph(txt, fBigTitle);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                preface.add(paragraph);
            }
        }

        if (title != null) {
            for (String txt : title) {
                Paragraph paragraph = new Paragraph(txt, fTitle);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                preface.add(paragraph);
            }

        }

        if (subTitle != null) {
            for (String txt : subTitle) {
                Paragraph paragraph = new Paragraph(txt, fSubTitle);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                preface.add(paragraph);
            }

        }

        addEmptyLine(preface, 2);

        if (body != null) {

                Paragraph paragraph = new Paragraph(body, fBody);
                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                preface.add(paragraph);


        }




     //  addEmptyLine(preface, 3);

        document.add(preface);

    }


    private void addDate(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        // We add one empty line

        addEmptyLine(preface, 3);

        if (infDate != null) {

                Paragraph paragraph = new Paragraph(infDate, fBody);
                paragraph.setAlignment(Element.ALIGN_RIGHT);
                preface.add(paragraph);

        }

        addEmptyLine(preface, 1);


        document.add(preface);

    }
    private void addAss(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        // We add one empty line

        addEmptyLine(preface, 3);

        if (assBody != null) {

            Paragraph paragraph = new Paragraph(assBody, fBody);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);

        }
        if (subAssBody != null) {

            Paragraph paragraph = new Paragraph(subAssBody, fBody);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);

        }

        addEmptyLine(preface, 1);


        document.add(preface);

    }




    private void addtable(Document document)
            throws BadElementException {

        // We add one empty line

        Paragraph preface = new Paragraph();

        addEmptyLine(preface, 1);

        if (ElementAlign != null && configColspan != null && listBody != null && ElementAlign.size() == configColspan.size() &&
                configColspan.size() == listBody.get(0).getAsJsonObject().keySet().size()) {
            ArrayList<String> elem = new ArrayList<>(listBody.get(0).getAsJsonObject().keySet());
                PdfPTable table = new PdfPTable(numeColumns);


                if(titleTable != null){
                    PdfPCell c1 = new PdfPCell(new Phrase(titleTable,fBody));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setColspan(numeColumns);
                    table.addCell(c1);
                }

                //populando o cabeçalho da tabela
                for (int i = 0; i < elem.size(); i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(elem.get(i),fBody));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setColspan(configColspan.get(i));
                    table.addCell(c1);
                }

                table.setHeaderRows(1);

                for (JsonObject obj : listBody) {

                    //populando a tabela
                    for (int i = 0; i < elem.size(); i++) {
                        PdfPCell c1 = new PdfPCell(new Phrase(obj.get(elem.get(i)).getAsString(),fBody2));
                        c1.setHorizontalAlignment(ElementAlign.get(i));
                        c1.setColspan(configColspan.get(i));
                        table.addCell(c1);
                    }

                }

                 preface.add(table);
                addEmptyLine(preface, 1);
                try {
                    document.add(preface);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


        }

    }

    private void addSecundtable(Document document)
            throws BadElementException {

        // We add one empty line

        Paragraph preface = new Paragraph();

            addEmptyLine(preface, 1);

            if (ElementAlign2 != null && configColspan2 != null && listBody2 != null && ElementAlign2.size() == configColspan2.size()
                    && configColspan2.size() == listBody2.get(0).getAsJsonObject().keySet().size()) {
                ArrayList<String> elem2 = new ArrayList<>(listBody2.get(0).getAsJsonObject().keySet());


                PdfPTable table2 = new PdfPTable(numeColumns2);
                if(titleTable2 != null){
                    PdfPCell c1 = new PdfPCell(new Phrase(titleTable2,fBody));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setColspan(numeColumns2);
                    table2.addCell(c1);
                }

                //populando o cabeçalho da tabela
                for (int i = 0; i < elem2.size(); i++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(elem2.get(i),fBody));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    c1.setColspan(configColspan2.get(i));
                    table2.addCell(c1);
                    Log.d("TESTESIGLA4 ", " POSto2 " +  elem2.get(i));
                }

                table2.setHeaderRows(1);

                for (JsonObject obj : listBody2) {

                    //populando a tabela
                    for (int i = 0; i < elem2.size(); i++) {
                        PdfPCell c1 = new PdfPCell(new Phrase(obj.get(elem2.get(i)).getAsString(),fBody2));
                        c1.setHorizontalAlignment(ElementAlign2.get(i));
                        c1.setColspan(configColspan2.get(i));
                        table2.addCell(c1);

                    }

                }

                preface.add(table2);
                addEmptyLine(preface, 1);
                try {
                    document.add(preface);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }



            }


    }

    public documentUtil start(pdfCreate callback) {
        documentUtil ini = getDefault(context);
        ini.setCallback(callback);
        return ini;
    }

    private void setCallback(pdfCreate pdf) {
        pdfCreated = pdf;
        if (pdfCreated != null) {
            pdfCreated.filePagePdf(getFilePdf());
        }

    }

    private File getFilePdf() {
        // create document

        Document document = new Document(PageSize.A4, 20, 20, 110, 40);
        PdfWriter writer = null;


        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
            // add header and footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent(context);
            writer.setPageEvent(event);

            document.open();
           // addMetaData(document);
            addPage(document);
            if (numeColumns >= 1) {
                 addtable(document);
            }
            if (numeColumns2 >= 1) {
                addSecundtable(document);
            }
            addDate(document);
            addAss(document);
            // Start a new page
         //   document.newPage();
            document.close();
            return FILE;

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;


    }

    private  void addMetaData(Document document) {
        document.addTitle("PMAM");
        document.addSubject("Escala de Serviço");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("AppComando");
        document.addCreator("AppComando");
    }
}
