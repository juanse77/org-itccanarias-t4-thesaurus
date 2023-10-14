package org.itccanarias.t4.thesaurus.ws;

import java.util.List;
import org.itccanarias.t4.xmlbind.Tfm4;

public interface ThesaurusProvider
{
    void validateSpecies(final String p0);
    
    Tfm4 validateThesaurus(final Tfm4 p0);
    
    List<String> validateSpecies(final List<String> p0);
}