package com.example.mdm_everis.sign_up

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.Constant
import com.example.core.failure.Failure
import com.example.domain.user.UserResponse
import com.example.mdm_everis.MainActivity
import com.example.mdm_everis.R
import com.example.mdm_everis.base.BaseFragment
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.sign_up_card_view.*
import kotlinx.coroutines.tasks.await


class SignUpFragment : BaseFragment<SignUpViewModel>() {

    //******************************************* BaseFragment abstract ****************************

    override fun getLayout() = R.layout.fragment_sign_up
    override fun getViewModel() = SignUpViewModel::class

    //******************************************* End BaseFragment abstract ************************

    private lateinit var user : UserResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showNavbar(false)
        (activity as MainActivity).setFragment(Constant.FragmentFlag.SIGN_UP)
        initListener()
        initObservers()
    }

    //******************************************* Init *********************************************

    private fun initListener(){
        btn_registrate.setOnClickListener{
            when{
                et_full_name.text.toString() == "" -> et_full_name.error = Constant.ErrorSignUp.NOT_NAME
                et_email.text.toString() == "" -> et_email.error = Constant.ErrorSignUp.NOT_EVERIS_EMAIL
                et_password.text.toString() == "" -> et_password.error = Constant.ErrorSignUp.NOT_PWD
                et_rep_password.text.toString() == "" -> et_rep_password.error = Constant.ErrorSignUp.NOT_REP_PWD
                et_password.text.toString() != et_rep_password.text.toString() -> et_rep_password.error = Constant.ErrorSignUp.NOT_EQUAL_PWD
                else ->{
                    if (et_password.text?.length!! >= 8){
                        viewModel.signUp(et_email.text.toString()+
                                Constant.GeneralConstant.EVERIS_EMAIL_EXTENSIONS,et_password.text.toString())
                    }else{
                        toast(Constant.ErrorSignUp.PWD_TOO_EASY)
                    }
                }
            }


        }
    }

    private fun initObservers(){
        viewModel.signUpLD.observe(this,signUpObserver)
        viewModel.createUserLD.observe(this,createUserObserver)
        viewModel.signUpFailureLD.observe(this,errorSignUpObserver)
        viewModel.failureLD.observe(this,errorSignUpObserver)
    }

    //******************************************* End Init *****************************************

    //******************************************* Observers ****************************************

    private val signUpObserver = Observer<String>{uid->
        user = UserResponse(uid,et_email.text.toString()+Constant.GeneralConstant.EVERIS_EMAIL_EXTENSIONS,
            et_full_name.text.toString(), arrayListOf(),"")
            callCreateUser(user)
    }



    private val errorSignUpObserver = Observer<Failure> {
        toast(Constant.ErrorSignUp.ERROR_REGISTRO)
    }


    private val createUserObserver = Observer<String>{
        val alertDialog = AlertDialog.Builder(context)
        if (it == "User created"){
            alertDialog.setTitle("Correo de verificación")
            alertDialog.setMessage("Ve a tu correo de EVERIS y verifica tu correo. Si no lo has recibido el correo revisa tus datos.")
            alertDialog.setPositiveButton("Sí, ir al login"
            ) { _, _ ->
                findNavController().navigate(SignUpFragmentDirections.actionSignUpToLogin())
            }
            alertDialog.setNeutralButton("Cancelar",null)
        }else{
            alertDialog.setTitle("Error")
            alertDialog.setMessage("No se ha podido registrar su usuario.")
            alertDialog.setPositiveButton("Reintentar"
            ) { _, _ ->
                viewModel.createUser(user)
            }
        }
        alertDialog.show()
    }

    //******************************************* End Observers ************************************

}