// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus.model;

public class TaxonAlternateChoice
{
    protected String[] alternativeTaxonName;
    protected String server;
    protected String url;
    
    public TaxonAlternateChoice() {
    }
    
    public TaxonAlternateChoice(final String[] alternativeTaxonName, final String server, final String url) {
        this.alternativeTaxonName = alternativeTaxonName;
        this.server = server;
        this.url = url;
    }
    
    public String[] getAlternativeTaxonName() {
        return this.alternativeTaxonName;
    }
    
    public void setAlternativeTaxonName(final String[] alternativeTaxonName) {
        this.alternativeTaxonName = alternativeTaxonName;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public void setServer(final String server) {
        this.server = server;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
}
