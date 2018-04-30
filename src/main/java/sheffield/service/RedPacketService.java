package sheffield.service;

import java.util.List;

import sheffield.mbg.pojo.RedPacketBySender;

public interface RedPacketService {

	int sendRedPacketThenUpdate(Long userid, String redpackettotalsum, String redpacketamount, String redpacketnote);

	List<RedPacketBySender> findAllRedPackets();

	Integer grabRedPacketByRecipient(Long senderid,Long recipientid,String times);

}
