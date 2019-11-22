package com.example.core.failure

sealed class ReserveFailure :Failure.FeatureFailure() {

    object InvalidReserve : ReserveFailure()

}