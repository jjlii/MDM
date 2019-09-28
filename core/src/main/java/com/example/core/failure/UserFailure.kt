package com.example.core.failure

sealed class UserFailure: Failure.FeatureFailure() {

    object InvalidEmail : UserFailure()
    object InvalidEmailFormat : UserFailure()
    object InvalidPassword : UserFailure()
    object EmailNotExist : UserFailure()

}