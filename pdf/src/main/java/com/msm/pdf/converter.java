package com.msm.pdf;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class converter {

    protected InputStream inStream;
    protected OutputStream outStream;

    public converter(InputStream inStream, OutputStream outStream) {
        this.inStream = inStream;
        this.outStream = outStream;

    }

    public abstract void convert();

}
