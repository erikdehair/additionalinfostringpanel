package nl.pocos.wicket.ui.components.scalars.string;

import nl.pocos.applib.StringWithAdditionalInfo;

import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.scalars.ComponentFactoryScalarAbstract;
import org.apache.wicket.Component;


public class AdditionalInfoStringPanelFactory extends ComponentFactoryScalarAbstract {

	private static final long serialVersionUID = 1L;

    public AdditionalInfoStringPanelFactory() {
        super(AdditionalInfoStringPanel.class, StringWithAdditionalInfo.class);
    }
    
    @Override
	protected Component createComponent(String id, ScalarModel scalarModel) {
    	return new AdditionalInfoStringPanel(id, scalarModel);
	}
}
