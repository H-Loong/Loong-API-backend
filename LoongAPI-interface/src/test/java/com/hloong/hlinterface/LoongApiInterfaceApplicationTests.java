package com.hloong.hlinterface;

import com.hloong.client_sdk.client.LoongAPIClient;
import com.hloong.client_sdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

@SpringBootTest
class LoongApiInterfaceApplicationTests {

    @Resource
    private LoongAPIClient loongAPIClient;

    @Test
    void contextLoads() {
        String name1 = loongAPIClient.getNameByGet("H-Loong");
        User user = new User();
        user.setName("h-Loong");
        String name2 = loongAPIClient.getUsernameByPost(user);
        System.out.println(name1);
        System.out.println(name2);

    }

}
