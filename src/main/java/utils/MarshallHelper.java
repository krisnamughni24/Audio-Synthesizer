package utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

import configuration.Preset;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class MarshallHelper
{

	private Class<?> clazz;

	public MarshallHelper(Class<?> clazz)
	{
		this.clazz = clazz;
	}

	public String marshall(Object object)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			StringWriter sw = new StringWriter();
			marshaller.marshal(object, sw);
			return sw.toString();
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public Optional<Object> unmarshall(String marshalledObject)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object unmarshalled = unmarshaller.unmarshal(new StringReader(marshalledObject));
			return Optional.of((Preset) unmarshalled);
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
