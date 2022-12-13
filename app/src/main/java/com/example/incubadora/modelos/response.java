package com.example.incubadora.modelos;

public class response {
    private datarespnse Data;

    public response(datarespnse data) {
        Data = data;
    }

    public datarespnse getData() {
        return Data;
    }

    public void setData(datarespnse data) {
        Data = data;
    }
}
