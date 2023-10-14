// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus.model;

public class TaxonValidationResponse
{
    protected TaxonAlternateChoice[] alternateList;
    protected String taxonName;
    protected boolean validated;
    
    public TaxonValidationResponse() {
    }
    
    public TaxonValidationResponse(final TaxonAlternateChoice[] alternateList, final String taxonName, final boolean validated) {
        this.alternateList = alternateList;
        this.taxonName = taxonName;
        this.validated = validated;
    }
    
    public TaxonAlternateChoice[] getAlternateList() {
        return this.alternateList;
    }
    
    public void setAlternateList(final TaxonAlternateChoice[] alternateList) {
        this.alternateList = alternateList;
    }
    
    public String getTaxonName() {
        return this.taxonName;
    }
    
    public void setTaxonName(final String taxonName) {
        this.taxonName = taxonName;
    }
    
    public boolean isValidated() {
        return this.validated;
    }
    
    public void setValidated(final boolean validated) {
        this.validated = validated;
    }
}
