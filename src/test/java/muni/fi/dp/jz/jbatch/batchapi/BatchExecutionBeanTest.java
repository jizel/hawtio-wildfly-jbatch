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
package muni.fi.dp.jz.jbatch.batchapi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.transaction.Transactional;
import muni.fi.dp.jz.jbatch.exception.BatchExecutionException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;

/**
 *
 * @author Zorz
 */
@RunWith(Arquillian.class)
public class BatchExecutionBeanTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @EJB
    private BatchExecutionBean batchExecutor;
    private static final Logger LOG = Logger.getLogger(BatchExecutionBeanTest.class.getName());

    @Deployment
    public static WebArchive createDeployment() {
        WebArchive war = ShrinkWrap.create(WebArchive.class);
        war.addPackage("muni.fi.dp.jz.jbatch.batchapi");
        war.addPackage("muni.fi.dp.jz.jbatch.exception");
        war.addClass(BatchExecutionBean.class);
        war.addClass(BatchExecutionException.class);
        war.addAsResource("META-INF/batch.xml");
        war.addAsResource("META-INF/batch-jobs/simple-batchlet-job.xml");
        return war;
    }

    /**
     * Test of submitJob method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(1)
    public void testSubmitJob() throws Exception {
        System.out.println("submitJob");
        long expected = 1;
        String jobName = "simple-batchlet-job";

        Assert.assertEquals(expected, batchExecutor.submitJob(jobName));
    }

    /**
     * Test of getJobExecution method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(2)
    public void testGetJobExecution() throws Exception {
        System.out.println("getJobExecution");
        long execId = 1;
        Assert.assertEquals("org.jberet.runtime.JobExecutionImpl@1", batchExecutor.getJobExecution(execId).toString());
    }

    /**
     * Test of restartJob method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(3)
    public void testRestartJob() throws Exception {
        System.out.println("restartJob");
        long newJobId = batchExecutor.restartJob(1);
        Assert.assertEquals(2, newJobId);
    }

    /**
     * Test of getJobNames method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobNames() throws Exception {
        System.out.println("getJobNames");
        Set<String> expectedJobNames = new HashSet<>();
        expectedJobNames.add("simple-batchlet-job");
        Assert.assertEquals(expectedJobNames, batchExecutor.getJobNames());
    }

    /**
     * Test of getJobInstanceCount method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstanceCount() throws Exception {
        System.out.println("getJobInstanceCount");
        Assert.assertEquals(1, batchExecutor.getJobInstanceCount("simple-batchlet-job"));
    }

    /**
     * Test of getRunningExecutions method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetRunningExecutions() throws Exception {
        System.out.println("getRunningExecutions");
        List<Long> expectedExecutions = new ArrayList<>();
//        Get some batch with endless running state!
//        expectedExecutions.add((long)1);
//        expectedExecutions.add((long)2);

        Assert.assertEquals(expectedExecutions, batchExecutor.getRunningExecutions("simple-batchlet-job"));
    }

    /**
     * Test of getJobInstances method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstances_String() throws Exception {
        System.out.println("getJobInstances");
        String jobName = "simple-batchlet-job";
        String expected = "org.jberet.runtime.JobInstanceImpl@1";
        Assert.assertEquals(expected, batchExecutor.getJobInstances(jobName).get(0).toString());

    }

    /**
     * Test of getJobInstances method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstances_3args() throws Exception {
        System.out.println("getJobInstances");
        String jobName = "simple-batchlet-job";
        int start = 0;
        int count = 1;
        String expected = "org.jberet.runtime.JobInstanceImpl@1";
        Assert.assertEquals(expected, batchExecutor.getJobInstances(jobName, start, count).get(0).toString());
    }

    /**
     * Test of getJobExecutions method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    @Transactional
    public void testGetJobExecutions() throws Exception {
        System.out.println("getJobExecutions");
        JobInstance tstInstance = batchExecutor.getJobInstances("simple-batchlet-job").get(0);
        String expected = "[org.jberet.runtime.JobExecutionImpl@1, org.jberet.runtime.JobExecutionImpl@2]";

        Assert.assertEquals(expected, batchExecutor.getJobExecutions(tstInstance).toString());
    }

    /**
     * Test of getStepExecutions method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetStepExecutions() throws Exception {
        System.out.println("getStepExecutions");
        long jobExecutionId = 1L;
        String expected = "[org.jberet.runtime.StepExecutionImpl@f9aff64a]";

        Assert.assertEquals(expected, batchExecutor.getStepExecutions(jobExecutionId).toString());
    }

    /**
     * Test of getJobInstance method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetJobInstance() throws Exception {
        System.out.println("getJobInstance");
        long executionId = 1L;
        String expected = "org.jberet.runtime.JobInstanceImpl@1";

        Assert.assertEquals(expected, (batchExecutor.getJobInstance(executionId)).toString());
    }

    /**
     * Test of getParameters method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    public void testGetParameters() throws Exception {
        System.out.println("getParameters");
        long execId = 1L;
        String expected = "{}";

        Assert.assertEquals(expected, (batchExecutor.getParameters(execId)).toString());
    }

    /**
     * Test of abandon method, of class BatchExecutionBean.
     */
    @Test
    @InSequence(4)
    @Transactional
    public void testAbandon() throws Exception {
        System.out.println("abandon");
        long executionId = 1L;
        String expectedStatus = "ABANDONED";
        batchExecutor.abandon(executionId);

        Assert.assertEquals(expectedStatus, (batchExecutor.getJobExecution(executionId).getBatchStatus()).toString());
    }
    
    /**
     * Test of stop method, of class BatchExecutionBean.
     * TODO: make testStop of running batch job (find/write job that never stops or at least waits long enough) 
     */
    @Test
    @InSequence(5)
    @Transactional
    public void testStop() {
        System.out.println("stop");
        long executionId = 1L;
        try{
        batchExecutor.stop(executionId);
        //Cannot restart abandoned exception
        fail("Exception was not thrown when stopping an abandoned execution");
        }catch(BatchExecutionException e){
            Assert.assertTrue(true);
        }catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }        
    }
        
//    Illegal argument tests
    
    @Test
    public void testSubmitNullJob() throws Exception {
        System.out.println("submitNullJob");
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Cannot start job with jobname null";
        try{
        batchExecutor.submitJob(null);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test(expected = BatchExecutionException.class)
    public void testSubmitInvalidJobName() throws Exception {
        batchExecutor.submitJob("invalid-job");           
    }
    
    @Test
    public void testStopNegativeExucitonId() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Execution id cannot be less than 1";
        try{
        batchExecutor.stop(-2L);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test(expected = BatchExecutionException.class)
    public void testStopInvalidId() throws Exception {
        batchExecutor.stop(50L);           
    }
    
    @Test
    public void testRestartExucitonIdZero() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Execution id cannot be less than 1";
        try{
        batchExecutor.restartJob(0L);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test(expected = BatchExecutionException.class)
    public void testRestartInvalidId() throws Exception {
        batchExecutor.restartJob(50L);           
    }
    
    @Test
    public void testGetExecutionExucitonIdZero() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Execution id cannot be less than 1";
        try{
        batchExecutor.getJobExecution(0L);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test(expected = BatchExecutionException.class)
    public void testGetExecutionInvalidId() throws Exception {
        batchExecutor.getJobExecution(50L);           
    }
    
    @Test
    public void testGetJobInstanceCountNullJob() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Cannot get instance count for jobname null";
        try{
        batchExecutor.getJobInstanceCount(null);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test(expected = BatchExecutionException.class)
    public void testGetInstanceCountInvalidJob() throws Exception {
        batchExecutor.getJobInstanceCount("invalid-job");           
    }
    
    @Test
    public void testGetJobInstancesNullJob() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Cannot get job instances for jobname null";
        try{
        batchExecutor.getJobInstances(null);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test(expected = BatchExecutionException.class)
    public void testGetJobInstancesInvalidJob() throws Exception {
        batchExecutor.getJobInstances("Invalid");           
    }
    
    @Test
    public void testGetJobExecutionsNullJob() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Cannot get job executions for null";
        try{
        batchExecutor.getJobExecutions(null);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }
    
    @Test
    public void testGetStepExucitonNegative() throws Exception {
//        EJB exception wrapping causes EJBException when IllegalArgumentException is thrown
        String expectedException ="javax.ejb.EJBException: java.lang.IllegalArgumentException: Execution id cannot be less than 1";
        try{
        batchExecutor.getStepExecutions(-5L);
        fail("JobName cannot be null");
       }catch(EJBException e){
            Assert.assertEquals(expectedException,e.toString());
        }
        catch(Exception e){
            fail("Wrong exception thrown: " + e);
        }   
    }    
    
    @Test(expected = BatchExecutionException.class)
    public void testGetStepExucitonInvalidId() throws Exception {
        batchExecutor.getStepExecutions(50L);           
    }
    
}
