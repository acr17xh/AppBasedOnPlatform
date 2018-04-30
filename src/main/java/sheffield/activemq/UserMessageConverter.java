package sheffield.activemq;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

public class UserMessageConverter implements MessageConverter {

	public UserMessageConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object fromMessage(Message arg0) throws JMSException, MessageConversionException {
		ObjectMessage objMessage = (ObjectMessage) arg0;
		return objMessage.getObject();
	}

	@Override
	public Message toMessage(Object arg0, Session arg1) throws JMSException, MessageConversionException {
		return arg1.createObjectMessage((Serializable) arg0);

	}

}
