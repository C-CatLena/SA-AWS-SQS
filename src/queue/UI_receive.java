package queue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.amazonaws.services.sqs.model.Message;

import queue.sqs_receive;

public class UI_receive {
	static List<Message> m = null;
	static String queueUrl = null;
	
	public static void main(String[] args) {
	//创建JFrame
	JFrame frame = new JFrame("Receive_Message");
    frame.setSize(850, 500);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//画布
	JPanel panel = new JPanel(); 
	frame.add(panel);
	panel.setLayout(null);
	
	//接收的label
	JLabel label = new JLabel("接收到的消息");
	label.setFont(new Font("黑体",Font.BOLD,20));
	label.setBounds(20,20,200,20);
	panel.add(label);
	
	//接收用的文本框
	JTextArea jt = new JTextArea();
	jt.setFont(new Font("黑体",Font.BOLD,20));
	jt.setVisible(true);
    JScrollPane jsp = new JScrollPane(jt);//new一个滚动条
	jsp.setBounds(20, 50,780, 300);
	jt.setVisible(true);
	panel.add(jsp); //放入文本框(当加入滚动条后，加入的就不在是文本框了)
	jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
	//清空按钮
	JButton button_clear = new JButton("清空");
	button_clear.setFont(new Font("黑体",Font.BOLD,20));
	button_clear.setBounds(30,370,90,50);
	panel.add(button_clear);
	button_clear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			jt.setText("");
		}
});
	
	//发送按钮设置
	JButton button = new JButton("接收");
	button.setFont(new Font("黑体",Font.BOLD,20));
	button.setBounds(700,370,90,50);
	panel.add(button);
	//开线程
	sqs_receive demo=new sqs_receive();
	demo.init();
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Thread(new Runnable() {
				public void run() {
					m = demo.run();
					//打印消息
					for(Message re:m) {
						jt.append("Message: " + re.getBody()+'\n');
					}
					//删除消息
			        for (Message msg : m) {
		        	demo.sqs.deleteMessage(demo.queueUrl, msg.getReceiptHandle());
				}
				}
			}).start();
		}
});


	
	frame.setVisible(true);//放在最后
	
	}
}
