// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus;

import java.util.ArrayList;
import java.util.List;

public class ThesaurusValidationItem
{
    private Status status;
    private String newName;
    private String name;
    private List<AlternativeSpecie> similarNames;
    
    public ThesaurusValidationItem() {
        this.status = Status.INVALID;
        this.similarNames = new ArrayList<AlternativeSpecie>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public List<AlternativeSpecie> getSimilarNames() {
        return this.similarNames;
    }
    
    public String getNewName() {
        return this.newName;
    }
    
    public void setNewName(final String newName) {
        this.newName = newName;
    }
    
    public boolean isValidated() {
        return this.status == Status.VALID;
    }
    
    public boolean isNew() {
        return this.status == Status.NEW;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public String getStatusAsString() {
        switch (this.status) {
            case VALID: {
                return "true";
            }
            case INVALID: {
                return "false";
            }
            default: {
                return "new";
            }
        }
    }
    
    public void setStatus(final Status status) {
        this.status = status;
    }
    
    public static class AlternativeSpecie
    {
        private String name;
        private String server;
        private String url;
        
        public AlternativeSpecie(final String name, final String server, final String url) {
            this.name = name;
            this.server = server;
            this.url = url;
        }
        
        public String getName() {
            return this.name;
        }
        
        public void setName(final String name) {
            this.name = name;
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
    
    public enum Status
    {
        INVALID, 
        VALID, 
        NEW;
    }
}
