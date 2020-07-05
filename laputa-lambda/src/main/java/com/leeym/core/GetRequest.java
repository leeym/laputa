package com.leeym.core;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2ProxyRequestEvent;
import com.leeym.platform.lambda.Query;
import com.leeym.platform.lambda.Service;

public class GetRequest extends Query<APIGatewayV2ProxyRequestEvent> {

    @Override
    public APIGatewayV2ProxyRequestEvent process() {
        return Service.getRequest();
    }
}
