package nl.pocos.applib;

import java.io.Serializable;

import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Title;

@DomainObjectLayout(named="Aanvullende informatie")
public class StringWithAdditionalInfo implements Serializable
{
	private static final long serialVersionUID = 4581209038280045763L;
	
	// {{ ValueAsString (property)
	private String valueAsString;
	@Title
	public String getValueAsString()
	{
		return valueAsString;
	}

	public void setValue(final String valueAsString)
	{
		this.valueAsString = valueAsString;
	}
	// }}
	
	// {{ Instruction (property)
	private String additionalInfo;

	public String getAdditionalInfo()
	{
		return additionalInfo;
	}

	public void setAdditionalInfo(final String additionalInfo)
	{
		this.additionalInfo = additionalInfo;
	}
	// }}

	@Override
    public String toString()
	{
        return getValueAsString();
    }
}
