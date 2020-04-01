package com.woocc.azure.function;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


출처: https://epicdevs.com/21 [Epic Developer]

public class AzureKafkaFunction {
    /**
     * This function listens at endpoint "/api/hello". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/hello
     * 2. curl {your host}/api/hello?name=HTTP%20Query
     */
    @FunctionName("Kafka")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request. -- Kafka -- ");

        // Parse query parameter
        try {
                Properties props = new Properties();
                props.put("metadata.broker.list", "kafka-test-001.epicdevs.com:9092,kafka-test-002.epicdevs.com:9092,kafka-test-003.epicdevs.com:9092");
                props.put("serializer.class", "kafka.serializer.StringEncoder");
                ProducerConfig producerConfig = new ProducerConfig(props);
                Producer<String, String> producer = new Producer<String, String>(producerConfig);
                
                List<KeyedMessage<String, String>> messages = new ArrayList<KeyedMessage<String, String>>();
                for (int i = 0; i < 10; i++)
                { 
                    messages.add(new KeyedMessage<String, String>("test", "Hello, World!")); 
                }
                producer.send(messages);
                producer.close();
        }
        catch (StorageException storageException) {
            context.getLogger().info(storageException.getMessage());
            System.exit(-1);
        }
        catch (Exception e) {
            context.getLogger().info(e.getMessage());
            System.exit(-1);
        }

        if (Kafkar == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body(Kafkar + " - Mail Added.").build();
        }
    }
}
