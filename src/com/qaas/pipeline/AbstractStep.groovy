package com.qaas.pipeline

// This is the base class of all steps in the pipeline
abstract class AbstractStep implements Serializable {
    String title
    Image image
    Status status
    String lazyFailure 

    public String getTitle() { return title }
    public Status getStatus() { return status }
    public String getLazyFailure() { return lazyFailure }

    abstract void run()
}