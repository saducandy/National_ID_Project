package com.example.national_id_project.Repository;

import com.example.national_id_project.Model.TransactionDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, String> {

    TransactionDetail findByTransactionId(String id);
    TransactionDetail findTopByOrderByCreatedAtDesc();

    @Transactional
    @Modifying
    @Query("update TransactionDetail set Auth = :status where transactionId = :id")
    void updateStatusAuth(@Param("status") Boolean status, @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update TransactionDetail set KYC = :status where transactionId = :id")
    void updateStatusKYC(@Param("status") Boolean status, @Param("id") String id);


}
