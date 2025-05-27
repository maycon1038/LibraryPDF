package com.msm.pdf.model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class ModelPDF {
	private String txt;

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public BaseColor getBackground() {
		return background;
	}

	@Override
	public String toString() {
		return "ModelPDF{" +
				"txt='" + txt + '\'' +
				", background=" + background +
				", font=" + font +
				", colSpan=" + colSpan +
				", rowSpan=" + rowSpan +
				", align=" + align +
				", underline=" + underline +
				", strike=" + strike +
				", myLink='" + myLink + '\'' +
				", assinar=" + assinar +
				'}';
	}

	public void setBackground(BaseColor background) {
		this.background = background;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isStrike() {
		return strike;
	}

	public void setStrike(boolean strike) {
		this.strike = strike;
	}

	public String getMyLink() {
		return myLink;
	}

	public void setMyLink(String myLink) {
		this.myLink = myLink;
	}

	public boolean isAssinar() {
		return assinar;
	}

	public void setAssinar(boolean assinar) {
		this.assinar = assinar;
	}

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
