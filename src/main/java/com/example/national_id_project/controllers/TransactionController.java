package com.example.national_id_project.controllers;

import com.example.national_id_project.Model.TransactionDetail;
import com.example.national_id_project.Repository.TransactionDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {


//    private String initial = "WB-";
    private TransactionDetailRepo transactionDetailRepo;

    @Autowired
    public void setTransactionDetailRepo(TransactionDetailRepo transactionDetailRepo) {
        this.transactionDetailRepo = transactionDetailRepo;
    }

    public String generateTID(){


        TransactionDetail transactionDetail;
        transactionDetail = transactionDetailRepo.findTopByOrderByCreatedAtDesc();
        String trID = transactionDetail.getTransactionId();





//        String transactionIdFound = transactionRepo.findByTransactionId("0000000002").getTransactionId();
//
//        long after1 = Long.parseLong(transactionIdFound);
//
//        transactionIdFound =  String.format("%10d", after1);



        return trID;






////        if(transactionRepo.findTopByOrderByCreatedAtDesc() == null){
//
//        TransactionDetail transactionDetail = new TransactionDetail();
////        transactionDetail = transactionRepo.findTopByOrderByCreatedAtDesc();
//
//        transactionDetail = transactionRepo.findAll().get(0);

//        String transactionIdFound = transactionRepo.findByTransactionId()

//        String transactionIdFound = "WB-0000003";
//            String[] splittedTID=  transactionIdFound.split("-");
//            long after1 = Long.parseLong(splittedTID[1]);
//            after1 = after1 +1;
//
//            return  initial.concat(String.valueOf(String.format("%07d", after1)));



//        }else {

//            long after = 0L;
//            return initial.concat(String.valueOf(String.format("%07d", after)));
//        }






    }


}
