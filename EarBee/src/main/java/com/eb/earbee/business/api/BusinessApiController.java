package com.eb.earbee.business.api;


import com.eb.earbee.business.entity.Business;
import jakarta.xml.ws.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BusinessApiController {

    @PostMapping("/search")
    public Response<Business> businessSearch(){








        return null;
    }
}
