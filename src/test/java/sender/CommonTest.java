package sender;

import org.junit.Test;

import com.youku.java.sender.actions.Mail;

public class CommonTest {

	@Test
	public  void test1(){
		String smtp = "smtp.163.com";
		String from = "qq603275863@163.com";
		String username="qq603275863";
		String password="qq603275863";
		String copyto = from;
		
		String to = "qq603275863@163.com";
		
		String subject = "邮件主题";
		String content = "邮件内容";
		
	 //	String filename = "附件路径，如：F:\\笔记<a>\\struts2</a>与mvc.txt";
	//	Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename);
		boolean ok = Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password, null);
		System.out.println(ok);
	}
}
