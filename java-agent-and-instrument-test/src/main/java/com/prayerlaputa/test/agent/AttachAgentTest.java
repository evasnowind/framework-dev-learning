package com.prayerlaputa.test.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * @author chenglong.yu
 * created on 2020/11/17
 */
public class AttachAgentTest {

    public static void main(String[] args) {
        try {
            VirtualMachine vm = VirtualMachine.attach("3032");
            vm.loadAgent("D:\\GitRepository\\framework-dev-learning\\java-agent-and-instrument\\target\\java-agent-and-instrument-1.0-SNAPSHOT.jar");
        } catch (AttachNotSupportedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AgentLoadException e) {
            e.printStackTrace();
        } catch (AgentInitializationException e) {
            e.printStackTrace();
        }
    }
}
