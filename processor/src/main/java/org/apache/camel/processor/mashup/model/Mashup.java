package org.apache.camel.processor.mashup.model;

import org.apache.commons.digester3.Digester;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class Mashup {
    
    private String id;
    private Cookie cookie;
    private List<Page> pages = new LinkedList<Page>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
    
    public void addPage(Page page) {
        this.pages.add(page);
    }

    public void digeste(InputStream inputStream) throws Exception {
        Digester digester = new Digester();

        digester.push(this);

        digester.addSetProperties("mashup");
        
        digester.addObjectCreate("mashup/cookie", Cookie.class);
        digester.addSetProperties("mashup/cookie");
        digester.addSetNext("mashup/cookie", "setCookie");
        
        digester.addObjectCreate("mashup/page", Page.class);
        digester.addSetProperties("mashup/page");
        digester.addSetNext("mashup/page", "addPage");
        
        digester.addObjectCreate("mashup/page/extractor", Extractor.class);
        digester.addSetProperties("mashup/page/extractor");
        digester.addSetNext("mashup/page/extractor", "addExtractor");
        
        digester.addObjectCreate("mashup/page/extractor/property", Property.class);
        digester.addSetProperties("mashup/page/extractor/property");
        digester.addCallMethod("mashup/page/extractor/property", "setValue", 0);
        digester.addSetNext("mashup/page/extractor/property", "addProperty");
        
        digester.addObjectCreate("mashup/page/errorhandler", ErrorHandler.class);
        digester.addSetProperties("mashup/page/errorhandler");
        digester.addSetNext("mashup/page/errorhandler", "setErrorHandler");

        digester.addObjectCreate("mashup/page/errorhandler/extractor", Extractor.class);
        digester.addSetProperties("mashup/page/errorhandler/extractor");
        digester.addSetNext("mashup/page/errorhandler/extractor", "addExtractor");

        digester.addObjectCreate("mashup/page/errorhandler/extractor/property", Property.class);
        digester.addSetProperties("mashup/page/errorhandler/extractor/property");
        digester.addSetNext("mashup/page/errorhandler/extractor/property", "addProperty");

        digester.parse(inputStream);
    }

}
