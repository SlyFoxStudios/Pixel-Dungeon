package com.watabou.noosa;

import java.awt.Color;
import java.util.*;
import com.watabou.noosa.*;

public class Scheduler {
    
    ExecuteMethod executeMethod = ExecuteMethod.DELAY;
    
    int ticks;
    int currentTicks;
    
    public boolean isActive = false;
    
    public Scheduler(int ticks) {
        this.ticks = ticks;
        Game.instance.schedulers.add(this);
    }
    
    public void update() {
        if (isActive) {
            currentTicks++;
            if (currentTicks == ticks) {
                Execute();
                if (executeMethod == ExecuteMethod.LOOP) {
                    currentTicks = 0;
                }
                else if (executeMethod == ExecuteMethod.DELAY) {
                    isActive = false;
                }
            }
        }
    }
    
    public void StartLoop() {
        isActive = true;
        executeMethod = ExecuteMethod.LOOP;
    }
    
    public void StartDelay() {
        isActive = true;
        executeMethod = ExecuteMethod.DELAY;
    }
    
    public void Execute() {
        
    }
    
    private enum ExecuteMethod {
        DELAY,
        LOOP
    }
}