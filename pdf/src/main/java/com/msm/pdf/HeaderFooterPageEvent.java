package com.msm.pdf;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.RequiresApi;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


    public class HeaderFooterPageEvent extends PdfPageEventHelper {

        private PdfTemplate t;
        private Image total;
        private Context context;

        public HeaderFooterPageEvent(Context context) {
            this.context = context;
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
            t = writer.getDirectContent().createTemplate(30, 16);
            try {
                total = Image.getInstance(t);
                total.setRole(PdfName.ARTIFACT);
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            addHeader(writer);
            addFooter(writer);
        }


        private void addHeader(PdfWriter writer){
            PdfPTable header = new PdfPTable(2);
            try {
                // set defaults
                header.setWidths(new int[]{2, 24});
                header.setTotalWidth(527);
                header.setLockedWidth(true);
                header.getDefaultCell().setFixedHeight(40);
                header.getDefaultCell().setBorder(Rectangle.BOTTOM);
                header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

                File dest = new File(context.getCacheDir(), "image_pmam.png");

                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.logo_pmam);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(dest);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                icon.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                // add image
                Image logo = null;
                try {
                    logo = Image.getInstance(dest.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                header.addCell(logo);

                // add text
                PdfPCell text = new PdfPCell();
                text.setPaddingBottom(15);
                text.setPaddingLeft(10);
                text.setBorder(Rectangle.BOTTOM);
                text.setBorderColor(BaseColor.LIGHT_GRAY);
                text.addElement(new Phrase("PMAM", new Font(Font.FontFamily.HELVETICA, 18)));
                text.addElement(new Phrase("Polícia Militar do Amazonas", new Font(Font.FontFamily.HELVETICA, 14)));
                header.addCell(text);

                // write content
                header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            } catch(DocumentException de) {
                throw new ExceptionConverter(de);
            } /*catch (MalformedURLException e) {
                throw new ExceptionConverter(e);
            } catch (IOException e) {
                throw new ExceptionConverter(e);
            }*/
        }

        private void addFooter(PdfWriter writer){
            PdfPTable footer = new PdfPTable(3);
            try {
                // set defaults
                footer.setWidths(new int[]{24, 2, 1});
                footer.setTotalWidth(527);
                footer.setLockedWidth(true);
                footer.getDefaultCell().setFixedHeight(40);
                footer.getDefaultCell().setBorder(Rectangle.TOP);
                footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
                df.format(new Date());


                // add copyright
                footer.addCell(new Phrase("\u00A9 " +getApplicationName() +" - " + df.format(new Date()), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

                // add current page count
                footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

                // add placeholder for total page count
                PdfPCell totalPageCount = new PdfPCell(total);
                totalPageCount.setBorder(Rectangle.TOP);
                totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
                footer.addCell(totalPageCount);

                // write page
                PdfContentByte canvas = writer.getDirectContent();
                canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
                footer.writeSelectedRows(0, -1, 34, 50, canvas);
                canvas.endMarkedContentSequence();
            } catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        public void onCloseDocument(PdfWriter writer, Document document) {
            int totalLength = String.valueOf(writer.getPageNumber()).length();
            int totalWidth = totalLength * 5;
            ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                    new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                    totalWidth, 6, 0);
        }

		private String getApplicationName() {
			ApplicationInfo applicationInfo = context.getApplicationInfo();
			int stringId = applicationInfo.labelRes;
			return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
		}
    }

