package com.example.national_id_project.Repository;

import com.example.national_id_project.Model.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, String> {

    TransactionDetail findTopByOrderByCreatedAtDesc();


}
