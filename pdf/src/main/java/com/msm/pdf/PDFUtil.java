package com.msm.pdf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.msm.pdf.model.ModeTablelPDF;
import com.msm.pdf.model.ModelPDF;

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

public class PDFUtil {


	public static Font fBigTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	public static Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	public static Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	public static Font fBody = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
	public static Font fBody2 = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.NORMAL);
	public static Font fBodyPhone = new Font(Font.FontFamily.COURIER, 8, Font.BOLD, new BaseColor(0, 128, 255));
	public static Font fBodyEmail = new Font(Font.FontFamily.COURIER, 8, Font.ITALIC, new BaseColor(0, 128, 255));
	public static Font fBodyMap = new Font(Font.FontFamily.COURIER, 8, Font.NORMAL, new BaseColor(0, 128, 255));
	private static HashMap<String, PDFUtil> instances = new HashMap<String, PDFUtil>();
	private String name;
	private Context context;
	private File FILE;
	private pdfCreate pdfCreated;
	private Document document;
	private Paragraph preface;
	/**
	 * margin in x direction starting from the left
	 */
	private int marginLeft = 50;

	/**
	 * margin in x direction starting from the right
	 */
	private int marginRight = 50;

	/**
	 * margin in y direction starting from the top
	 */
	private int marginTop = 110;

	/**
	 * margin in y direction starting from the bottom
	 */
	private int marginBottom = 50;

	private PDFUtil(Context context, String name) {
		this.context = context;
		this.name = name;
	}

	public static PDFUtil with(Context context) {
		return getDefault(context);
	}

	private static PDFUtil getDefault(Context context) {
		return getInstance(context, "myPersonDocumentPDF");
	}

	private static PDFUtil getInstance(Context context, String name) {
		if (context == null) {
			throw new NullPointerException("O Context não pode ser null");
		} else {
			PDFUtil instance = instances.get(name);
			if (instance == null)
				instances.put(name, instance = new PDFUtil(context, name));
			return instance;
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public String getDate(double lat, double lng) {
		Geocoder gcd = new Geocoder(context);
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		List<Address> addresses = null;
		String infDate;
		try {
			if (lat != 0 && lng != 0) {

				addresses = gcd.getFromLocation(lat, lng, 1);
			}
			if (addresses != null && addresses.size() > 0) {
				//  local = addresses.get(0).getAddressLine(0);
				//  Log.d(TAG, "Locale addresses1 "+ addresses.get(0).getSubLocality() + " addresses2 "+ addresses.get(0).getLocality());
				//   Log.d(TAG, "Locale addresses1 "+ addresses.get(0).getAdminArea() + " addresses2 "+ addresses.get(0).getSubAdminArea());
				infDate = ("Quartel em " + addresses.get(0).getSubAdminArea() + ", " + df.format(new Date()));
			} else {
				infDate = df.format(new Date());
			}
		} catch (IOException e) {
			e.printStackTrace();
			infDate = df.format(new Date());
		}
		return infDate;
	}

	public PDFUtil setFileOut(File filepart, String fileName) {
		if (filepart != null && fileName != null) {
			File f = new File(filepart, fileName);
			f.deleteOnExit();
			FILE = new File(f.getPath());
			//configuração do documento margem etc
			document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
			PdfWriter writer = null;
			try {
				writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
				// adicionar cabeçalho e rodapé
				HeaderFooterPageEvent event = new HeaderFooterPageEvent(context);
				writer.setPageEvent(event);
				document.open();
				// adicionado titulo ao documento
			} catch (DocumentException | FileNotFoundException e) {
				e.printStackTrace();
			}
			return getDefault(context);
		} else {
			throw new NullPointerException("File não pode ser null");
		}
	}

	public PDFUtil setFileOut(File filepart, String fileName, int marginLeft, int marginRight, int marginTop, int marginBottom) {
		if (filepart != null && fileName != null) {
			File f = new File(filepart, fileName);
			f.deleteOnExit();
			FILE = new File(f.getPath());
			//configuração do documento margem etc
			document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
			PdfWriter writer = null;
			try {
				writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
				// adicionar cabeçalho e rodapé
				HeaderFooterPageEvent event = new HeaderFooterPageEvent(context);
				writer.setPageEvent(event);
				document.open();
				// adicionado titulo ao documento
			} catch (DocumentException | FileNotFoundException e) {
				e.printStackTrace();
			}
			return getDefault(context);
		} else {
			throw new NullPointerException("File não pode ser null");
		}
	}

	public PDFUtil setFileOut(File filepart, File imgLogo, ModelPDF titleHeader, ModelPDF subTitleHeader, String fileName, int marginLeft, int marginRight, int marginTop, int marginBottom) {
		if (filepart != null && fileName != null) {
			File f = new File(filepart, fileName);
			f.deleteOnExit();
			FILE = new File(f.getPath());
			//configuração do documento margem etc
			document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
			PdfWriter writer = null;
			try {
				writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
				HeaderFooterPageEvent event;
				// adicionar cabeçalho e rodapé
				if (imgLogo != null && titleHeader != null && subTitleHeader != null) {
					event = new HeaderFooterPageEvent(context, titleHeader, subTitleHeader, imgLogo);
				} else {
					event = new HeaderFooterPageEvent(context);
				}
				writer.setPageEvent(event);
				document.open();
			} catch (DocumentException | FileNotFoundException e) {
				e.printStackTrace();
			}
			return getDefault(context);
		} else {
			throw new NullPointerException("File não pode ser null");
		}
	}

	public void addPage(ArrayList<ModelPDF> modepdf, ModelPDF body) throws DocumentException {

		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);

		if (modepdf != null && modepdf.size() > 0) {
			for (int i = 0; i < modepdf.size(); i++) {
				//grifando o texto
				ModelPDF mpdf = modepdf.get(i);
				addTextinParagraph(mpdf, preface);
			}
		}


		addEmptyLine(preface, 2);

		addTextinParagraph(body, preface);
		//  addEmptyLine(preface, 3);

		document.add(preface);

	}

	public void addData(ModelPDF body) throws DocumentException {

		Paragraph preface = new Paragraph();
		// We add one empty line

		addEmptyLine(preface, 3);

		addTextinParagraph(body, preface);

		addEmptyLine(preface, 1);


		document.add(preface);

	}

	public void addAssinatura(ModelPDF body) throws DocumentException {

		Paragraph preface = new Paragraph();
		// Nós adicionamos 3 linha vazia
		addEmptyLine(preface, 3);
		addTextinParagraph(body, preface);
// Nós adicionamos 1 linha vazia
		addEmptyLine(preface, 1);
		document.add(preface);

	}

	private void addTextinParagraph(ModelPDF body, Paragraph preface) {
		if (body != null && body.getTxt() != null) {
			Font f = (body.getFont() != null) ? body.getFont() : new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
			Paragraph paragraph = new Paragraph();
			Chunk chunk = new Chunk(body.getTxt(), f);
			if (body.getBackground() != null) {
				chunk.setBackground(body.getBackground());
			}
			//grifando o texto
			if (body.underline) {
				chunk.setUnderline(0.1f, -1f);
			} else if (body.strike) {
				chunk.setUnderline(1f, f.getSize() / 2);
			} else if (body.assinar) {
				////cria um golpe através do pedaço com 1 espessura
				chunk.setUnderline(1f, f.getSize());

			}
			paragraph.add(chunk);
			paragraph.setAlignment(body.getAlign());
			preface.add(paragraph);
		}
	}

	@SuppressLint("DefaultLocale")
	public void addtable(ArrayList<ModeTablelPDF> mtable, int numeColumns) throws DocumentException {
		PdfPTable table = new PdfPTable(numeColumns);
		// We add one empty line
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);

		// We add one empty line
		if (mtable != null && mtable.size() > 0) {
			for (int i = 0; i < mtable.size(); i++) {

				ModeTablelPDF body = mtable.get(i);
				if (body != null && body.getTxt() != null) {
					Font f = (body.getFont() != null) ? body.getFont() : new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
					Phrase phrase = new Phrase();
					Chunk chunk = new Chunk(body.getTxt(), f);
					//grifando o texto
					if (body.underline) {
						chunk.setUnderline(0.1f, -1f);
					} else if (body.strike) {
						chunk.setUnderline(1f, f.getSize() / 2);
					}
					phrase.add(chunk);

					PdfPCell c1 = new PdfPCell(phrase);
					c1.setHorizontalAlignment(body.getAlign());
					c1.setVerticalAlignment(body.getAlign());
					c1.setColspan(body.getColSpan());
					c1.setRowspan(body.getRowSpan());
					if (body.getBackground() != null) {
						c1.setBackgroundColor(body.getBackground());
					}
					if (body.getMyLink() != null) {
						c1.setCellEvent(new LinkInCell(body.getMyLink()));
					}
					table.addCell(c1);
				}
			}

		}
		preface.add(table);
		document.add(preface);
	}

	public PDFUtil start(pdfCreate callback) {
		PDFUtil ini = getDefault(context);
		ini.setCallback(callback);
		return ini;
	}

	private void setCallback(pdfCreate pdf) {
		pdfCreated = pdf;
		if (pdfCreated != null) {
			if (document != null) {
				document.close();
			}
			pdfCreated.filePagePdf(getFilePdf());

		}

	}

	//definindo as principais configurações do pdf


	//definindo as principais configurações do pdf
	private File getFilePdf() {
		// create document
		if (document != null && preface != null) {
			try {
				document.add(preface);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			document.close();
		}
		return FILE;

	}

	private void addMetaData(Document document, String title, String subject, String keyWords, String author, String creator) {
		document.addTitle(title); // PMAM
		document.addSubject(subject); //
		document.addKeywords(keyWords); //"Java, PDF, iText"
		document.addAuthor(author); //
		document.addCreator(creator); //
	}

	class LinkInCell implements PdfPCellEvent {
		protected String url;

		public LinkInCell(String url) {
			this.url = url;
		}

		public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases) {
			PdfWriter writer = canvases[0].getPdfWriter();
			PdfAction action = new PdfAction(url);
			PdfAnnotation link = PdfAnnotation.createLink(writer, position, PdfAnnotation.HIGHLIGHT_INVERT, action);
			writer.addAnnotation(link);
		}
	}
}
