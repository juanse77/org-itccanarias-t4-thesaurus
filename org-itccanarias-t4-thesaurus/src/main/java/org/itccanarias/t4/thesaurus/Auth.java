// 
// Decompiled by Procyon v0.5.36
// 
package org.itccanarias.t4.thesaurus;

public class Auth
{
    protected String password;
    protected String email;
    
    public Auth() {
    }
    
    public Auth(final String password, final String email) {
        this.password = password;
        this.email = email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(final String email) {
        this.email = email;
    }
}
