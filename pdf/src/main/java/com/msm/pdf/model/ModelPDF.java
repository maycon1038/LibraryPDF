package com.msm.pdf.model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

import lombok.Data;

@Data
public class ModelPDF {
	private String txt;
	private BaseColor background;
	private Font font;
	private int colSpan;
	private int rowSpan;
	private int align;
	public  boolean underline;
	public  boolean strike;
	public String myLink;
	public  boolean assinar;

	public ModelPDF(String txt, BaseColor background, Font font,   int align) {
		this.txt = txt;
		this.background = background;
		this.font = font;
		this.align = align;
	}


}
