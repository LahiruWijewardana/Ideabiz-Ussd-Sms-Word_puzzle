package wordpuzzle.smsmodule.sender;

import java.io.IOException;

import mobisec.ideabiz.exceptions.SendSmsException;
import mobisec.sms.format.SmsSendReq;
import mobisec.sms.send.SmsSender;
import wordpuzzle.models.Users;

public class SmsPuzzleSender extends SmsSender{

	public SmsPuzzleSender(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	
	public void sendSms(Users user, String message) throws IOException, SendSmsException {
		
		SmsSendReq sendReq = new SmsSendReq();
		sendReq.setAddress("tel:+"+ user.getMsisdn());
		sendReq.setSenderAddress("tel:8771");
		sendReq.setClientCorrelator("123456");
		sendReq.setSenderName("ABCCo");
		sendReq.setMessage(message);
		
		System.out.println(message);
		
		//send(sendReq);
	}

}
