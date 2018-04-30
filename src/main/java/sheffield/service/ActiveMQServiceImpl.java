package sheffield.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service(value = "activeMQService")
public class ActiveMQServiceImpl implements ActiveMQService {

	@Autowired
	private JmsTemplate jmsTemplate;

	public ActiveMQServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendRegisterMessage(Destination destination, Serializable object) {
		// 不用MessageConverter
		// jmsTemplate.send(destination, new MessageCreator() {
		//
		// @Override
		// public Message createMessage(Session arg0) throws JMSException {
		// ObjectMessage objectMessage = arg0.createObjectMessage(object);
		// return objectMessage;
		// }
		// });
		jmsTemplate.convertAndSend(destination, object);
	}

}
