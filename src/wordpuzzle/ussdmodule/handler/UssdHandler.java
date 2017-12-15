package wordpuzzle.ussdmodule.handler;

import java.io.IOException;
import java.sql.SQLException;

import wordpuzzle.mappers.UsersMapper;
import wordpuzzle.models.Users;
import wordpuzzle.smsmodule.handler.WordCheck;
import wordpuzzle.ussdmodule.models.initiat.OutboundUSSDMessageRequest;
import wordpuzzle.ussdmodule.models.initiat.ResponseRequest;
import wordpuzzle.ussdmodule.models.initiat.UssdInitiat;
import wordpuzzle.ussdmodule.models.receiveformat.UssdReceiveFormat;
import wordpuzzle.ussdmodule.sender.HttpSender;
import wordpuzzle.util.Utility;

public class UssdHandler {

	public void divider(UssdReceiveFormat receiveFormat) throws SQLException {
		
		String msisdn = receiveFormat.getInboundUSSDMessageRequest().getAddress();
		msisdn = msisdn.split("\\+")[1];
		
		String ussdMessage = receiveFormat.getInboundUSSDMessageRequest().getInboundUSSDMessage();
		String ussdAction = receiveFormat.getInboundUSSDMessageRequest().getUssdAction();
		
		UsersMapper usersMapper = new UsersMapper();
		Users user = usersMapper.getUser(msisdn);
		
		String register = "0";
		
		String status = null;
		OutboundUSSDMessageRequest outboundRequest = null;
		
		if(user != null) {
			register = user.getRegister();
			status = user.getStatus();
			outboundRequest = this.createRequest(user);
		}
		
		String infoMessage = null;
		String nextStatus = null;
		
		WordCheck check = new WordCheck();
		
		if(ussdAction.equalsIgnoreCase(Utility.USSD_MT_INIT) && register.equals("0") && ussdMessage.equals(Utility.USSD_DIAL_CODE)) {
			//Register user and Send puzzle word, status change to 1
			System.out.println("Register user and send word");
			usersMapper.registerUser(msisdn);
			user = usersMapper.getUser(msisdn);
			outboundRequest = this.createRequest(user);
			
			infoMessage = check.sendNewWord(user);
			outboundRequest.setUssdAction(Utility.USSD_MT_INIT);
			
			nextStatus = "1";
			
		} else if((ussdAction.equalsIgnoreCase(Utility.USSD_MT_INIT) && register.equals("1") && ussdMessage.equals(Utility.USSD_DIAL_CODE)) || (ussdAction.equalsIgnoreCase(Utility.USSD_MT_CONT) && register.equals("1") && status.equals("2") && ussdMessage.equals("1"))) {
			//Display the puzzle word, status change to 1
			System.out.println("send word..");
			usersMapper.setStatus(msisdn, "0");
			
			infoMessage = check.sendNewWord(user);
			outboundRequest.setUssdAction(Utility.USSD_MT_INIT);
			
			nextStatus = "1";
			
		} else if(ussdAction.equalsIgnoreCase(Utility.USSD_MT_CONT) && register.equals("1") && status.equals("1")) {
			//Check the word, status change to 2
			System.out.println("check the word");
			infoMessage = check.checkReceivedWord(user, ussdMessage);
			outboundRequest.setUssdAction(Utility.USSD_MT_CONT);
			
			nextStatus = "2";
			
		} else if(ussdAction.equalsIgnoreCase(Utility.USSD_MT_CONT) && register.equals("1") && status.equals("2") && ussdMessage.equals("2")) {
			// User exit from the session, status change to 0
			nextStatus = "0";
			System.out.println("session over");
		} else {
			//Wrong ussd code
			System.out.println("wrong code");
		}
		
		
		if(infoMessage != null) {
			outboundRequest.setOutboundUSSDMessage(infoMessage);
			
			UssdInitiat request = new UssdInitiat();
			request.setOutboundUSSDMessageRequest(outboundRequest);
			
			HttpSender sender = new HttpSender();
			try {
				sender.requestSender(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(nextStatus != null) {
			usersMapper.setStatus(msisdn, nextStatus);
		}
	}
	
	public OutboundUSSDMessageRequest createRequest(Users user) {
		
		OutboundUSSDMessageRequest request = new OutboundUSSDMessageRequest();
		request.setAddress("tel:+"+ user.getMsisdn());
		request.setShortCode("tel:1234");
		request.setKeyword("123");
		request.setClientCorrelator("123456");
		
		ResponseRequest responseRequest = new ResponseRequest();
		responseRequest.setNotifyURL("http://ussd.response.receive.url");
		responseRequest.setCallbackData("some-data-useful-to-the-requester");
		
		request.setResponseRequest(responseRequest);
		
		return request;
	}
}
