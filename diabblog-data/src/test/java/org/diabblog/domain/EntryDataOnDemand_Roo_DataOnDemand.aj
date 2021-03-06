// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.diabblog.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.diabblog.domain.Entry;
import org.diabblog.domain.EntryDataOnDemand;
import org.diabblog.domain.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect EntryDataOnDemand_Roo_DataOnDemand {
    
    declare @type: EntryDataOnDemand: @Component;
    
    private Random EntryDataOnDemand.rnd = new SecureRandom();
    
    private List<Entry> EntryDataOnDemand.data;
    
    @Autowired
    EntryRepository EntryDataOnDemand.entryRepository;
    
    public Entry EntryDataOnDemand.getNewTransientEntry(int index) {
        Entry obj = new Entry();
        setBloodSugarLevel(obj, index);
        setCarbs(obj, index);
        setComments(obj, index);
        setCorrection(obj, index);
        setCreated(obj, index);
        setFastInsulin(obj, index);
        setSlowInsulin(obj, index);
        return obj;
    }
    
    public void EntryDataOnDemand.setBloodSugarLevel(Entry obj, int index) {
        Float bloodSugarLevel = new Integer(index).floatValue();
        if (bloodSugarLevel > 9.99999999F) {
            bloodSugarLevel = 9.99999999F;
        }
        obj.setBloodSugarLevel(bloodSugarLevel);
    }
    
    public void EntryDataOnDemand.setCarbs(Entry obj, int index) {
        String carbs = "carbs_" + index;
        if (carbs.length() > 255) {
            carbs = carbs.substring(0, 255);
        }
        obj.setCarbs(carbs);
    }
    
    public void EntryDataOnDemand.setComments(Entry obj, int index) {
        String comments = "comments_" + index;
        if (comments.length() > 255) {
            comments = comments.substring(0, 255);
        }
        obj.setComments(comments);
    }
    
    public void EntryDataOnDemand.setCorrection(Entry obj, int index) {
        String correction = "correction_" + index;
        if (correction.length() > 255) {
            correction = correction.substring(0, 255);
        }
        obj.setCorrection(correction);
    }
    
    public void EntryDataOnDemand.setCreated(Entry obj, int index) {
        Calendar created = Calendar.getInstance();
        obj.setCreated(created);
    }
    
    public void EntryDataOnDemand.setFastInsulin(Entry obj, int index) {
        Float fastInsulin = new Integer(index).floatValue();
        if (fastInsulin > 9.99999999F) {
            fastInsulin = 9.99999999F;
        }
        obj.setFastInsulin(fastInsulin);
    }
    
    public void EntryDataOnDemand.setSlowInsulin(Entry obj, int index) {
        Float slowInsulin = new Integer(index).floatValue();
        if (slowInsulin > 9.99999999F) {
            slowInsulin = 9.99999999F;
        }
        obj.setSlowInsulin(slowInsulin);
    }
    
    public Entry EntryDataOnDemand.getSpecificEntry(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Entry obj = data.get(index);
        Long id = obj.getId();
        return entryRepository.findOne(id);
    }
    
    public Entry EntryDataOnDemand.getRandomEntry() {
        init();
        Entry obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return entryRepository.findOne(id);
    }
    
    public boolean EntryDataOnDemand.modifyEntry(Entry obj) {
        return false;
    }
    
    public void EntryDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = entryRepository.findAll(new org.springframework.data.domain.PageRequest(from / to, to)).getContent();
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Entry' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Entry>();
        for (int i = 0; i < 10; i++) {
            Entry obj = getNewTransientEntry(i);
            try {
                entryRepository.save(obj);
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            entryRepository.flush();
            data.add(obj);
        }
    }
    
}
