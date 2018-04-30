package sheffield.service;

import java.io.Serializable;

import javax.jms.Destination;

public interface ActiveMQService {

	public void sendRegisterMessage(Destination destination, final Serializable object);

}
