package nl.pocos.wicket.ui.components.scalars.string;

import org.apache.isis.core.metamodel.adapter.ObjectAdapter;
import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.scalars.string.StringPanel;
import org.apache.isis.viewer.wicket.ui.util.Components;
import org.apache.isis.viewer.wicket.ui.util.CssClassAppender;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.widget.tooltip.CustomTooltipBehavior;

import nl.pocos.applib.StringWithAdditionalInfo;
import nl.pocos.wicket.ui.components.instruction.InfoPanel;

public class AdditionalInfoStringPanel extends StringPanel {

    private static final long serialVersionUID = 1L;
    private String info = null;
    private static final String ID_INFO_PANEL = "infoPanel";
    
    public AdditionalInfoStringPanel(final String id, final ScalarModel model)
    {
        super(id, model);
    }
    
    /**
     * Mandatory hook method to build the component to render the model when in
     * {@link Rendering#COMPACT compact} format.
     * 
     * <p>
     * This default implementation uses a {@link Label}, however it may be overridden if required.
     */
    @Override
    protected Component addComponentForCompact()
    {
        Fragment compactFragment = getCompactFragment(CompactType.SPAN);
        final Label labelIfCompact = new Label(ID_SCALAR_IF_COMPACT, getModel().getObjectAsString());
        compactFragment.add(labelIfCompact);
        
        this.info = null;
    	ObjectAdapter adapter = getModel().getObject();
    	if(adapter != null)
    	{
    		this.info = ((StringWithAdditionalInfo) adapter.getObject()).getAdditionalInfo();
    	}
    	if(this.info != null)
    	{
    		labelIfCompact.add(new InfoTooltipBehavior(this.info));
    		labelIfCompact.add(new CssClassAppender("additionalInfoValueIfCompact"));
    	}
        
        scalarTypeContainer.addOrReplace(compactFragment);
        return labelIfCompact;
    }
    
    @Override
    protected MarkupContainer addComponentForRegular()
	{
		final MarkupContainer labelIfRegular = super.addComponentForRegular();
		addInfoPanelForRegular(labelIfRegular);
		
		return labelIfRegular;
    }
	
	protected void addInfoPanelForRegular(final MarkupContainer labelIfRegular) {
		ObjectAdapter adapter = getModel().getObject();
		if(adapter != null)
		{
			String info = ((StringWithAdditionalInfo) adapter.getObject()).getAdditionalInfo();

			labelIfRegular.addOrReplace(new InfoPanel(ID_INFO_PANEL, Model.of(info), labelIfRegular));

			if(info == null || info.isEmpty())
			{
				Components.permanentlyHide(labelIfRegular, ID_INFO_PANEL);
			}
			else
			{
				labelIfRegular.add(new AttributeModifier("title", null));
				labelIfRegular.add(AttributeModifier.append("class", new Model<String>("hasInfoPanel")));
			}
		}
    }
	
	@Override
    public void renderHead(final IHeaderResponse response)
	{
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(new CssResourceReference(AdditionalInfoStringPanel.class, "AdditionalInfoStringPanel.css")));
	}
	
	class InfoTooltipBehavior extends CustomTooltipBehavior
	{
		private static final long serialVersionUID = 1L;

		private final String content;

		public InfoTooltipBehavior(String content)
		{
			super(newOptions());

			this.content = content;
			//bind(component);
		}

		@Override
		protected WebMarkupContainer newContent(String markupId)
		{
			Fragment fragment = new Fragment(markupId, "tooltip-fragment", AdditionalInfoStringPanel.this);
			fragment.add(new Label("content", Model.of(this.content)));

			return fragment;
		}
	}

	private static Options newOptions()
	{
		Options options = new Options();
		options.set("track", true);
		options.set("hide", "{ effect: 'fade', delay: 100 }");
		options.set("tooltipClass","\"myTooltip\"");
		return options;
	}
}
