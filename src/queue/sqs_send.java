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

	static AmazonSQS sqs; //����һ����Ϣ����
	static String queueUrl = null;  //��Ϣ���е�URL
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
		System.out.println("������");
	}
	public void run() {
		SendMessageRequest sendMessageRequest = new SendMessageRequest()  //����һ��������Ϣ������
			       .withQueueUrl(queueUrl)   //ָ��Ҫ����Ϣ���͵��ĸ�����
			       .withMessageBody(msg)  //������Ϣ����
			       .withDelaySeconds(0);
	        sendMessageRequest.setMessageGroupId("messageGroup2"); //��Ϣ��ID
	        sqs.sendMessage(sendMessageRequest);
	        SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
	        String sequenceNumber = sendMessageResult.getSequenceNumber();
	        String messageId = sendMessageResult.getMessageId();
	        System.out.println(
	        		"SendMessage succeed with messageId " + messageId + ", sequence number " + sequenceNumber + "\n");
	}
	
}
