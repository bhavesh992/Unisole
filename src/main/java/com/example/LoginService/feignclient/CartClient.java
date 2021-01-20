package com.example.LoginService.feignclient;


import com.example.LoginService.domain.Cart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CartService", url = "http://localhost:9095/")
public interface CartClient {
    @PostMapping(value = "/cart/create")
    public Cart createCart(@RequestBody Cart cart);
}
