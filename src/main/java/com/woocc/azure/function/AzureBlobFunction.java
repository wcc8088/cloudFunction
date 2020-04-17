package com.woocc.azure.function;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BlobInput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.StorageAccount;

public class AzureBlobFunction {
    /**
     * This function listens at endpoint "/api/hello". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/hello
     * 2. curl {your host}/api/hello?name=HTTP%20Query
     */
    @FunctionName("BlobSize")
    @StorageAccount("Storage_Account_Connection_String")
    public HttpResponseMessage blobSize(@HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request, @BlobInput(name = "file", dataType = "binary", path = "www/{Query.file}") byte[] content, final ExecutionContext context) 
    {
        // build HTTP response with size of requested blob
        return request.createResponseBuilder(HttpStatus.OK).body("The size of \"" + request.getQueryParameters().get("file") + "\" is: " + content.length + " bytes").build();
    }

}
