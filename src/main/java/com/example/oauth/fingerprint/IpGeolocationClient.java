package com.example.oauth.fingerprint;

import com.example.oauth.model.IpGeolocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="IpGeolocationClient", url = "${ip.geolocation.client.url}")
public interface IpGeolocationClient {

    @RequestMapping("/{ip}")
    public IpGeolocationResponse findById(@PathVariable(value="ip") String ip);
}
