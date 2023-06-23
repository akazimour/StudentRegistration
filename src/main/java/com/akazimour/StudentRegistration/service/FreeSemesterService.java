package com.akazimour.StudentRegistration.service;

import com.akazimour.StudentRegistration.aspect.ListeningErrors;
import com.akazimour.StudentRegistration.repository.StudentRepository;
import jakarta.transaction.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Random;
import java.util.concurrent.RejectedExecutionException;

@Service
public class FreeSemesterService {

   Random random = new Random();
   private int getAnInt() {
      int i = random.nextInt(6)+1;
      return i;
   }
   @ListeningErrors
   public int requestFreeSemesters(String centralId){

      try {
         Thread.sleep(2000);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      int i = getAnInt();
      if (i==2 || i==3) {
         throw new RejectedExecutionException("This is an Error which was thrown with purpose");
      }
      return i;
   }




}