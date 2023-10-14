// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus.ws;

import org.itccanarias.t4.thesaurus.model.TaxonBaseDS;
import org.itccanarias.t4.thesaurus.model.TaxonDS;
import org.itccanarias.t4.thesaurus.model.TaxonValidationResponse;

public interface ThesaurusAPIConsumer {
    
    public TaxonDS addTaxon(
            final String endpoint,
            final String taxon,
            final String token) throws Exception;
    
    public TaxonDS[] addTaxa(
            final String endpoint,
            final TaxonBaseDS[] taxa,
            final String token) throws Exception;
    
    public boolean deleteTaxon(
            final String endpoint,
            final int taxon_id,
            final String token) throws Exception;
    
    public TaxonDS getTaxon(
            final String endpoint,
            final int taxon_id) throws Exception;
    
    public TaxonDS[] listTaxon(final String endpoint) throws Exception;
    
    public boolean updateTaxon(
            final String endpoint,
            final Integer taxon_id,
            final String new_name,
            final String token) throws Exception;
    
    public TaxonValidationResponse[] validateTaxaName(
            final String endpoint,
            final String[] taxa) throws Exception;
    
    public TaxonValidationResponse validateTaxonName(
            final String endpoint,
            final String taxon) throws Exception;
}
