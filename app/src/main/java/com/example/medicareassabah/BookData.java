package com.example.medicareassabah;

public class BookData {

    private String id,duser,puser,pphone,page,pdisease,bookdate,booktime ,key;


    public BookData(String id, String duser,String puser, String pphone, String page, String pdisease, String bookdate, String booktime,String key) {


        this.id=id;
        this.duser=duser;
        this.puser = puser;
        this.pphone = pphone;
        this.page = page;
        this.pdisease = pdisease;
        this.bookdate = bookdate;
        this.booktime = booktime;
        this.key=key;
    }

    public String getId() {
        return id;
    }
    public String getDuser() {
        return duser;
    }

    public String getPuser() {
        return puser;
    }

    public String getPphone() {
        return pphone;
    }

    public String getPage() {
        return page;
    }

    public String getPdisease() {
        return pdisease;
    }

    public String getBookdate() {
        return bookdate;
    }

    public String getBooktime() {
        return booktime;
    }

    public String getKey() {
        return key;
    }
}
