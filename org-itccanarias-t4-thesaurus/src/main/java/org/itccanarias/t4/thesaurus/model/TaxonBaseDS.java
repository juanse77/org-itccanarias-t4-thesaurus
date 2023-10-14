// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus.model;

public class TaxonBaseDS
{
    protected String researcher;
    protected String name;
    
    public TaxonBaseDS() {
    }
    
    public TaxonBaseDS(final String reesearcher, final String name) {
        this.researcher = reesearcher;
        this.name = name;
    }
    
    public String getResearcher() {
        return this.researcher;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
