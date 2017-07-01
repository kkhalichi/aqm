package com.khalichi.atcq.service.resource;

import com.khalichi.atcq.framework.cxf.CustomSwagger2Feature;
import javax.ws.rs.core.Response;
import org.apache.cxf.feature.Features;
import org.springframework.stereotype.Service;

/**
 * @author Keivan Khalichi
 * @since Jul 01, 2017
 */
@Service("partnerProxy")
@Features(classes = CustomSwagger2Feature.class)
public class ATCQueueResourceImpl implements ATCQueueResource {

    @Override
    public Response boot() {
        return null;
    }
}
