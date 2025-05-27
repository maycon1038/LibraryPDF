package com.msm.pdf.model;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class ModeTablelPDF {
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
		return "ModeTablelPDF{" +
				"txt='" + txt + '\'' +
				", background=" + background +
				", font=" + font +
				", colSpan=" + colSpan +
				", rowSpan=" + rowSpan +
				", align=" + align +
				", underline=" + underline +
				", strike=" + strike +
				", myLink='" + myLink + '\'' +
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

	private String txt;
	private BaseColor background;
	private Font font;
	private int colSpan;
	private int rowSpan;
	private int align;
	public  boolean underline;
	public  boolean strike;
	public String myLink;

	public ModeTablelPDF(String txt, BaseColor background, Font font, int colSpan, int rowSpan, int align) {
		this.txt = txt;
		this.background = background;
		this.font = font;
		this.colSpan = colSpan;
		this.rowSpan = rowSpan;
		this.align = align;
	}


}
