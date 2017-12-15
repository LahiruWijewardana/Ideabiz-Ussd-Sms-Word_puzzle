package wordpuzzle.smsmodule.handler;

import java.io.IOException;
import java.sql.SQLException;

import mobisec.ideabiz.exceptions.SendSmsException;
import mobisec.sms.format.SmsRes;
import wordpuzzle.mappers.UsersMapper;
import wordpuzzle.models.Users;
import wordpuzzle.smsmodule.sender.SmsPuzzleSender;
import wordpuzzle.util.Utility;

public class SmsHandler {

	public void divider(SmsRes receivedSms) throws SQLException {
		
		String msisdn = receivedSms.getInboundSMSMessageNotification().getInboundSMSMessage().getSenderAddress();
		
		// Get received message
		String smsMessage = receivedSms.getInboundSMSMessageNotification().getInboundSMSMessage().getMessage();
		String smsFirstHalf = smsMessage.split(" ")[0];
		String smsSecondHalf = null;
		if(smsMessage.split(" ").length > 1) {
			smsSecondHalf = smsMessage.split(" ")[1];
		}
		
		// Get user information
		UsersMapper usersMapper = new UsersMapper();
		Users user = usersMapper.getUser(msisdn);
		
		String register = "0";
		
		if(user != null) {
			register = user.getRegister();
		}
		
		SmsPuzzleSender sender = new SmsPuzzleSender(Utility.SMS_SENDING_URL);

		
		if(smsMessage.equalsIgnoreCase(Utility.SMS_REG) && register.equals("0")) {
			// Register the user
			System.out.println("User Registration");
			usersMapper.registerUser(msisdn);
			user = usersMapper.getUser(msisdn);
			
			WordCheck wordCheck = new WordCheck();
			String puzzleWord = wordCheck.sendNewWord(user);
			
			String infoMessage = "Thank you for subcriving to Word Puzzle game. Here is your first puzzle word\n" + puzzleWord;
			
			try {
				sender.sendSms(user, infoMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SendSmsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			
		} else if(smsMessage.equalsIgnoreCase(Utility.SMS_REG) && register.equals("1")) {
			// Ask from user to send PUZZLE sms
			System.out.println("Already registered");
			String infoMessage = "You already registed with Word Puzzle Game. Please send 'PUZZLE GET' to get new puzzle word";
			
			try {
				sender.sendSms(user, infoMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SendSmsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(smsFirstHalf.equalsIgnoreCase(Utility.SMS_PUZZLE) && register.equals("1")) {
			// Registered user send the PUZZLE sms 
			
			if(smsSecondHalf.equalsIgnoreCase(Utility.SMS_GET)) {
				System.out.println("Send new word...");
				WordCheck wordCheck = new WordCheck();
				String puzzleWord = wordCheck.sendNewWord(user);
				
				try {
					sender.sendSms(user, puzzleWord);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SendSmsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				//Check the received word for correction
				System.out.println("Checking word...");
				WordCheck wordCheck = new WordCheck();
				String infoMessage = wordCheck.checkReceivedWord(user, smsSecondHalf);
				
				try {
					sender.sendSms(user, infoMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SendSmsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} else if(smsFirstHalf.equalsIgnoreCase(Utility.SMS_PUZZLE) && register.equals("0")) {
			// Register the new user and send the puzzle
			usersMapper.registerUser(msisdn);
			user = usersMapper.getUser(msisdn);
			System.out.println("Register user and send word");
			
			if(smsSecondHalf.equalsIgnoreCase(Utility.SMS_GET)) {
				System.out.println("Send new word...");
				WordCheck wordCheck = new WordCheck();
				String puzzleWord = wordCheck.sendNewWord(user);
				
				try {
					sender.sendSms(user, puzzleWord);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SendSmsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				// Ask user to call PUZZLE GET or register
				System.out.println("Not registered");
				String infoMessage = "You are not registed with Word Puzzle Game. Please send 'REG PUZZLE' to get register with this game.";
				
				try {
					sender.sendSms(user, infoMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SendSmsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} else {
			System.out.println("Message format is wrong");
		}
	}
}
