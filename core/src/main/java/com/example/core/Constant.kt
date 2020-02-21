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
        const val NEED_EMAIL = "ES NECESARIO INTRODUCIR SU EMAIL"
    }

    object FragmentFlag{
        const val LOGIN = "F_LOGIN"
        const val SIGN_UP = "F_SIGN_UP"
        const val RESERVES = "F_RESERVES"
        const val FAVORITES = "F_FAVORITES"
        const val DEVICES = "F_DEVICES"
        const val PROFILE = "F_PROFILE"
        const val DEVICE_DETAILS = "F_DEVICE_DETAILS"
        const val RESERVE_PROCESS = "F_RESERVE_PROCESS"
        const val RESET_PWD = "RESET_PWD"
    }

    object Hours{
        const val NINE = 32400000
        const val SIX = 64800000
    }

    object DateFormat{
        const val DATE_WITH_TIME = "dd/MM/yyyy HH:mm"
        const val DATE_WITHOUT_TIME = "dd/MM/yyyy"
    }

    object CategoryPhoto{
        const val ANDROID = "https://firebasestorage.googleapis.com/v0/b/mdm-everis-prueba.appspot.com/o/CategoryPNG%2FAndroid.png?alt=media&token=71bb17f5-a646-4184-a89d-b9b6ec318ea6"
        const val IOS = "https://firebasestorage.googleapis.com/v0/b/mdm-everis-prueba.appspot.com/o/CategoryPNG%2FApple.png?alt=media&token=9e8e8852-db15-4966-aadf-2c470d2df754"
        const val PHONE = "https://firebasestorage.googleapis.com/v0/b/mdm-everis-prueba.appspot.com/o/CategoryPNG%2Fphone.png?alt=media&token=4accda78-4dcf-4cc3-b212-672dc1387cec"
        const val TABLET = "https://firebasestorage.googleapis.com/v0/b/mdm-everis-prueba.appspot.com/o/CategoryPNG%2Ftablet.png?alt=media&token=2c4236c8-71b1-4c2b-9ad8-7e91636379e4"
    }

    object Msg{
        const val NOT_RESERVES = "No tienes reservas"
        const val ERROR_LOAD_DEVICES = "Error al cargar los datos"
    }

}