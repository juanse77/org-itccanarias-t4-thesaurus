// 
// Decompiled by Procyon v0.5.36
// 
package org.itccanarias.t4.thesaurus;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.swing.JOptionPane;

import org.openide.DialogDescriptor;
import org.openide.NotifyDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import org.openide.DialogDescriptor;

import org.itccanarias.t4.serviceproviders.ThesaurusProvider;
import org.itccanarias.t4.thesaurus.model.TaxonDS;
import org.itccanarias.t4.thesaurus.model.TaxonBaseDS;
import org.itccanarias.t4.thesaurus.ui.LoginPanel;
import org.itccanarias.t4.dialog.ProgressDialog;
import org.itccanarias.t4.thesaurus.model.TaxonAlternateChoice;
import org.itccanarias.t4.thesaurus.model.TaxonValidationResponse;
import org.itccanarias.t4.thesaurus.ws.ThesaurusAPIConsumer_Impl;
import org.itccanarias.t4.thesaurus.ws.ResponseWrapper;
import org.itccanarias.t4.util.Utils;

import org.itccanarias.t4.thesaurus.ui.SpeciesValidateDialog;
import org.itccanarias.t4.xmlbind.Tfm4;
import org.itccanarias.t4.dialog.MessageDialog;
import org.itccanarias.t4.util.Tfm4Util;

import org.itccanarias.t4.xmlbind.CodominantItem;
import org.itccanarias.t4.xmlbind.DominantItem;

public class ThesaurusManager {

    private static String login;
    private static String password;

    public static void validateSpecies(final String tfm4File) {
        Tfm4 tfm4;
        try {
            tfm4 = Tfm4Util.loadTfm4FromFile(tfm4File);
        } catch (Exception ex) {
            MessageDialog.showError("Error while loading matrix", ex);
            return;
        }
        final Tfm4 newTfm4 = validateThesaurus(tfm4);
        if (newTfm4 != null) {
            try {
                Tfm4Util.saveTfm4ToFile(tfm4, tfm4File);
                MessageDialog.showInfo("Changes saved to matrix " + tfm4File);
            } catch (Exception ex2) {
                MessageDialog.showError("Changes could not be saved to Tfm4 file " + tfm4File, ex2);
            }
        }
    }

    public static Tfm4 validateThesaurus(final Tfm4 tfm4) {
        Tfm4 retTfm4 = null;
        final List<String> taxaNameList = (List<String>) Tfm4Util.getSpeciesNames(tfm4);
        final List<ThesaurusValidationItem> searchList = searchSpecies(taxaNameList);
        if (searchList != null && searchList.size() > 0) {
            final List<ThesaurusValidationItem> valList = SpeciesValidateDialog.show(searchList);
            if (valList != null) {

                for (final ThesaurusValidationItem item : valList) {

                    if (item.isNew()) {

                        final int retValue = JOptionPane.showConfirmDialog(null, "Do you want to upload new taxa to Thesaurus service?", "Question", 0);
                        if (retValue == 0) {

                            final LoginPanel loginPanel = new LoginPanel();

                            loginPanel.setUser(ThesaurusManager.login);
                            loginPanel.setPassword(ThesaurusManager.password);

                            final Auth auth = getLoginData(loginPanel);

                            if (auth != null) {
                                ThesaurusManager.login = auth.getEmail();
                                ThesaurusManager.password = auth.getPassword();
                            }

                            if (!uploadNewSpecies(auth, valList)) {
                                return null;
                            }

                            break;
                        }

                        break;
                    }
                }

                final Map<String, ThesaurusValidationItem> valMap = new HashMap<String, ThesaurusValidationItem>();
                for (final ThesaurusValidationItem valSpe : valList) {
                    valMap.put(valSpe.getName(), valSpe);
                }

                changeTfm4Species(tfm4, valMap);
                retTfm4 = tfm4;
            }
        }
        return retTfm4;
    }

    public static List<String> validateSpecies(final List<String> taxaNameList) {
        final List<ThesaurusValidationItem> searchList = searchSpecies(taxaNameList);
        final List<String> noValidated = new ArrayList<String>();
        if (searchList != null) {
            for (final ThesaurusValidationItem item : searchList) {
                if (!item.isValidated()) {
                    noValidated.add(item.getName());
                }
            }
        }
        return noValidated;
    }

    private static List<ThesaurusValidationItem> searchSpecies(final List<String> speciesList) {
        class SearchTask implements Runnable {

            private List<ThesaurusValidationItem> retList;

            SearchTask() {
                this.retList = new ArrayList<ThesaurusValidationItem>();
            }

            @Override
            public void run() {

                try {

                    final String url = Utils.getThesaurusWebServiceURL();
                    final String endpoint = "/validate_taxon";

                    final ThesaurusAPIConsumer_Impl api_consumer = new ThesaurusAPIConsumer_Impl(url);

                    final TaxonValidationResponse[] validationResponseList = api_consumer.validateTaxaName(
                            endpoint,
                            (String[]) speciesList.toArray(new String[0])
                    );

                    if (validationResponseList != null && validationResponseList.length > 0) {

                        final Map<String, ThesaurusValidationItem> valMap = new HashMap<String, ThesaurusValidationItem>();

                        for (final TaxonValidationResponse validationResponse : validationResponseList) {

                            ThesaurusValidationItem validationItem = valMap.get(validationResponse.getTaxonName());

                            if (validationItem == null) {
                                validationItem = new ThesaurusValidationItem();
                                validationItem.setName(validationResponse.getTaxonName().replace("*", ""));
                                valMap.put(validationResponse.getTaxonName(), validationItem);
                            }

                            if (validationResponse.isValidated()) {
                                validationItem.setStatus(ThesaurusValidationItem.Status.VALID);
                            }

                            for (final TaxonAlternateChoice taxonChoice : validationResponse.getAlternateList()) {

                                for (final String alternativeName : taxonChoice.getAlternativeTaxonName()) {
                                    final ThesaurusValidationItem.AlternativeSpecie altSpecie = new ThesaurusValidationItem.AlternativeSpecie(alternativeName, taxonChoice.getServer(), taxonChoice.getUrl());
                                    validationItem.getSimilarNames().add(altSpecie);
                                }

                            }
                        }

                        this.retList.addAll(valMap.values());

                    } else {
                        MessageDialog.showInfo("Thesaurus service did not return a valid response");
                    }

                } catch (Exception ex) {
                    MessageDialog.showError("Thesaurus service error", ex);
                }
            }
        }

        final SearchTask task = new SearchTask();
        ProgressDialog.showProgress((Runnable) task, "Querying taxa names in Thesaurus system");
        return task.retList;
    }

    private static void changeTfm4Species(final Tfm4 tfm4, final Map<String, ThesaurusValidationItem> valMap) {

        if (tfm4.getMatrix().getCodominant() != null) {

            for (final CodominantItem.Species species : tfm4.getMatrix().getCodominant().getSpecies()) {

                final ThesaurusValidationItem valSpe = valMap.get(species.getName());

                if (valSpe != null) {

                    if (valSpe.getNewName() != null && !valSpe.getNewName().isEmpty()) {
                        species.setName(valSpe.getNewName());
                    }
                    species.setValidated(valSpe.getStatusAsString());

                }

            }

        } else {

            if (tfm4.getMatrix().getDominant() == null) {
                throw new NullPointerException("Null Dominant and Codominant items");
            }

            for (final DominantItem.Species species2 : tfm4.getMatrix().getDominant().getSpecies()) {
                final ThesaurusValidationItem valSpe = valMap.get(species2.getName());
                if (valSpe != null) {
                    if (valSpe.getNewName() != null && !valSpe.getNewName().isEmpty()) {
                        species2.setName(valSpe.getNewName());
                    }
                    switch (valSpe.getStatus()) {
                        case INVALID: {
                            species2.setValidated("false");
                            continue;
                        }
                        case VALID: {
                            species2.setValidated("true");
                            continue;
                        }
                        case NEW: {
                            species2.setValidated("new");
                            continue;
                        }
                    }
                }
            }
        }
    }

    private static boolean uploadNewSpecies(final Auth auth, final List<ThesaurusValidationItem> valList) {

        final List<TaxonBaseDS> sendList = new ArrayList<TaxonBaseDS>();

        String urlApi = Utils.getThesaurusWebServiceURL();
        ThesaurusAPIConsumer_Impl thesaurusAPIConsumer = new ThesaurusAPIConsumer_Impl(urlApi);

        try {
            ResponseWrapper response_wrapper = thesaurusAPIConsumer.login(auth, "/login");

            if (response_wrapper.response_code == HttpURLConnection.HTTP_OK) {

                final String token = response_wrapper.message;

                for (final ThesaurusValidationItem item : valList) {

                    if (item.getStatus() == ThesaurusValidationItem.Status.NEW) {

                        final TaxonBaseDS tb = new TaxonBaseDS();
                        tb.setName(item.getNewName());

                        sendList.add(tb);

                    }
                }

                class UploadTask implements Runnable {

                    @Override
                    public void run() {

                        try {

                            final String url = Utils.getThesaurusWebServiceURL();
                            final String endpoint = "/taxon/new";

                            final ThesaurusAPIConsumer_Impl api_consumer = new ThesaurusAPIConsumer_Impl(url);
                            final TaxonDS[] resp = api_consumer.addTaxa(
                                    endpoint,
                                    (TaxonBaseDS[]) sendList.toArray(new TaxonBaseDS[0]),
                                    token
                            );

                            if (resp != null && resp.length > 0) {
                                MessageDialog.showInfo("New taxa were uploaded to Thesaurus service");                                
                            } else {
                                MessageDialog.showInfo("Thesaurus service didn't returned a valid response");
                            }

                        } catch (Exception ex) {
                            MessageDialog.showError("Error with Thesaurus service while uploading new taxa", ex);
                        }
                    }
                }

                final UploadTask task = new UploadTask();
                ProgressDialog.showProgress((Runnable) task, "Uploading new taxa to Thesaurus service");

            } else {
                MessageDialog.showError("Authentication error. Check your credentials", new Exception(response_wrapper.message));
                return false;
            }

        } catch (Exception ex) {
            MessageDialog.showError("Authentication error. Check your credentials", ex);
            return false;
        }

        return true;
    }

    private static Auth getLoginData(final LoginPanel loginPanel) {

        final DialogDescriptor dd = new DialogDescriptor((Object) loginPanel, "Thesaurus Login Credentials");
        dd.setHelpCtx(new HelpCtx("Thesaurus validation"));
        final Object option = DialogDisplayer.getDefault().notify((NotifyDescriptor) dd);

        Auth auth = null;

        if (option == NotifyDescriptor.OK_OPTION) {
            auth = new Auth();
            auth.setEmail(loginPanel.getUser());
            auth.setPassword(loginPanel.getPassword());
        }

        return auth;
    }

    public static class Thesaurus implements ThesaurusProvider {

        public void validateSpecies(final String tfm4File) {
            ThesaurusManager.validateSpecies(tfm4File);
        }

        public Tfm4 validateThesaurus(final Tfm4 tfm4) {
            return ThesaurusManager.validateThesaurus(tfm4);
        }

        public List<String> validateSpecies(final List<String> taxaNameList) {
            return ThesaurusManager.validateSpecies(taxaNameList);
        }
    }
}
