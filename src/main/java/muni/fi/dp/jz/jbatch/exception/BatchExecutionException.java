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

import java.util.logging.Logger;

/**
 *
 * @author Zorz
 */
public class BatchExecutionException extends Exception {
    
    private String msg;
    private Throwable cause;
    
    /**
     * Creates a new instance of <code>NewException</code> without detail
     * message.
     */
    public BatchExecutionException() {
    }

    /**
     * Constructs an instance of <code>NewException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public BatchExecutionException(String msg) {
        super(msg);
    }
    public BatchExecutionException(String msg,Throwable cause) {
        super(msg,cause);
        this.msg = msg;
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "BatchExecutionException{" + "Message= " + msg + ", cause= " + cause + '}';
    }

    
    
    
    
}
