// 
// Decompiled by Procyon v0.5.36
// 
package org.itccanarias.t4.thesaurus.model;

import java.util.Calendar;

public class TaxonDS extends TaxonBaseDS {

    protected Integer idTaxon;
    protected Calendar registrationData;

    public TaxonDS() {
    }

    public TaxonDS(final String researcher, final String name, final Integer idTaxon, final Calendar registrationData) {
        this.researcher = researcher;
        this.name = name;
        this.idTaxon = idTaxon;
        this.registrationData = registrationData;
    }

    public Integer getIdTaxon() {
        return this.idTaxon;
    }

    public void setIdTaxon(final Integer idTaxon) {
        this.idTaxon = idTaxon;
    }

    public Calendar getRegistrationData() {
        return this.registrationData;
    }
}
