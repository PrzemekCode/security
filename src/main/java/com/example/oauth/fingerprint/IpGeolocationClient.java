package com.example.oauth.fingerprint;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="IpGeolocationClient", url = "http://demo.ip-api.com/json")
public interface IpGeolocationClient {

    @RequestMapping("/{ip}")
    public Object findById(@PathVariable(value="ip") String ip);
}
