package com.example.core.failure

sealed class ReserveFailure :Failure.FeatureFailure() {

    object InvalidReserve : ReserveFailure()
    object ErrorGetUserReserves : ReserveFailure()
    object ErrorGetReserve : ReserveFailure()
    object ErrorCreateReserve : ReserveFailure()
    object ErrorCreateCaducatedReserve : ReserveFailure()
    object ErrorDeleteReserve : ReserveFailure()


}