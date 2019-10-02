package queue;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class sqs_send {

	static AmazonSQS sqs; //声明一个消息队列
	static String queueUrl = null;  //消息队列的URL
	String msg = null;
	public void init()  {
		 
		 AWSCredentialsProvider credentials = null;
			try {
				credentials = new ProfileCredentialsProvider();
			}  catch (Exception e) {
				throw new AmazonClientException("Can't load the credentials from the credential profiles file. "
						+ "Please make sure that your credentials file is at the correct "
						+ "location (~/.aws/credentials), and is a in valid format.", e);
			}
			
		sqs = AmazonSQSClient.builder().withRegion("us-east-1").withCredentials(credentials).build();
		ListQueuesResult lq_result = sqs.listQueues();
		System.out.println("Your SQS Queue URLs:");
		for (String url : lq_result.getQueueUrls()) {
		    System.out.println(url);
		    queueUrl = url;
		}
		System.out.println("已连接");
	}
	public void run() {
		SendMessageRequest sendMessageRequest = new SendMessageRequest()  //声明一个发送消息的请求
			       .withQueueUrl(queueUrl)   //指定要将消息发送到哪个队列
			       .withMessageBody(msg)  //设置消息内容
			       .withDelaySeconds(0);
	        sendMessageRequest.setMessageGroupId("messageGroup2"); //消息组ID
	        sqs.sendMessage(sendMessageRequest);
	        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
	        String sequenceNumber = sendMessageResult.getSequenceNumber();
	        String messageId = sendMessageResult.getMessageId();
	        System.out.println(
	        		"SendMessage succeed with messageId " + messageId + ", sequence number " + sequenceNumber + "\n");
	}
	
}
