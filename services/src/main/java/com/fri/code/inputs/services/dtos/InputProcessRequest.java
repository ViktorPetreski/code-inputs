package com.fri.code.inputs.services.dtos;

public class InputProcessRequest {
    private String inputID;
    private String inputLocation;

    public InputProcessRequest(String inputID, String inputLocation) {
        this.inputID = inputID;
        this.inputLocation = inputLocation;
    }

    public String getInputID() {
        return inputID;
    }

    public void setInputID(String inputID) {
        this.inputID = inputID;
    }

    public String getInputLocation() {
        return inputLocation;
    }

    public void setInputLocation(String inputLocation) {
        this.inputLocation = inputLocation;
    }
}
