package wordpuzzle.smsmodule.receiver;

import java.sql.SQLException;

import mobisec.interfaces.SmsListener;
import mobisec.sms.format.SmsRes;
import wordpuzzle.smsmodule.handler.SmsHandler;

public class SmsReceiver implements SmsListener{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSmsReceive(SmsRes receivedSms) {
		// TODO Auto-generated method stub
		System.out.println("This is the Sms Receiver...");
		
		System.out.println(receivedSms.getInboundSMSMessageNotification().getInboundSMSMessage().getMessage());
		
		SmsHandler smsHandler = new SmsHandler();
		try {
			smsHandler.divider(receivedSms);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
