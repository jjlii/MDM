package com.example.core

class Constant{
    object ErrorGeneral{
        const val ERROR_DESCONOCIDO = "ERROR DESCONOCIDO"
    }
    object ErrorSignUp{
        const val ERROR_REGISTRO = "SE HA PRODUCIDO UN ERROR EN EL REGISTRO."
        const val NOT_PWD = "Debes introducir una contraseña"
        const val NOT_EVERIS_EMAIL = "Debes introducir tu correo de everis"
        const val NOT_NAME = "Debes introducir su nombre"
        const val NOT_REP_PWD = "Tienes que introducir de nuevo la contraseña"
        const val NOT_EQUAL_PWD = "La contraseña tiene que coincidir"
        const val PWD_TOO_EASY = "La contraseña debe tener 8 o más caracteres"
    }
    object ErrorLogin{
        const val CONTRESENIA_INCORRECTA = "CONTRASEÑA INCORRECTA"
        const val FORMATO_EMAIL_INCORRECTO = "FORMATO EMAIL INCORRECTO"
        const val NO_EXISTE_USUARIO = "NO EXISTE EL USUARIO"
        const val ERROR_CONEXION = "ERROR DE CONEXION"
        const val EMAIL_NO_VERIFIED = "NO HA VERIFICADO SU EMAIL"
    }
    object GeneralConstant{
        const val EVERIS_EMAIL_EXTENSIONS = "@everis.com"
    }
}