/*
 * Copyright 2016 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package muni.fi.dp.jz.jbatch.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import javax.ejb.EJBAccessException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.json.JSONObject;

/**
 *
 * @author Zorz
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Provider
public class EJBAccessExceptionMapper implements ExceptionMapper<EJBAccessException> {
    public static final EJBAccessExceptionMapper INSTANCE = new EJBAccessExceptionMapper();    

    @Override
    public Response toResponse(EJBAccessException exception) {
        JSONObject jsonResp = new JSONObject();
        jsonResp.put("outcome","not-allowed");
        jsonResp.put("description","This operation is not allowed by your user role.");
        return Response.ok(jsonResp.toString(), MediaType.APPLICATION_JSON).build();
    }
    
}
