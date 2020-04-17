package com.woocc.azure.function;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BlobInput;
import com.microsoft.azure.functions.annotation.BlobOutput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.StorageAccount;

/**
 * Azure Functions with HTTP Trigger.
 */
public class AzureUploadFunction {
    /**
     * This function listens at endpoint "/api/hello". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/hello
     * 2. curl {your host}/api/hello?name=HTTP%20Query
     */
    @FunctionName("copyBlobHttp")
    @StorageAccount("Storage_Account_Connection_String")
    public HttpResponseMessage copyBlobHttp(
    @HttpTrigger(name = "req", 
      methods = {HttpMethod.GET}, 
      authLevel = AuthorizationLevel.ANONYMOUS) 
    HttpRequestMessage<Optional<String>> request,
    @BlobInput(
      name = "file", 
      dataType = "binary", 
      path = "www/maillist.html") 
    byte[] content,
    @BlobOutput(
      name = "target", 
      path = "www/maillist.html")
    OutputBinding<String> outputItem,
    final ExecutionContext context) {
      // Save blob to outputItem
      String addr = request.getQueryParameters().get("addr");
      context.getLogger().info("ADDR:"+addr);
      String newStr = new String(content, StandardCharsets.UTF_8);
      newStr = newStr + "\n<BR>" + addr;
      context.getLogger().info("NewString:"+newStr);
      outputItem.setValue(newStr);

      // build HTTP response with size of requested blob
      return request.createResponseBuilder(HttpStatus.OK)
        .body("The content of \"" + addr + "\" is: " + newStr + " bytes")
        .build();
    }
}
