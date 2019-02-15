package com.example.android.newshub.model.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchArticle implements Parcelable
{

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("print_page")
    @Expose
    private String printPage;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedia> multimedia = null;
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("keywords")
    @Expose
    private List<Keyword> keywords = null;
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("byline")
    @Expose
    private Byline byline;
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("word_count")
    @Expose
    private Integer wordCount;
    public final static Parcelable.Creator<SearchArticle> CREATOR = new Creator<SearchArticle>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SearchArticle createFromParcel(Parcel in) {
            return new SearchArticle(in);
        }

        public SearchArticle[] newArray(int size) {
            return (new SearchArticle[size]);
        }

    }
            ;

    protected SearchArticle(Parcel in) {
        this.webUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.snippet = ((String) in.readValue((String.class.getClassLoader())));
        this.printPage = ((String) in.readValue((String.class.getClassLoader())));
        this.source = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.multimedia, (java.lang.Object.class.getClassLoader()));
        this.headline = ((Headline) in.readValue((Headline.class.getClassLoader())));
        in.readList(this.keywords, (Keyword.class.getClassLoader()));
        this.pubDate = ((String) in.readValue((String.class.getClassLoader())));
        this.documentType = ((String) in.readValue((String.class.getClassLoader())));
        this.newsDesk = ((String) in.readValue((String.class.getClassLoader())));
        this.byline = ((Byline) in.readValue((Byline.class.getClassLoader())));
        this.typeOfMaterial = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.wordCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SearchArticle() {
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPrintPage() {
        return printPage;
    }

    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public Byline getByline() {
        return byline;
    }

    public void setByline(Byline byline) {
        this.byline = byline;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(webUrl);
        dest.writeValue(snippet);
        dest.writeValue(printPage);
        dest.writeValue(source);
        dest.writeList(multimedia);
        dest.writeValue(headline);
        dest.writeList(keywords);
        dest.writeValue(pubDate);
        dest.writeValue(documentType);
        dest.writeValue(newsDesk);
        dest.writeValue(byline);
        dest.writeValue(typeOfMaterial);
        dest.writeValue(id);
        dest.writeValue(wordCount);
    }

    public int describeContents() {
        return 0;
    }

}
