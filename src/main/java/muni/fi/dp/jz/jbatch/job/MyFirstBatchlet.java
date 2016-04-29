/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.dp.jz.jbatch.job;

import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

/**
 *
 * @author Zorz
 */
public class MyFirstBatchlet implements javax.batch.api.Batchlet {

    @Inject JobContext jobContext;
    @Inject StepContext stepContext;
    
    @Override
    public String process() throws Exception {
        
        long executionId = jobContext.getExecutionId();
        
        if(isEven(executionId))   {
            throw new 
              NullPointerException("I don't like even numbers :)");
        }
        return "SUCCESS";
    }
    
    private boolean isEven(long num) {
        return (num % 2 == 0);
    }

    @Override
    public void stop() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
