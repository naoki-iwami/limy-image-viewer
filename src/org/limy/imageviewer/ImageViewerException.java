package org.limy.imageviewer;

public class ImageViewerException extends Exception {

    public ImageViewerException() {
        super();
    }

    public ImageViewerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ImageViewerException(String detailMessage) {
        super(detailMessage);
    }

    public ImageViewerException(Throwable throwable) {
        super(throwable);
    }

}
