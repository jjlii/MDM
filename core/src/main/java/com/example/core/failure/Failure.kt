package com.example.core.failure

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object Unknown: Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}