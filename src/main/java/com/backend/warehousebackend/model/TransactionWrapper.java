package com.backend.warehousebackend.model;

import java.sql.Timestamp;

public class TransactionWrapper {
    private Timestamp createdDate;
    private Object transaction;

    public TransactionWrapper(Timestamp createdDate, Object transaction) {
        this.createdDate = createdDate;
        this.transaction = transaction;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Object getTransaction() {
        return transaction;
    }

    public void setTransaction(Object transaction) {
        this.transaction = transaction;
    }
}

